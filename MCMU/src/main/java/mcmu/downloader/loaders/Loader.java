package mcmu.downloader.loaders;

import com.google.gson.*;
import mcmu.downloader.containers.*;

import java.io.Reader;

/**
 * Created by bradl on 1/9/2017.
 */
public class Loader {
    private Gson gson = new Gson();
    private String ID;
    private FileList files;
    public Loader(String id) {
        this.ID = ID;
    }
    public void proc(Reader jso) {
        try {
            files = gson.fromJson(jso, FileList.class);
        } catch (Exception Ex) {
            System.out.println(Ex.getMessage());
        }
    }

    public FileList getFiles() {
        return files;
    }

    public String getID() {
        return ID;
    }
}
