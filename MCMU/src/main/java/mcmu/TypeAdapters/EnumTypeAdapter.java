package mcmu.TypeAdapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by bradl on 1/12/2017.
 */
public class EnumTypeAdapter implements JsonDeserializer<Enum> {
    @Override
    public Enum deserialize(JsonElement json, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        try {
            if (type instanceof Class && ((Class<?>) type).isEnum())
                return Enum.valueOf((Class<Enum>) type, json.getAsString().toUpperCase());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
