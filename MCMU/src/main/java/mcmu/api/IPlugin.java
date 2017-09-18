package mcmu.api;

import mcmu.IMCMU;

public interface IPlugin {
    void init(IMCMU mcmu, Object params);
    void recRemote(Object downloads);
    Runnable getRunTask();
    String getPlugspace();
}
