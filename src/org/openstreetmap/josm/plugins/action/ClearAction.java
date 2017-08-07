package org.openstreetmap.josm.plugins.action;

import java.awt.event.ActionEvent;
import java.util.List;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.actions.JosmAction;
import org.openstreetmap.josm.data.osm.DataSet;
import org.openstreetmap.josm.data.osm.Filter;
import org.openstreetmap.josm.plugins.ConfigPlugin;

/**
 * Created by aarthychandrasekhar on 02/12/15.
 */
public class ClearAction extends JosmAction {


    public ClearAction(String name){
        super(name, null, name, null, true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for (int i=0; i< ConfigPlugin.currentLayer.size(); i++){
            getLayerManager().removeLayer(ConfigPlugin.currentLayer.get(i));

           // new Notification("yahallo" + Main.pref.getCollection("mappaint.style.entries")).show();
        }

        List<Filter> existingFilters = Main.map.filterDialog.getFilterModel().getFilters();
        for (int i = 0; i < existingFilters.size(); i++) {
            Main.map.filterDialog.getFilterModel().removeFilter(i);

        }
        Main.map.filterDialog.getFilterModel().executeFilters();

        DataSet ds = getLayerManager().getEditDataSet();
        if (ds != null) {
            ds.addChangeSetTag("source", "");
            ds.addChangeSetTag("comment", "");
        }
    }
}
