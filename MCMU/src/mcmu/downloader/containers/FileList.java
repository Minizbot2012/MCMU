package mcmu.downloader.containers;

import mcmu.downloader.containers.DLOBJ;
import mcmu.downloader.containers.RMOBJ;

import java.util.ArrayList;
import java.util.List;



public class FileList {
    public List<DLOBJ> files = new ArrayList<>();
    public String ConfURL = "";
    public List<RMOBJ> rmfiles = new ArrayList<>();
}
