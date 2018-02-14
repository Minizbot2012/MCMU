package mcmu.api;

import mcmu.IMCMU;

public interface IPlugin<L, R> {
    void init(IMCMU mcmu, L params);
    void recRemote(R paramter);
    Runnable getRunTask();
    String getPlugspace();
    Class getRemoteFormat();
    Class getLocalFormat();
}
