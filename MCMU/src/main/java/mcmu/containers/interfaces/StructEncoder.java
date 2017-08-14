package mcmu.containers.interfaces;

import mcmu.containers.typed_structures.TypedData;

import java.util.Map;

public interface StructEncoder {
    void writeStructurePart(String key, TypedData data);
    String[] getParts();
    TypedData getPart(String s);

}
