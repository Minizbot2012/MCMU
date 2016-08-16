package mcmu;

import com.google.gson.Gson;
import mcmu.downloader.loaders.*;
import mcmu.downloader.ModLoader;
import mcmu.downloader.containers.*;
import mcmu.downloader.threads.ConfigThread;

import java.io.*;
import java.util.*;

public class MCMU
{
  static String DLURL;
  public static Sided Side;
  public static Map<String, FileList> filst = new HashMap<>();
  public static boolean loadRemote = true;
  
  public MCMU()
  {
    System.out.println("Loading mods from website in mod-repo.json");
    ConfigFile cnf;
    try
    {
      BufferedReader cfile = new BufferedReader(new FileReader("mod-repo.json"));
      Gson confson = new Gson();
      cnf = confson.fromJson(cfile, ConfigFile.class);
      DLURL = cnf.URL;
      Side = cnf.Side;
      System.out.println("Side: " + Side);
    }
    catch (FileNotFoundException FNF)
    {
      System.out.println("unable to read mod-repo.json, not loading remote files");
      loadRemote = false;
    }
    LocalIdxLoader lidx = new LocalIdxLoader();
    ModLoader ml = new ModLoader();
  }
  public static void main(String[] args) {
      new MCMU();
  }
}