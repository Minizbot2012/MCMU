package mcmu;

import com.google.gson.Gson;
import mcmu.downloader.ModLoader;
import mcmu.downloader.containers.ConfigFile;
import mcmu.downloader.containers.FileList;
import mcmu.downloader.containers.Sided;
import mcmu.downloader.loaders.IdxLoader;
import mcmu.downloader.loaders.Loader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MCMU {
    private static String DLURL;
    public static Sided Side;
    private static Map<String, FileList> filst = new HashMap<>();

    private MCMU() {
        System.out.println("Loading mods from website in mod-repo.json");
        ConfigFile cnf;
        ArrayList<Loader> Loaders = new ArrayList<>();
        try {
            BufferedReader cfile = new BufferedReader(new FileReader("mod-repo.json"));
            Gson confson = new Gson();
            cnf = confson.fromJson(cfile, ConfigFile.class);
            DLURL = cnf.URL;
            Side = cnf.Side;
            System.out.println("Side: " + Side);
            Loaders.add(new IdxLoader("remote", DLURL));
            filst.put("remote", Loaders.get(0).getFiles());
            if(cnf.URLs != null) {
                for(Map.Entry<String, String> ent: cnf.URLs.entrySet()) {
                    Loaders.add(new IdxLoader(ent.getKey(), ent.getValue()));
                    filst.put(ent.getKey(), Loaders.get(Loaders.size() - 1).getFiles());
                }
            }
            if(cnf.localIDXs != null) {
                for (Map.Entry<String, String> ent : cnf.localIDXs.entrySet()) {
                    Loaders.add(new IdxLoader(ent.getKey(), ent.getValue()));
                    filst.put(ent.getKey(), Loaders.get(Loaders.size() - 1).getFiles());
                }
            }
        } catch (FileNotFoundException FNF) {
            System.out.println("unable to read mod-repo.json, not loading remote files");
        }
        ExecutorService tp = Executors.newFixedThreadPool(8);
        for(Map.Entry<String, FileList> files : filst.entrySet()) {
            tp.execute(new ModLoader(files.getValue()));
        }
        tp.shutdown();
        while(!tp.isTerminated());
        System.out.println("Files Downloaded");
    }

    public static void main(String[] args) {
        new MCMU();
    }
}