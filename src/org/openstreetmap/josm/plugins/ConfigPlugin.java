package org.openstreetmap.josm.plugins;

import org.openstreetmap.josm.plugins.config.JOSMConfig;
import org.openstreetmap.josm.plugins.util.TaskLayer;
import org.openstreetmap.josm.plugins.action.AddNewConfigTaskAction;
import java.awt.event.KeyEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.gui.help.HelpUtil;
import org.openstreetmap.josm.gui.preferences.SourceEntry;

import static org.openstreetmap.josm.gui.mappaint.mapcss.ExpressionFactory.Functions.tr;

public class ConfigPlugin extends Plugin {
    public static ArrayList<JOSMConfig> josmConfigs = new ArrayList<>();
    public static JMenu loadTaskMenu;

    public ConfigPlugin(PluginInformation info) {
        super(info);
         loadTaskMenu = Main.main.menu.addMenu(
                "Task config", tr("Task config"), KeyEvent.VK_K,
                Main.main.menu.getDefaultMenuPos(), HelpUtil.ht("/Plugin/task")
        );
        //add  new config task - Action
        loadTaskMenu.add(new JMenuItem(new AddNewConfigTaskAction("Load task from URL")));
        loadTaskMenu.add(new JSeparator());

        // clean configuration action
        loadTaskMenu.add(new JSeparator());
        loadTaskMenu.add(new JMenuItem(new ClearAction("Clear")));

        //  default configuration task
         josmConfigs.addAll(LoadTaskConfig.load());
        for(int j=0; j<josmConfigs.size(); j++){
            loadTaskMenu.add(new JMenuItem(josmConfigs.get(j)));
        }

    }

    public static  void addActionToMenu(JOSMConfig josmConfig){
     loadTaskMenu.add(new JMenuItem(josmConfig));
    }
}
