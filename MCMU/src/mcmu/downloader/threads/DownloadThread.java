package mcmu.downloader.threads;

import mcmu.MCMU;
import mcmu.downloader.containers.DLOBJ;
import mcmu.utils.Utils;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by bradl on 2/20/2016.
 */
public class DownloadThread implements Runnable {
    DLOBJ downloadInf;
    public DownloadThread(DLOBJ download) {
            downloadInf = download;
    }
    @Override
    public void run() {
        saveFile(downloadInf);
    }
    public void saveFile(DLOBJ obj)
    {
        String path = obj.Folder + obj.FileName + obj.Ext;
        File folder = new File(obj.Folder);
        File flDisabled = new File(path+".disabled");
        if(flDisabled.exists()) {
            return;
        }
        File fl = new File(path);
        if (fl.exists() || folder.mkdirs()) {
            try
            {
                FileInputStream fis = new FileInputStream(path);
                String MD5 = Utils.MD5(fis);
                fis.close();
                if (MD5.equals(obj.Hash)) {
                    return;
                }
            }
            catch (IOException exception) {}
        }
        if (obj.Side.isClient() == MCMU.Side.isClient() || obj.Side.isServer() == MCMU.Side.isServer()) {
            System.out.println("Started downloading : " + obj.FileName + " : " + obj.Hash);
            byte[] byt = GetFile(obj.URL);
            if (byt == null) {
                System.out.println("unable to download file: " + obj.FileName);
                return;
            }
            try {
                FileOutputStream fos = new FileOutputStream(path);
                fos.write(byt);
                fos.close();
                System.out.println("Finished downloading: " +obj.FileName);
            } catch (IOException ignored) {

            }
        }
    }

    public byte[] GetFile(String Addr)
    {
        byte[] bytes;
        try
        {
            HttpsURLConnection conn = (HttpsURLConnection) new URL(Addr).openConnection();
            bytes = Utils.getBytes(conn.getInputStream());
        }
        catch (MalformedURLException ex)
        {
            System.out.println("Malformed URL in index: " + Addr);
            return null;
        }
        catch (IOException ex)
        {
            System.out.println("Unable to download mod at: " + Addr);
            return null;
        }
        return bytes;
    }
}
