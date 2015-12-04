package org.openstreetmap.josm.plugins;

import org.openstreetmap.josm.plugins.util.TaskLayer;
import org.openstreetmap.josm.plugins.action.AddNewConfigTaskAction;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.*;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.gui.help.HelpUtil;
import org.openstreetmap.josm.gui.preferences.SourceEntry;

import static org.openstreetmap.josm.gui.mappaint.mapcss.ExpressionFactory.Functions.tr;

public class ConfigPlugin extends Plugin {


    static ArrayList<SourceEntry> currentMappaint = new ArrayList<>();

    public ConfigPlugin(PluginInformation info) {
        super(info);
        final JMenu loadTaskMenu = Main.main.menu.addMenu(
                "Task config", tr("Task config"), KeyEvent.VK_K,
                Main.main.menu.getDefaultMenuPos(), HelpUtil.ht("/Plugin/task")
        );
        //add  new config task - Action
        loadTaskMenu.add(new JMenuItem(new AddNewConfigTaskAction("Load task from URL")));
        loadTaskMenu.add(new JSeparator());
        //  default configuration task
        LoadTaskConfig loadTaskConfig = new LoadTaskConfig(loadTaskMenu);
        loadTaskConfig.load();
        // clean configuration action
        loadTaskMenu.add(new JSeparator());
        loadTaskMenu.add(new JMenuItem(new ClearAction("Clear")));
    }
}
