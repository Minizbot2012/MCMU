package mcmu.containers;

import mcmu.containers.interfaces.StructEncoder;
import mcmu.containers.typed_structures.TypedData;


/**
 * Created by bradl on 8/16/2016.
 */
public class Config implements StructEncoder {
    public TypedData<String> URL = TypedData.string();
    public TypedData<String> ID = TypedData.string();
    public TypedData<String> Hash = TypedData.string();
    public Config() {
        URL.setValue("");
        ID.setValue("");
        Hash.setValue("");
    }

    @Override
    public void writeStructurePart(String key, TypedData data) {
        switch(key) {
            case "URL":
                URL.setValue((String) data.getValue());
                break;
            case "ID":
                ID.setValue((String) data.getValue());
                break;
            case "Hash":
                Hash.setValue((String) data.getValue());
                break;
            default:
        }
    }

    @Override
    public String[] getParts() {
        return new String[]{"URL", "ID", "Hash"};
    }

    @Override
    public TypedData getPart(String s) {
        switch(s) {
            case "URL":
                return URL;
            case "ID":
                return ID;
            case "Hash":
                return Hash;
            default:
                return TypedData.none();
        }
    }
}
