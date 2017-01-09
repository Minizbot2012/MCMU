package mcmu.downloader.loaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class IdxLoader extends Loader {
    public IdxLoader(String ID, String idxURL) {
        super();
        try {
            BufferedReader cfile = new BufferedReader(new InputStreamReader(new URL(idxURL).openStream()));
            proc(cfile);
        } catch (MalformedURLException ex) {
            System.out.println("Malformed URL for download: " + idxURL);
        } catch (IOException ex) {
            System.out.println("Issue reading file");
        }
    }
}
