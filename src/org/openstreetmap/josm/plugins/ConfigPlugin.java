package org.openstreetmap.josm.plugins;

import java.awt.event.KeyEvent;
import javax.swing.*;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.gui.help.HelpUtil;

import static org.openstreetmap.josm.gui.mappaint.mapcss.ExpressionFactory.Functions.tr;

public class ConfigPlugin extends Plugin {

    public ConfigPlugin(PluginInformation info) {
        super(info);
        final JMenu loadTaskMenu = Main.main.menu.addMenu(
                "Task config", tr("Task config"), KeyEvent.VK_K,
                Main.main.menu.getDefaultMenuPos(), HelpUtil.ht("/Plugin/task")
        );
        //loadTaskMenu.add(new JMenuItem(new ConfigLayerAction("Load task from Gist")));
        //menulist(JMenulist);
        LoadTaskConfig loadTaskConfig = new LoadTaskConfig(loadTaskMenu);
        loadTaskConfig.load();
    }
}
