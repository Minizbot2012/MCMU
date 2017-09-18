package mcmu.impl.baseService.tasks;

import mcmu.IMCMU;
import mcmu.impl.baseService.containers.BasePluginContainer;

import java.io.File;
import java.util.concurrent.ExecutorService;

public class MainTask implements Runnable {
    private BasePluginContainer cont;
    private ExecutorService exeSrv;
    private IMCMU mcmu;
    public MainTask(BasePluginContainer container, IMCMU mcmu) {
        this.mcmu = mcmu;
        this.cont = container;
        this.exeSrv = mcmu.getExecutor();
    }

    @Override
    public void run() {
        if(!(new File("mods/").exists())) {
            new File("mods/").mkdirs();
        }
        if(cont!= null) {
            if(cont.files!=null)
                cont.files.forEach((s, dlobj) -> exeSrv.execute(new DownloadTask(s, dlobj, mcmu)));
            if (cont.rmfiles != null)
                cont.rmfiles.forEach((rmobj) -> exeSrv.execute((new RemoveTask(rmobj))));
            if (cont.config != null)
                exeSrv.execute(new ConfigTask(cont.config));
        }
    }
}
