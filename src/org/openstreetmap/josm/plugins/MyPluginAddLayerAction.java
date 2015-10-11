package org.openstreetmap.josm.plugins;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.actions.JosmAction;
import org.openstreetmap.josm.data.Preferences;
import org.openstreetmap.josm.data.imagery.ImageryInfo;
import org.openstreetmap.josm.gui.MapView;
import org.openstreetmap.josm.tools.Shortcut;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.util.prefs.PreferenceChangeListener;

/**
 * Created by aarthychandrasekhar on 09/10/15.
 */
public class MyPluginAddLayerAction extends JosmAction {
    TaskLayer taskLayer;
    public MyPluginAddLayerAction() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String layer = "http://cyberjapandata.gsi.go.jp/xyz/ort/{z}/{x}/{y}.jpg";
        String layerName = "Japan GSI";
        ImageryInfo imageryInfo = new ImageryInfo();
        imageryInfo.setUrl(layer);
        imageryInfo.setName(layerName);

        if(taskLayer != null) {Main.main.removeLayer(taskLayer);}

        taskLayer = new TaskLayer(imageryInfo);
        Main.main.addLayer(taskLayer);
    }
}
