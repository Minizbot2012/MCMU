package mcmu.containers;

import java.util.*;


public class FileList {
    public HashMap<String, DLOBJ> files = new HashMap<>();
    public Config config = new Config();
    public List<RMOBJ> rmfiles = new ArrayList<>();
    public ConfigFile confUpdate;
}
