package mcmu.impl.downloader;

import mcmu.IMCMU;
import mcmu.api.IPlugin;
import mcmu.impl.downloader.containers.BasePluginContainer;
import mcmu.impl.downloader.tasks.MainTask;

public class MainPlugin implements IPlugin<Object, BasePluginContainer> {
    //don't have any local configuration
    BasePluginContainer cont;
    IMCMU mcmu;
    @Override
    public void init(IMCMU mcmu, Object params) {
        this.mcmu = mcmu;
    }

    @Override
    public void recRemote(BasePluginContainer downloads) {
        cont = downloads;
    }

    @Override
    public Runnable getRunTask() {
        return new MainTask(cont, this.mcmu);
    }

    @Override
    public String getPlugspace() {
        return "downloader";
    }

    @Override
    public Class getRemoteFormat() {
        return BasePluginContainer.class;
    }

    @Override
    public Class getLocalFormat() {
        return null;
    }
}
