package mcmu.impl.extendedPack;

import mcmu.IMCMU;
import mcmu.api.IPlugin;
import mcmu.impl.downloader.MainPlugin;
import mcmu.impl.downloader.containers.BasePluginContainer;
import mcmu.utils.Utils;

public class PackExt implements IPlugin<Object, String> {
    IMCMU imcmu;
    String remote;
    MainPlugin mainPlugin;
    BasePluginContainer bpcToEx;
    @Override
    public void init(IMCMU mcmu, Object params) {
        this.imcmu = mcmu;
    }

    @Override
    public void recRemote(String paramter) {
        remote = paramter;
    }
    @Override
    public void postInit() {
        mainPlugin = (MainPlugin) imcmu.getPlugins().get("downloader");
        if (remote != null) {
            bpcToEx = imcmu.getGSON().fromJson(Utils.getString(remote), BasePluginContainer.class);
            mainPlugin.mergeLists(bpcToEx);
        }
    }

    @Override
    public Runnable getRunTask() {
        return null;
    }

    @Override
    public String getPlugspace() {
        return "extends";
    }

    @Override
    public Class getRemoteFormat() {
        return String.class;
    }

    @Override
    public Class getLocalFormat() {
        return Object.class;
    }
}
