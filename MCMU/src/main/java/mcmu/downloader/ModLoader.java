package mcmu.downloader;

import mcmu.Statics;
import mcmu.api.IPlugin;

public class ModLoader {
    IPlugin plug;
    public ModLoader(IPlugin plugin, Object remote) {
        this.plug = plugin;
        this.plug.recRemote(remote);
        Statics.threadPool.execute(plug.getRunTask());
    }
}
