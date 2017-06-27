package mcmu.downloader.loaders;

import mcmu.MCMU;
import mcmu.Statics;
import mcmu.downloader.containers.ConfigFile;

import java.io.*;
import java.net.*;

public class IdxLoader extends Loader {
    public IdxLoader(String idxURL) {
        super();
        try {
            BufferedReader cfile = new BufferedReader(new InputStreamReader(new URL(idxURL).openStream()));
            proc(cfile);
            if(this.getFiles().confUpdate != null) {
                System.out.println("A new pack configuration (mod-repo.json) is being installed");
                FileWriter file = new FileWriter(new File("mod-repo.json"));
                if(this.getFiles().confUpdate.URL!=null) {
                    MCMU.cnf.URL=this.getFiles().confUpdate.URL;
                }
                if(this.getFiles().confUpdate.URLs!=null) {
                    MCMU.cnf.URLs = this.getFiles().confUpdate.URLs;
                }
                file.write(Statics.Json.toJson(MCMU.cnf));
                file.close();
                //restart the process
                Statics.Restart=true;
            }
        } catch (MalformedURLException ex) {
            System.out.println("Malformed URL for download: " + idxURL);
        } catch (IOException ex) {
            System.out.println("Issue reading file");
        }
    }
}
