package mcmu.impl.config;

import mcmu.IMCMU;
import mcmu.api.IPlugin;
import mcmu.impl.config.containers.Config;
import mcmu.impl.config.tasks.MainTask;

public class ConfigPlugin implements IPlugin<Object, Config> {

    IMCMU mcmu;
    Config conf;
    @Override
    public void init(IMCMU mcmu, Object params) {
        this.mcmu = mcmu;
    }

    @Override
    public void recRemote(Config downloads) {
        conf = downloads;
    }

    @Override
    public Runnable getRunTask() {
        return new MainTask(this.mcmu, conf);
    }

    @Override
    public String getPlugspace() {
        return "config";
    }

    @Override
    public Class getRemoteFormat() {
        return Config.class;
    }

    @Override
    public Class getLocalFormat() {
        return Object.class;
    }

    @Override
    public void postInit() {

    }
}
