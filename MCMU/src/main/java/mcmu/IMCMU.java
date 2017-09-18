package mcmu;

import com.google.gson.Gson;
import mcmu.api.*;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;

public interface IMCMU {
    Gson getGSON();
    ExecutorService getExecutor();
    HashMap<String, IPlugin> getPlugins();
    Sided getSide();
    void addPlugin(String name, IPlugin plg);
    void run();
}
