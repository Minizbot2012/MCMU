package mcmu.downloader.loaders;

import java.io.*;

public class LocalIdxLoader extends Loader {
    public LocalIdxLoader(String FileName) {
        super();
        try {
            BufferedReader cfile = new BufferedReader(new FileReader(FileName));
            proc(cfile);
        } catch (IOException localIOException) {
            System.out.println("File not found, not loading");
        }
    }
}
