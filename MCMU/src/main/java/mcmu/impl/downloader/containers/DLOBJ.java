package mcmu.impl.downloader.containers;

import mcmu.api.CompatOverride;
import mcmu.api.Sided;

public class DLOBJ {
    public String URL;
    public String Hash;
    public String Ext = ".jar";
    public String Folder = "mods/";
    public CompatOverride Override = CompatOverride.NONE;
    public Sided Side = Sided.BOTH;
}
