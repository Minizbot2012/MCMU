package mcmu;

import com.google.gson.Gson;
import mcmu.downloader.ModLoader;
import mcmu.downloader.containers.*;
import mcmu.downloader.loaders.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class MCMU {
    private static String DLURL;
    public static Sided Side;
    private ConfigFile cnf;
    private ArrayList<Loader> Loaders = new ArrayList<>();
    private MCMU() {
        System.out.println("Loading mods from website in mod-repo.json");
        LoadConfig();
        LoadIndexes();
        loadMods();
    }
    public static void main(String[] args) {
        new MCMU();
    }
    private void LoadConfig() {
        try {
            BufferedReader cfile = new BufferedReader(new FileReader("mod-repo.json"));
            Gson confson = new Gson();
            cnf = confson.fromJson(cfile, ConfigFile.class);
            Side = cnf.Side;
            System.out.println("Side: " + Side);
        } catch (FileNotFoundException FNF) {
            System.out.println("unable to read mod-repo.json, not loading remote files");
        }
    }
    private void LoadIndexes() {
        if(cnf.URL != null) {
            DLURL = cnf.URL;
            Loaders.add(new IdxLoader(DLURL));
        }
        if(cnf.localIDXs != null) {
            LoadLocalIndexes(cnf.localIDXs);
        }
        if(cnf.URLs != null) {
            LoadRemoteIndexes(cnf.URLs);
        }
    }
    private void LoadLocalIndexes(ArrayList<String> indexes) {
        for(String index : indexes) {
            Loaders.add(new LocalIdxLoader(index));
        }
    }
    private void LoadRemoteIndexes(ArrayList<String> indexes) {
        for(String index : indexes) {
            Loaders.add(new IdxLoader(index));
        }
    }
    private void loadMods() {
        ExecutorService tp = Executors.newFixedThreadPool(8);
        for (Loader files : Loaders) {
            tp.execute(new ModLoader(files));
        }
        tp.shutdown();
        while (!tp.isTerminated()) ;
        System.out.println("Files Downloaded");
    }
}