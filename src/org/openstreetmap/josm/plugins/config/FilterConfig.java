package org.openstreetmap.josm.plugins.config;

import java.util.ArrayList;
import java.util.List;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.data.osm.Filter;
import org.openstreetmap.josm.gui.Notification;
import org.openstreetmap.josm.gui.dialogs.FilterTableModel;

public class FilterConfig {

    public void add_filter(String previous_filter, String actual_filter) {
        List<Filter> filterList = new ArrayList<>();
        new Notification("Filter:" + actual_filter).show();
        Filter f1 = new Filter();
        f1.text = actual_filter;
        f1.hiding = true;
        filterList.add(f1);
        FilterTableModel filterTableModel = Main.map.filterDialog.getFilterModel();
        List<Filter> existingFilters = filterTableModel.getFilters();
        clear_filter(previous_filter);
        //Add filter
        for (Filter f : filterList) {
            filterTableModel.addFilter(f);
        }
        filterTableModel.executeFilters();
    }

    public void clear_filter(String filters) {
        FilterTableModel filterTableModel = Main.map.filterDialog.getFilterModel();
        List<Filter> existingFilters = filterTableModel.getFilters();
        //Remove if exist previous configuration
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
