package mcmu.downloader.loaders;

import com.google.gson.Gson;
import mcmu.MCMU;
import mcmu.downloader.containers.FileList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class IdxLoader {
    public IdxLoader(String idxURL) {
        try {
            Gson json = new Gson();
            BufferedReader cfile = new BufferedReader(new InputStreamReader(new URL(idxURL).openStream()));
            try {
                MCMU.filst.put("remote", json.fromJson(cfile, FileList.class));
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } catch (MalformedURLException ex) {
            System.out.println("Malformed URL for download: " + idxURL);
        } catch (IOException ex) {
            System.out.println("Issue reading file");
        }
    }
}
