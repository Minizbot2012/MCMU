package mcmu.downloader.threads;

import mcmu.containers.*;
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
        File rmFile = new File(rmobj.Folder.getValue() + rmobj.FileName.getValue() + rmobj.Ext.getValue());
        if (rmFile.exists()) {
            rmFile.delete();
        }
    }
}
