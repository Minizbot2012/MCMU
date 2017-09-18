package mcmu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mcmu.TypeAdapters.*;
import mcmu.api.CompatOverride;
import mcmu.api.Sided;
import mcmu.api.IPlugin;
import mcmu.containers.*;
import mcmu.downloader.ModLoader;
import mcmu.utils.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;

import static mcmu.Statics.*;
public class MCMU implements IMCMU {
    public static ConfigFile cnf;
    private FileList flst;
    private HashMap<String, IPlugin> plugs = new HashMap<>();
    public MCMU() {
        run();
    }

    @Override
    public Gson getGSON() {
        return Statics.Json;
    }

    @Override
    public ExecutorService getExecutor() {
        return Statics.threadPool;
    }

    @Override
    public HashMap<String, IPlugin> getPlugins() {
        return plugs;
    }

    @Override
    public void addPlugin(String n, IPlugin plg) {
        plg.init(this, cnf.conf.getOrDefault(n, null));
        plugs.put(n, plg);
    }
    public Sided getSide() {
        return cnf.Side;
    }
    public void run() {
        try {
            initializeGson();
            loadConfig();
            loadPlugins();
            loadURL();
            runPlugins();
            Thread.sleep(4000);
            Statics.threadPool.shutdown();
            while(!Statics.threadPool.isTerminated());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void loadURL() {
        flst = Json.fromJson(Utils.getString(cnf.URL), FileList.class);
    }
    public static void main(String[] args) {
        new MCMU();
    }
    private void runPlugins() {
        plugs.forEach((Str,IPlug) -> {
            System.out.println(Str);
            new ModLoader(IPlug, flst.flst.get(Str));
        });
    }
    private void initializeGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Sided.class, new EnumTypeAdapter());
        builder.registerTypeAdapter(CompatOverride.class, new EnumTypeAdapter());
        Json = builder.create();
    }
    private void loadConfig() {
        try {
            BufferedReader cfile = new BufferedReader(new FileReader("mod-repo.json"));
            cnf = Json.fromJson(cfile, ConfigFile.class);
            Side = cnf.Side;
            System.out.println("Side: " + Side);
        } catch (FileNotFoundException FNF) {
            System.out.println("unable to read mod-repo.json, not loading remote files");
        }
    }
    private void loadPlugins() {
        try {
            for (IPlugin plug : ServiceLoader.load(IPlugin.class)) {
                addPlugin(plug.getPlugspace(), plug);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}