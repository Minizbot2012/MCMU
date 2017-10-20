package mcmu.api;

import com.sun.org.apache.xpath.internal.operations.Bool;
import mcmu.IMCMU;

import java.util.concurrent.Callable;

public interface IPlugin {
    void init(IMCMU mcmu, Object params);
    void recRemote(Object downloads);
    Runnable getRunTask();
    String getPlugspace();
}
