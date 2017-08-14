package mcmu.containers;

import mcmu.containers.interfaces.StructEncoder;
import mcmu.containers.typed_structures.DataType;
import mcmu.containers.typed_structures.TypedData;

import java.util.*;

public class ConfigFile implements StructEncoder {
    public TypedData<String> URL = TypedData.string();
    public TypedData<ArrayList<String>> URLs = TypedData.StringList();
    public TypedData<ArrayList<String>> localIDXs = TypedData.StringList();
    public TypedData<Sided> Side = new TypedData<>(DataType.ENUM);

    @Override
    public void writeStructurePart(String key, TypedData data) {

    }

    @Override
    public String[] getParts() {
        return new String[0];
    }

    @Override
    public TypedData getPart(String s) {
        return null;
    }
}