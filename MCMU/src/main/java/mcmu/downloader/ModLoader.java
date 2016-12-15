package mcmu.downloader;

import mcmu.MCMU;
import mcmu.downloader.containers.*;
import mcmu.downloader.threads.*;

import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ModLoader {
    public ModLoader() {
        ExecutorService executor = Executors.newFixedThreadPool(8);
        for (FileList files : MCMU.filst.values()) {
            if (!files.config.URL.equals("")) {
                executor.execute(new ConfigThread(files.config.URL, files.config.ID));
            }
            for (Entry<String, DLOBJ> fil: files.files.entrySet()) {
                Runnable worker = new DownloadThread(fil.getKey(), fil.getValue());
                executor.execute(worker);
            }
            for (RMOBJ fil : files.rmfiles) {
                Runnable worker = new RemoveThread(fil);
                executor.execute(worker);
            }
        }
        executor.shutdown();
        while (!executor.isTerminated()) ;
        System.out.println("Files Downloaded");
    }
}
