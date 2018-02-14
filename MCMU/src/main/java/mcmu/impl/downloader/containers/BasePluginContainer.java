package mcmu.impl.downloader.containers;

import mcmu.impl.config.containers.Config;

import java.util.HashMap;
import java.util.List;

public class BasePluginContainer {
    public HashMap<String, DLOBJ> files;
    public List<RMOBJ> rmfiles;
    public Config config;
}
