package mcmu.impl.downloader.tasks;

import mcmu.impl.downloader.containers.RMOBJ;

import java.io.File;

public class RemoveTask implements Runnable {
    private RMOBJ rmobj;
    public RemoveTask(RMOBJ obj) {
        this.rmobj = obj;
    }

    @Override
    public void run() {
        String FileName = rmobj.Folder + rmobj.FileName + rmobj.Ext;
        File fil = new File(FileName);
        File fild = new File(FileName+".disabled");
        if(fil.exists()) {
            fil.delete();
        }
        if(fild.exists()) {
            fild.delete();
        }
    }
}
