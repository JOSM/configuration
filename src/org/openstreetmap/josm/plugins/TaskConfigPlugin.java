package org.openstreetmap.josm.plugins;

import java.awt.event.KeyEvent;
import javax.swing.*;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.gui.MainMenu;
import org.openstreetmap.josm.gui.help.HelpUtil;
import static org.openstreetmap.josm.gui.mappaint.mapcss.ExpressionFactory.Functions.tr;
import static org.openstreetmap.josm.tools.I18n.marktr;

public class TaskConfigPlugin extends Plugin {

    public TaskConfigPlugin(PluginInformation info) {
        super(info);
        
         final JMenu scriptingMenu = Main.main.menu.addMenu(
                "Task-config", tr("Task-config"), -1 /* no mnemonic key */,
                Main.main.menu.getDefaultMenuPos(), ht("/Plugin/Scripting")
        );
         
         
        MainMenu menu = Main.main.menu;
        JMenu loadTaskComponents = menu.addMenu(marktr("Task Components"), KeyEvent.VK_K, menu.getDefaultMenuPos(), HelpUtil.ht("/Plugin/task"));
        loadTaskComponents.add(new JMenuItem(new TaskConfigLayerAction("Load task from Gist")));
    }

    protected void installScriptsMenu() {
        final JMenu scriptingMenu = Main.main.menu.addMenu(
                "Scripting", tr("Scripting"), -1 /* no mnemonic key */,
                Main.main.menu.getDefaultMenuPos(), ht("/Plugin/Scripting")
        );
        scriptingMenu.setMnemonic('S');
        MostRecentlyRunScriptsModel.getInstance().loadFromPreferences(Main.pref);
        populateStandardentries(scriptingMenu);
        populateMruMenuEntries(scriptingMenu);
        MostRecentlyRunScriptsModel.getInstance().addObserver(new Observer() {
            @Override
            public void update(Observable arg0, Object arg1) {
                scriptingMenu.removeAll();
                populateStandardentries(scriptingMenu);
                populateMruMenuEntries(scriptingMenu);
            }

        }
