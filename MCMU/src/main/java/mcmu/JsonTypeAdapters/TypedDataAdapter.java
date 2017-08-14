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
    public JsonElement serialize(TypedData typedData, Type type, JsonSerializationContext jsonCtx) {
        return Statics.Json.toJsonTree(typedData.getValue());
    }

    @Override
    public TypedData deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonCtx) throws JsonParseException {
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
                } else {
                    try {
                        Class<? extends Enum> enm = (Class<? extends Enum>) Class.forName(type.getTypeName().substring(type.getTypeName().indexOf("<") + 1, type.getTypeName().lastIndexOf(">")));
                        return TypedData.Enum(jsonCtx.deserialize(jsonElement, enm));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                return null;
            }
        } else if (jsonElement.isJsonObject()) {
            return new TypedData<>(jsonCtx.deserialize(jsonElement.getAsJsonObject(), Object.class), DataType.OBJECT);
        }
        return null;
    }
}
