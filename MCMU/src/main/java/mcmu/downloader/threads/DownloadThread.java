package mcmu.downloader.threads;

import mcmu.downloader.containers.*;
import mcmu.utils.Utils;
import java.io.*;
import java.net.*;

import static mcmu.Statics.Side;
/**
 * Created by bradl on 2/20/2016.
 */
public class DownloadThread implements Runnable {
    private DLOBJ downloadInf;
    private String filename;
    public DownloadThread(String FN, DLOBJ download) {
        downloadInf = download;
        filename = FN;
    }

    @Override
    public void run() {
        saveFile(downloadInf);
    }

    private void saveFile(DLOBJ obj) {
        String path = obj.Folder + filename + obj.Ext;
        File folder = new File(obj.Folder);
        File flDisabled = new File(path + ".disabled");
        File fl;
        if (flDisabled.exists()) {
            fl = new File(path + ".disabled");
            if (obj.Override == CompatOverride.ENABLE) {
                fl.renameTo(new File(path));
                fl = new File(path);
            } else {
                return;
            }
        } else {
            fl = new File(path);
            if (obj.Override == CompatOverride.DISABLE) {
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
            } catch (IOException ignored) {
            }
        }
        if (obj.Side.isClient() == Side.isClient() || obj.Side.isServer() == Side.isServer()) {
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

    private byte[] GetFile(String Addr) {
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
