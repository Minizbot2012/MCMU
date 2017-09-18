package mcmu.containers;

import mcmu.api.Sided;

import java.util.*;

public class ConfigFile {
    public HashMap<String, Object> conf = new HashMap<>();
    public String URL;
    public Sided Side = Sided.BOTH;
}