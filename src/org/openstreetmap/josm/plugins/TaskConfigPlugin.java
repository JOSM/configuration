package org.openstreetmap.josm.plugins;

import java.awt.event.KeyEvent;
import javax.swing.*;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.gui.help.HelpUtil;
import static org.openstreetmap.josm.gui.mappaint.mapcss.ExpressionFactory.Functions.tr;

public class TaskConfigPlugin extends Plugin {

    public TaskConfigPlugin(PluginInformation info) {
        super(info);

        final JMenu loadTaskComponents = Main.main.menu.addMenu(
                "Task config", tr("Task config"), KeyEvent.VK_K,
                Main.main.menu.getDefaultMenuPos(), HelpUtil.ht("/Plugin/Scripting")
        );
        loadTaskComponents.add(new JMenuItem(new TaskConfigLayerAction("Load task from Gist")));
    }
}
