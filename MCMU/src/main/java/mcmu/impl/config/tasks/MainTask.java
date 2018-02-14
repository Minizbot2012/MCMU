package mcmu.impl.config.tasks;

import mcmu.IMCMU;
import mcmu.impl.config.containers.Config;

public class MainTask implements Runnable {
    IMCMU api;
    Config conf;
    public MainTask(IMCMU mmu, Config cont) {
        this.api = mmu;
    }
    @Override
    public void run() {
        if(conf != null) {
            this.api.getExecutor().submit(new ConfigTask(conf));
        }
    }
}
