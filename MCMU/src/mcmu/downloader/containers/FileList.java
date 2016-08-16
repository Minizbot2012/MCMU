package mcmu.downloader.containers;

import java.util.ArrayList;
import java.util.List;


public class FileList {
    public List<DLOBJ> files = new ArrayList<>();
    public Config config = new Config();
    public List<RMOBJ> rmfiles = new ArrayList<>();
}
