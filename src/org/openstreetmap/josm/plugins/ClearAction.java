package org.openstreetmap.josm.plugins;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.actions.JosmAction;
import org.openstreetmap.josm.data.osm.Filter;
import org.openstreetmap.josm.gui.MapView;
import org.openstreetmap.josm.gui.Notification;
import org.openstreetmap.josm.gui.mappaint.MapPaintStyles;
import org.openstreetmap.josm.gui.mappaint.StyleSource;
import org.openstreetmap.josm.gui.preferences.SourceEditor;
import org.openstreetmap.josm.gui.preferences.map.MapPaintPreference;
import org.openstreetmap.josm.plugins.ConfigPlugin;
import org.openstreetmap.josm.plugins.config.BeanConfig;

import java.awt.event.ActionEvent;
import java.util.Collection;
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
            }

            List<Filter> existingFilters = Main.map.filterDialog.getFilterModel().getFilters();
            for (int k = 0; k < existingFilters.size(); k++) {
                if(BeanConfig.actual_filters.contains(existingFilters.get(k).text)) {
                    Main.map.filterDialog.getFilterModel().removeFilter(k--);
                }
            }

            Main.map.filterDialog.getFilterModel().executeFilters();



            List<StyleSource> existingMapPaintStyleSources = MapPaintStyles.getStyles().getStyleSources();
            for (int k = 0; k < existingMapPaintStyleSources.size(); k++) {
//                if(BeanConfig.mappaints.contains(existingMapPaintStyleSources.get(k).url)) {
//            }
            }

            Main.main.getCurrentDataSet().addChangeSetTag("source", "");
            Main.main.getCurrentDataSet().addChangeSetTag("comment", "");
        }
        catch (Exception ex){
            new Notification("Exception" + ex);
        }

    }
    
    
}
