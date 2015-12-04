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

    public static void setup_filter(String filters) {
        Print.print(filters);
        List<Filter> filterList = new ArrayList<>();
        Filter f1 = new Filter();
        f1.text = filters;
        f1.hiding = true;
        filterList.add(f1);

        FilterTableModel filterTableModel = Main.map.filterDialog.getFilterModel();
        List<Filter> existingFilters = filterTableModel.getFilters();

        //Remove if exist previus configuration?
        String previus_filter = BeanConfig.filters;
        if (BeanConfig.filters != null) {
            for (int i = 0; i < existingFilters.size(); i++) {
                System.err.println(existingFilters.get(i).text);
                if (existingFilters.get(i).text.equals(previus_filter)) {
                    filterTableModel.removeFilter(i);
                }
            }
        }
        //Add filter
        for (Filter f : filterList) {
            filterTableModel.addFilter(f);
        }
        filterTableModel.executeFilters();
        //Set the new the configuration
        BeanConfig.filters = filters;
    }
}
