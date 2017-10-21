package mcmu.impl.baseService.tasks;

import mcmu.impl.baseService.containers.Config;
import mcmu.utils.Utils;

import java.io.*;
import java.net.MalformedURLException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ConfigTask implements Runnable {
    private Config conf;
    public ConfigTask(Config cnf) {
        this.conf = cnf;
    }
    private String dirpart(String name) {
        int s = name.lastIndexOf(File.separatorChar);
        return s == -1 ? "" : name.substring(0, s);
    }
    @Override
    public void run() {
        System.out.println("Downloading configs");
        File cnffolder = new File("config/");
        if(!cnffolder.exists()) {
            cnffolder.mkdirs();
        }
        File cnf = new File("Config-bp.zip");
        if(cnf.exists()) {
            try {
                if(conf.Hash.equals(Utils.MD5(new FileInputStream(cnf)))) {
                    return;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream writer = new FileOutputStream(cnf);
            writer.write(Utils.getFile(conf.URL));
            writer.close();
            ZipFile zip = new ZipFile(cnf);
            Enumeration e = zip.entries();
            while(e.hasMoreElements()) {
                ZipEntry ent = (ZipEntry) e.nextElement();
                if(!ent.isDirectory()) {
                    InputStream is = zip.getInputStream(ent);
                    FileOutputStream fos = new FileOutputStream(new File("config/"+ent.getName()));
                    fos.write(Utils.getBytes(is));
                    fos.close();
                } else {
                    new File("config/"+ent.getName()).mkdirs();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
