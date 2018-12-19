package mcmu;

import com.google.gson.Gson;
import mcmu.api.IPlugin;
import mcmu.api.Sided;
import mcmu.containers.FileList;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;

public interface IMCMU {
    Gson getGSON();
    ExecutorService getExecutor();
    HashMap<String, IPlugin> getPlugins();
    Sided getSide();
    void addPlugin(IPlugin plg);
    void run();

    FileList getFileList();
}
