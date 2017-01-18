package mcmu.downloader.containers;

/**
 * Created by bradl on 7/12/2016.
 */
public enum Sided {
    SERVER, CLIENT, BOTH;
    public boolean isServer() {
        return this == SERVER || this == CLIENT;
    }

    public boolean isClient() {
        return this == CLIENT || this == BOTH;
    }
}
