package mcmu.JsonTypeAdapters;

import com.google.gson.*;
import mcmu.Statics;
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
                        String typeName = type.getTypeName();
                        Class<? extends Enum> enm = (Class<? extends Enum>) Class.forName(typeName.substring(type.getTypeName().indexOf("<") + 1, typeName.lastIndexOf(">")));
                        return TypedData.Enum(jsonCtx.deserialize(jsonElement, enm));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                return null;
            }
        } else if (jsonElement.isJsonObject()) {
            String typeName = type.getTypeName();
            Class<?> clazz = null;
            try {
                clazz = Class.forName(typeName.substring(type.getTypeName().indexOf("<") + 1, typeName.lastIndexOf(">")));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if(clazz != null)
                return new TypedData<>(jsonCtx.deserialize(jsonElement.getAsJsonObject(), clazz), DataType.OBJECT);
            else
                return new TypedData<>(jsonCtx.deserialize(jsonElement.getAsJsonObject(), Object.class), DataType.OBJECT);
        }
        return null;
    }
}
