package mcmu.impl.downloader.containers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BasePluginContainer {
    public HashMap<String, DLOBJ> files;
    public List<RMOBJ> rmfiles;
    public BasePluginContainer() {
        files = new HashMap<>();
        rmfiles = new ArrayList<>();
    }
    public void mergeList(BasePluginContainer JSON) {
        JSON.files.forEach((String si, DLOBJ obj) -> {
            files.put(si, obj);
        });
    }
}
