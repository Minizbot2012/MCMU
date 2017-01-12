package mcmu.downloader.loaders;

import java.io.*;
import java.net.*;

public class IdxLoader extends Loader {
    public IdxLoader(String idxURL) {
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
