package mcmu.downloader.threads;

import mcmu.utils.Utils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ConfigThread implements Runnable
{
    public String ConfURL;
  public ConfigThread(String cnfURL)
  {
      this.ConfURL = cnfURL;
  }
  
  public String dirpart(String name)
  {
    int s = name.lastIndexOf(File.separatorChar);
    return s == -1 ? "" : name.substring(0, s);
  }

  @Override
  public void run() {
    try
    {
      File fl = new File("config.zip");
      if (fl.exists())
      {
        FileInputStream fis = new FileInputStream(fl);
        String b64hash = Utils.MD5B64(fis);
        BufferedReader in = new BufferedReader(new InputStreamReader(new URL(this.ConfURL+".hash").openConnection().getInputStream()));
        String h = in.readLine();
        if(b64hash.equals(h)) {
          return;
        }
      }
      BufferedInputStream confbuf = new BufferedInputStream(new URL(this.ConfURL).openStream());
      byte[] byt = Utils.getBytes(confbuf);
      confbuf.close();
      File confzip = new File("config.zip");
      FileOutputStream fos = new FileOutputStream(confzip);
      fos.write(byt);
      fos.close();
      ZipFile zip = new ZipFile(confzip);
      Enumeration e = zip.entries();
      while (e.hasMoreElements())
      {
        ZipEntry ent = (ZipEntry)e.nextElement();
        InputStream is = zip.getInputStream(ent);
        byte[] byts = Utils.getBytes(is);
        File confile = new File("config/" + ent.getName());
        File dir = new File(dirpart(("config/" + ent.getName()).replace("/", File.separator)));
        dir.mkdirs();
        if (!ent.isDirectory())
        {
          BufferedOutputStream cofos = new BufferedOutputStream(new FileOutputStream("config/" + ent.getName()));
          cofos.write(byts);
          cofos.close();
        }
      }
    }
    catch (MalformedURLException ex)
    {
      Logger.getLogger(ConfigThread.class.getName()).log(Level.SEVERE, null, ex);
    }
    catch (IOException ex)
    {
      Logger.getLogger(ConfigThread.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
