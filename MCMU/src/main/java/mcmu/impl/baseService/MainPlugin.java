package mcmu.impl.baseService;

import com.google.gson.Gson;
import mcmu.IMCMU;
import mcmu.api.IPlugin;
import mcmu.impl.baseService.containers.BasePluginContainer;
import mcmu.impl.baseService.tasks.MainTask;

public class MainPlugin implements IPlugin {
    //don't have any local configuration
    BasePluginContainer cont;
    IMCMU mcmu;
    @Override
    public void init(IMCMU mcmu, Object params) {
        this.mcmu = mcmu;
    }

    @Override
    public void recRemote(Object downloads) {
        Gson jso = mcmu.getGSON();
        cont = jso.fromJson(jso.toJson(downloads), BasePluginContainer.class);
    }

    @Override
    public Runnable getRunTask() {
        return new MainTask(cont, this.mcmu);
    }

    @Override
    public String getPlugspace() {
        return "base";
    }
}
