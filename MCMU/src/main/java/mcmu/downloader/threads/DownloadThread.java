package mcmu.downloader.threads;

import mcmu.MCMU;
import mcmu.downloader.containers.CompatOverride;
import mcmu.downloader.containers.DLOBJ;
import mcmu.utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by bradl on 2/20/2016.
 */
public class DownloadThread implements Runnable {
    DLOBJ downloadInf;
    String filename;
    public DownloadThread(DLOBJ download) {
        downloadInf = download;
    }
    public DownloadThread(String FN, DLOBJ download) {
        downloadInf = download;
        filename = FN;
    }

    @Override
    public void run() {
        saveFile(downloadInf);
    }

    public void saveFile(DLOBJ obj) {
        String path = obj.Folder + filename + obj.Ext;
        File folder = new File(obj.Folder);
        File flDisabled = new File(path + ".disabled");
        File fl;
        if (flDisabled.exists()) {
            fl = new File(path + ".disabled");
            if (obj.Override == CompatOverride.Enable) {
                fl.renameTo(new File(path));
                fl = new File(path);
            } else {
                return;
            }
        } else {
            fl = new File(path);
            if (obj.Override == CompatOverride.Disable) {
                fl.renameTo(new File(path + ".disabled"));
                return;
            }
        }
        if (fl.exists() || folder.mkdirs()) {
            try {
                FileInputStream fis = new FileInputStream(path);
                String MD5 = Utils.MD5(fis);
                fis.close();
                if (MD5.equals(obj.Hash)) {
                    return;
                }
            } catch (IOException exception) {
            }
        }
        if (obj.Side.isClient() == MCMU.Side.isClient() || obj.Side.isServer() == MCMU.Side.isServer()) {
            System.out.println("Started downloading : " + filename+ " : " + obj.Hash);
            byte[] byt = GetFile(obj.URL);
            if (byt == null) {
                System.out.println("unable to download file: " + filename);
                return;
            }
            try {
                FileOutputStream fos = new FileOutputStream(path);
                fos.write(byt);
                fos.close();
                System.out.println("Finished downloading: " + filename);
            } catch (IOException ignored) {

            }
        }
    }

    public byte[] GetFile(String Addr) {
        byte[] bytes;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(Addr).openConnection();
            conn.setInstanceFollowRedirects(true);
            conn.connect();
            switch (conn.getResponseCode()) {
                case 307:
                case 302:
                    String newURL = conn.getHeaderField("Location");
                    return this.GetFile(newURL);
            }
            bytes = Utils.getBytes(conn.getInputStream());
        } catch (MalformedURLException ex) {
            System.out.println("Malformed URL in index: " + Addr);
            return null;
        } catch (IOException ex) {
            System.out.println("Unable to download mod at: " + Addr);
            System.out.println(ex.getMessage());
            return null;
        }
        return bytes;
    }
}