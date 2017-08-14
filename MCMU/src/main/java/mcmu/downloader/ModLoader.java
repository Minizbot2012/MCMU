package mcmu.downloader;

import mcmu.containers.*;
import mcmu.downloader.loaders.Loader;
import mcmu.downloader.threads.*;

import java.util.Map.Entry;
import java.util.concurrent.*;

public class ModLoader {
    private FileList files;
    public ModLoader(Loader loader) {
        this.files = loader.getFiles();
        run();
    }
    public void run() {
        ExecutorService executor = Executors.newFixedThreadPool(8);
        if (!files.config.URL.equals("")) {
            executor.execute(new ConfigThread(files.config));
        }
        for (Entry<String, DLOBJ> fil: files.files.entrySet()) {
            Runnable worker = new DownloadThread(fil.getKey(), fil.getValue());
            executor.execute(worker);
        }
        for (RMOBJ fil : files.rmfiles) {
            Runnable worker = new RemoveThread(fil);
            executor.execute(worker);
        }
        executor.shutdown();
        while (!executor.isTerminated()) ;
    }
}
