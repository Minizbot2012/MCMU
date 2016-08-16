package mcmu;

import com.google.gson.Gson;
import mcmu.downloader.ModLoader;
import mcmu.downloader.containers.ConfigFile;
import mcmu.downloader.containers.FileList;
import mcmu.downloader.containers.Sided;
import mcmu.downloader.loaders.IdxLoader;
import mcmu.downloader.loaders.LocalIdxLoader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class MCMU {
    static String DLURL;
    public static Sided Side;
    public static Map<String, FileList> filst = new HashMap<>();
    public static boolean loadRemote = true;

    public MCMU() {
        System.out.println("Loading mods from website in mod-repo.json");
        ConfigFile cnf;
        try {
            BufferedReader cfile = new BufferedReader(new FileReader("mod-repo.json"));
            Gson confson = new Gson();
            cnf = confson.fromJson(cfile, ConfigFile.class);
            DLURL = cnf.URL;
            Side = cnf.Side;
            System.out.println("Side: " + Side);
            IdxLoader idxLoader = new IdxLoader(DLURL);
        } catch (FileNotFoundException FNF) {
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