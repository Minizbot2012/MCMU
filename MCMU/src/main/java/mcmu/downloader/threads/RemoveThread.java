package mcmu.downloader.threads;

import mcmu.downloader.containers.*;
import java.io.*;

/**
 * Created by bradl on 2/28/2016.
 */
public class RemoveThread implements Runnable {
    private RMOBJ rmobj;

    public RemoveThread(RMOBJ obj) {
        this.rmobj = obj;
    }

    @Override
    public void run() {
        File rmFile = new File(rmobj.Folder + rmobj.FileName + rmobj.Ext);
        if (rmFile.exists()) {
            rmFile.delete();
        }
    }
}
