package org.openstreetmap.josm.plugins;

import java.awt.event.KeyEvent;
import javax.swing.*;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.gui.MainMenu;
import static org.openstreetmap.josm.tools.I18n.marktr;
import org.openstreetmap.josm.gui.help.HelpUtil;

public class TaskConfigPlugin extends Plugin {

    public TaskConfigPlugin(PluginInformation info) {
        super(info);
        MainMenu menu = Main.main.menu;
        JMenu loadTaskComponents = menu.addMenu(marktr("Task Components"), KeyEvent.VK_K, menu.getDefaultMenuPos(), HelpUtil.ht("/Plugin/task"));
        loadTaskComponents.add(new JMenuItem(new TaskConfigLayerAction("Load task from Gist")));
    }
}
