package mcmu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mcmu.TypeAdapters.*;
import mcmu.api.*;
import mcmu.containers.*;
import mcmu.downloader.ModLoader;
import mcmu.utils.*;
import sun.plugin2.main.server.Plugin;

import java.io.*;
import java.net.URLClassLoader;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static mcmu.Statics.*;
public class MCMU implements IMCMU {
    public static ConfigFile cnf;
    private FileList flst;
    private HashMap<String, IPlugin> plugs = new HashMap<>();
    ArrayList<ModLoader> mlds;
    public MCMU() {
        mlds = new ArrayList<>();
        run();
        //we are likely being run from MCMU wrapper
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
    public void addPlugin(IPlugin plg) {
        plg.init(this, cnf.conf.getOrDefault(plg.getPlugspace(), null));
        plugs.put(plg.getPlugspace(), plg);
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
            while(PluginsRunning) {
                Iterator<ModLoader> mldi = mlds.iterator();
                while(mldi.hasNext()) {
                    ModLoader mldr = mldi.next();
                    if(mldr.getCompleted()) {
                        mldi.remove();
                    }
                }
                if(mlds.size() == 0) {
                    PluginsRunning = false;
                }
                Thread.sleep(1000);
            }
            Statics.threadPool.shutdown();
            while (!Statics.threadPool.isTerminated()) ;
        } catch (Exception e) {
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
            mlds.add(new ModLoader(IPlug, flst.flst.get(Str)));
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
            Iterator<IPlugin> iPlugins = ServiceLoader.load(IPlugin.class, getClass().getClassLoader()).iterator();
            while (iPlugins.hasNext()) {
                IPlugin plg = iPlugins.next();
                addPlugin(plg);
            }
        } catch(Exception x) {
            //empty catch block, ignore this error.
        }
    }
}