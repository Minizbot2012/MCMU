package mcmu.downloader;

import mcmu.downloader.containers.*;
import mcmu.downloader.threads.*;
import mcmu.downloader.loaders.*;

import java.util.Map.Entry;
import java.util.concurrent.*;

public class ModLoader implements Runnable {
    private FileList files;
    public ModLoader(Loader loader) {
        this.files = loader.getFiles();
    }
    @Override
    public void run() {
        ExecutorService executor = Executors.newFixedThreadPool(8);
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
        executor.shutdown();
        while (!executor.isTerminated()) ;
    }
}
