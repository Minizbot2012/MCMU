package mcmu;

import com.google.gson.Gson;
import mcmu.api.Sided;

import java.util.concurrent.*;

/**
 * Created by bradl on 1/12/2017.
 */
public class Statics {
    public static Gson Json;
    public static Sided Side;
    public static boolean Restart;
    public static ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    public static boolean PluginsRunning = true;
}
