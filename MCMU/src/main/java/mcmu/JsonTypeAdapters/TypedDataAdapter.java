package mcmu.JsonTypeAdapters;

import com.google.gson.*;
import mcmu.Statics;
import mcmu.containers.CompatOverride;
import mcmu.containers.Sided;
import mcmu.containers.typed_structures.DataType;
import mcmu.containers.typed_structures.TypedData;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TypedDataAdapter implements JsonSerializer<TypedData>, JsonDeserializer<TypedData> {
    @Override
    public JsonElement serialize(TypedData typedData, Type type, JsonSerializationContext jsonSerializationContext) {
        return Statics.Json.toJsonTree(typedData.getValue());
    }

    @Override
    public TypedData deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (jsonElement.isJsonArray()) {
            TypedData<ArrayList> d = new TypedData<>(DataType.LIST);
            d.setValue(Statics.Json.fromJson(jsonElement.getAsJsonArray(), ArrayList.class));
            return d;
        } else if (jsonElement.isJsonPrimitive()) {
            JsonPrimitive jso = jsonElement.getAsJsonPrimitive();
            if (jso.isBoolean()) {
                return TypedData.bool(jso.getAsBoolean());
            } else if (jso.isString()) {
                if(type.getTypeName().contains("java.lang")) {
                    return TypedData.string(jso.getAsString());
                } else if(type.getTypeName().contains("Sided")) {
                    return TypedData.Enum(Statics.Json.fromJson(jsonElement, Sided.class));
                } else if(type.getTypeName().contains("CompatOverride")) {
                    return TypedData.Enum(Statics.Json.fromJson(jsonElement, CompatOverride.class));
                }
            } else {
                System.out.println(jsonElement.getAsString());
                return null;
            }
        } else if (jsonElement.isJsonObject()) {
            return new TypedData<>(jsonDeserializationContext.deserialize(jsonElement.getAsJsonObject(), Object.class), DataType.OBJECT);
        }
        return null;
    }
}
