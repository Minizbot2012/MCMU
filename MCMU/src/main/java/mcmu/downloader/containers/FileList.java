package mcmu.downloader.containers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FileList {
    
    public HashMap<String, DLOBJ> files = new HashMap<>();
    public Config config = new Config();
    public List<RMOBJ> rmfiles = new ArrayList<>();
}
