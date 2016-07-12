package mcmu.downloader.containers;

public class DLOBJ
{
  public String URL;
  public String Hash;
  public String FileName;
  public String Ext;
  public String Folder;
  public Sided Side;
  
  public DLOBJ()
  {
    this.Ext = ".jar";
    this.Side = Sided.BOTH;
    this.Folder = "mods/";
  }
}
