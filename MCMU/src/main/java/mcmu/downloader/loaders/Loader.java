package mcmu.downloader.loaders;

import static mcmu.Statics.Json;
import mcmu.downloader.containers.*;
import java.io.*;

/**
 * Created by bradl on 1/9/2017.
 */
public class Loader {
    private FileList files;
    void proc(Reader jso) {
        try {
            files = Json.fromJson(jso, FileList.class);
        } catch (Exception Ex) {
            System.out.println(Ex.getMessage());
        }
    }

    public FileList getFiles() {
        return files;
    }
}
