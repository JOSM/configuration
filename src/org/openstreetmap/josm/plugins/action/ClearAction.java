package org.openstreetmap.josm.plugins.action;

import org.openstreetmap.josm.actions.JosmAction;
import org.openstreetmap.josm.data.osm.DataSet;
import org.openstreetmap.josm.data.osm.Filter;
import org.openstreetmap.josm.plugins.ConfigPlugin;

import java.awt.event.ActionEvent;
import java.util.List;

import static org.openstreetmap.josm.gui.MainApplication.getMap;

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
        }
        ConfigPlugin.currentLayer.clear();

        List<Filter> existingFilters = getMap().filterDialog.getFilterModel().getFilters();
        for (int i = 0; i < existingFilters.size(); i++) {
            getMap().filterDialog.getFilterModel().removeFilter(i);

        }
        getMap().filterDialog.getFilterModel().executeFilters();

        DataSet ds = getLayerManager().getEditDataSet();
        if (ds != null) {
            ds.addChangeSetTag("source", "");
            ds.addChangeSetTag("comment", "");
        }
    }
}
