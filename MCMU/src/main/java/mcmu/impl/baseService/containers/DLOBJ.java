package mcmu.impl.baseService.containers;

import mcmu.api.Sided;
import mcmu.api.CompatOverride;

public class DLOBJ {
    public String URL;
    public String Hash;
    public String Ext = ".jar";
    public String Folder = "mods/";
    public CompatOverride Override = CompatOverride.NONE;
    public Sided Side = Sided.BOTH;
}
