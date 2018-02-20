package mcmu.downloader;

import mcmu.Statics;
import mcmu.api.IPlugin;

import java.util.concurrent.Future;

public class ModLoader {
    IPlugin plug;
    Future completed;
    boolean alt;
    public ModLoader(IPlugin plugin, Object remote) {
        this.plug = plugin;
        this.plug.recRemote(Statics.Json.fromJson(Statics.Json.toJson(remote), plug.getRemoteFormat()));
    }
    public boolean getCompleted() {
        if(completed != null) {
            return completed.isDone();
        } else {
            return true;
        }
    }
    public void run() {
        Runnable tsk = plug.getRunTask();
        if(tsk != null) {
            completed = Statics.threadPool.submit(tsk);
        }
    }
    public void postInit() {
        plug.postInit();
    }
}
