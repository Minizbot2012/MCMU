package mcmu.GUI.views;

import mcmu.containers.interfaces.StructEncoder;

import javax.swing.*;
import java.util.HashMap;

public abstract class BaseView<DataStruct, Encoder extends StructEncoder> extends JPanel {
    HashMap<String, JComponent> componentHashMap = new HashMap<>();
    String PanelName;
    DataStruct Info;
    Encoder enc;
    BaseView(String panelName) {
        PanelName = panelName;
    }
}
