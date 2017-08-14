package mcmu.containers;

import mcmu.containers.typed_structures.TypedData;

/**
 * Created by bradl on 2/28/2016.
 */
public class RMOBJ {
    public RMOBJ() {
        this.Ext = TypedData.string(".jar");
        this.Folder = TypedData.string("mods/");
    }

    public TypedData<String> FileName;
    public TypedData<String> Folder;
    public TypedData<String> Ext;
}
