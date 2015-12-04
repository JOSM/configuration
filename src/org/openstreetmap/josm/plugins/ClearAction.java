package org.openstreetmap.josm.plugins;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.actions.JosmAction;
import org.openstreetmap.josm.data.osm.Filter;
import org.openstreetmap.josm.gui.MapView;
import org.openstreetmap.josm.gui.Notification;
import org.openstreetmap.josm.plugins.ConfigPlugin;
import org.openstreetmap.josm.plugins.config.BeanConfig;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by aarthychandrasekhar on 02/12/15.
 */
public class ClearAction extends JosmAction {


    public ClearAction(String name){
        super(name, null, name, null, true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            for (int i = 0; i < BeanConfig.currentLayer.size(); i++) {
                Main.main.removeLayer(BeanConfig.currentLayer.get(i));

                // new Notification("yahallo" + Main.pref.getCollection("mappaint.style.entries")).show();
            }

            List<Filter> existingFilters = Main.map.filterDialog.getFilterModel().getFilters();
            for (int i = 0; i < existingFilters.size(); i++) {
                Main.map.filterDialog.getFilterModel().removeFilter(i);

            }
            Main.map.filterDialog.getFilterModel().executeFilters();

            Main.main.getCurrentDataSet().addChangeSetTag("source", "");
            Main.main.getCurrentDataSet().addChangeSetTag("comment", "");
        }
        catch (Exception ex){
            new Notification("Exception" + ex);
        }

    }
}
