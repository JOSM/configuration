package org.openstreetmap.josm.plugins.config;

import java.util.ArrayList;
import java.util.List;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.data.osm.Filter;
import org.openstreetmap.josm.gui.Notification;
import org.openstreetmap.josm.gui.dialogs.FilterTableModel;
import org.openstreetmap.josm.plugins.util.Print;

/**
 *
 * @author ruben
 */
public class FilterConfig {

//    FilterTableModel filterTableModel = Main.map.filterDialog.getFilterModel();
//    List<Filter> existingFilters = filterTableModel.getFilters();
    public void add_filter(BeanConfig bc) {
        //Print.print(previous_filter + "-" + actual_filter);

        List<Filter> filterList = new ArrayList<>();
        new Notification("Filter:" + bc.getActual_filters()).show();
        Filter f1 = new Filter();
        f1.text = bc.getActual_filters();
        f1.hiding = true;
        filterList.add(f1);

        FilterTableModel filterTableModel = Main.map.filterDialog.getFilterModel();
        List<Filter> existingFilters = filterTableModel.getFilters();
        //Remove if exist previus configuration?
        clear_filter(bc.getPrevious_filters());
        //Add filter
        for (Filter f : filterList) {
            filterTableModel.addFilter(f);
        }
        filterTableModel.executeFilters();

        bc.setPrevious_filters(bc.getActual_filters());
    }

    public void clear_filter(String filters) {
        FilterTableModel filterTableModel = Main.map.filterDialog.getFilterModel();
        List<Filter> existingFilters = filterTableModel.getFilters();
        //Remove if exist previous configuration?
        if (filters != null) {
            for (int i = 0; i < existingFilters.size(); i++) {
                System.err.println(existingFilters.get(i).text);
                if (existingFilters.get(i).text.equals(filters)) {
                    filterTableModel.removeFilter(i);
                }
            }
        }
    }

}
