package mcmu.containers;

import mcmu.containers.typed_structures.DataType;
import mcmu.containers.typed_structures.TypedData;

public class DLOBJ {
    public TypedData<String> URL;
    public TypedData<String> Hash;
    public TypedData<String> Ext;
    public TypedData<String> Folder;
    public TypedData<Sided> Side;
    public TypedData<CompatOverride> Override;

    public DLOBJ() {
        this.Override = new TypedData<>(CompatOverride.NONE, DataType.ENUM);
        this.Ext = TypedData.string(".jar");
        this.Side = new TypedData<>(Sided.BOTH, DataType.ENUM);
        this.Folder = TypedData.string("mods/");
    }
}
