package mcmu.impl.config.tasks;

import mcmu.IMCMU;
import mcmu.impl.config.containers.Config;

public class MainTask implements Runnable {
    IMCMU api;
    Config conf;
    public MainTask(IMCMU mcmu, Config cont) {
        this.api = mcmu;
        this.conf = cont;
    }
    @Override
    public void run() {
        if(conf != null) {
            this.api.getExecutor().execute(new ConfigTask(conf));
        }
    }
}
