package mcmu.downloader.containers;

/**
 * Created by bradl on 7/12/2016.
 */
public enum Sided {
    SERVER(false, true), CLIENT(true, false), BOTH(true, true);
    private boolean on_server;
    private boolean on_client;

    Sided(boolean cli, boolean serv) {
        on_client = cli;
        on_server = serv;
    }

    public boolean isServer() {
        return on_server;
    }

    public boolean isClient() {
        return on_client;
    }
}
