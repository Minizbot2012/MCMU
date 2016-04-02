package mcmu.downloader.containers;

public class DLOBJ
{
  public String URL;
  public String Hash;
  public String FileName;
  public String Ext;
  public String Folder;
  public String Side;
  
  public DLOBJ()
  {
    this.Ext = ".jar";
    this.Side = "both";
    this.Folder = "mods/";
  }
}
