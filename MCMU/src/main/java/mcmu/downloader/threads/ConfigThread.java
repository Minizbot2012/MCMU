package mcmu.downloader.threads;

import mcmu.containers.Config;
import mcmu.utils.Utils;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.logging.*;
import java.util.zip.*;

public class ConfigThread implements Runnable {
    private Config conf;
    private String ConfFileName;

    public ConfigThread(Config conf) {
        this.conf = conf;
    }

    private String dirpart(String name) {
        int s = name.lastIndexOf(File.separatorChar);
        return s == -1 ? "" : name.substring(0, s);
    }

    @Override
    public void run() {
        try {
            if(conf.ID != null) {
                ConfFileName = "config-"+conf.ID+".zip";
            } else {
                ConfFileName = "config.zip";
            }
            File fl = new File(ConfFileName);
            System.out.println("Downloading Configs");
            if (fl.exists()) {
                FileInputStream fis = new FileInputStream(fl);
                String hash = Utils.MD5(fis);
                if (hash.equals(conf.Hash.getValue())) {
                    System.out.println("Configs already up to date");
                    return;
                }
            }
            BufferedInputStream confbuf = new BufferedInputStream(new URL(conf.URL.getValue()).openStream());
            byte[] byt = Utils.getBytes(confbuf);
            confbuf.close();
            File confzip = new File(ConfFileName);
            FileOutputStream fos = new FileOutputStream(confzip);
            fos.write(byt);
            fos.close();
            ZipFile zip = new ZipFile(confzip);
            Enumeration e = zip.entries();
            while (e.hasMoreElements()) {
                ZipEntry ent = (ZipEntry) e.nextElement();
                InputStream is = zip.getInputStream(ent);
                byte[] byts = Utils.getBytes(is);
                File dir = new File(dirpart(("config/" + ent.getName()).replace("/", File.separator)));
                dir.mkdirs();
                if (!ent.isDirectory()) {
                    BufferedOutputStream cofos = new BufferedOutputStream(new FileOutputStream("config/" + ent.getName()));
                    cofos.write(byts);
                    cofos.close();
                }
            }
            System.out.println("Configs updated");
        } catch (IOException ex) {
            Logger.getLogger(ConfigThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
