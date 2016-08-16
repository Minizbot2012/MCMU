package mcmu.downloader.containers;

public class DLOBJ {
    public String URL;
    public String Hash;
    public String FileName;
    public String Ext;
    public String Folder;
    public Sided Side;
    public CompatOverride Override;

    public DLOBJ() {
        this.Override = CompatOverride.None;
        this.Ext = ".jar";
        this.Side = Sided.BOTH;
        this.Folder = "mods/";
    }
}
