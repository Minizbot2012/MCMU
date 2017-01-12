package mcmu.downloader.loaders;

import com.google.gson.*;
import mcmu.downloader.containers.*;
import java.util.*;
import java.io.*;

/**
 * Created by bradl on 1/9/2017.
 */
public class Loader {
    private Gson gson;
    private FileList files;
    Loader() {
        gson = new Gson();
    }
    void proc(Reader jso) {
        try {
            files = gson.fromJson(jso, FileList.class);
        } catch (Exception Ex) {
            System.out.println(Ex.getMessage());
        }
    }

    public FileList getFiles() {
        return files;
    }
}
