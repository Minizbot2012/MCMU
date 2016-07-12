package mcmu.downloader.loaders;

import com.google.gson.Gson;
import mcmu.MCMU;
import mcmu.utils.FileList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LocalIdxLoader
{
  public LocalIdxLoader()
  {
    try
    {
      BufferedReader cfile = new BufferedReader(new FileReader("local-mods.json"));
      Gson json = new Gson();
      MCMU.filst.put("local", json.fromJson(cfile, FileList.class));
    }
    catch (IOException localIOException) {
      System.out.println("No local-mods.json, not loading");
    }
  }
}
