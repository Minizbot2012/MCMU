package mcmu.downloader.loaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class LocalIdxLoader extends Loader {
    private LocalIdxLoader(String FileName, String ID) {
        super();
        try {
            BufferedReader cfile = new BufferedReader(new FileReader(FileName));
            proc(cfile);
        } catch (IOException localIOException) {
            System.out.println("File not found, not loading");
        }
    }
}
