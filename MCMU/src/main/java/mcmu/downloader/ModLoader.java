package mcmu.downloader;

import com.sun.org.apache.xpath.internal.operations.Bool;
import mcmu.Statics;
import mcmu.api.IPlugin;

import java.util.concurrent.Future;

public class ModLoader {
    IPlugin plug;
    Future completed;
    public ModLoader(IPlugin plugin, Object remote) {
        this.plug = plugin;
        this.plug.recRemote(remote);
        completed = Statics.threadPool.submit(plug.getRunTask());
    }
    public boolean getCompleted() {
        return completed.isDone();
    }
}
