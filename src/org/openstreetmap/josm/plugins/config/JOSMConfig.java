/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openstreetmap.josm.plugins.config;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.net.ssl.HttpsURLConnection;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.actions.JosmAction;
import org.openstreetmap.josm.data.imagery.ImageryInfo;
import org.openstreetmap.josm.data.osm.Filter;
import org.openstreetmap.josm.gui.MapView;
import org.openstreetmap.josm.gui.Notification;
import org.openstreetmap.josm.gui.dialogs.FilterTableModel;
import org.openstreetmap.josm.gui.preferences.SourceEntry;
import org.openstreetmap.josm.plugins.ConfigPlugin;
import org.openstreetmap.josm.plugins.util.TaskLayer;


public class JOSMConfig extends JosmAction {

    String URL;

    public JOSMConfig(String name, String URL) {
        super(name, null, name, null, true);
        this.URL = URL;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {

            URL obj = new URL(URL);
            HttpsURLConnection httpURLConnection = (HttpsURLConnection) obj.openConnection();
            JsonObject jsonObject = Json.createReader(httpURLConnection.getInputStream()).readObject();
            JsonObject task = jsonObject.getJsonObject("task");

            //layers         
            setup_layers(task.getJsonArray("layers"));

            //mappaints
            setup_mappaints(task.getJsonArray("mappaints"));

            //comment and soure for changesets.
            setup_commet_source(task.getString("comment"), task.getString("source"));

            //filters
            setup_filter(task.getString("filters"));

        } catch (IOException e1) {
            new Notification("E:" + e1.toString()).show();
        }

    }

    public void setup_layers(JsonArray layers) {

    }

    public void setup_mappaints(JsonArray mappaints) {
        
        
        ArrayList<SourceEntry> mapPaintStyleSourceEntries = new ArrayList<SourceEntry>();
        for (int j = 0; j < mappaints.size(); j++) {
            JsonObject mapPaint = mappaints.getJsonObject(j);
            String mapPaintName = mapPaint.getString("name");
            String mapPaintDescription = mapPaint.getString("description");
            String mapPaintUrl = mapPaint.getString("url");
            mapPaintStyleSourceEntries.add(new SourceEntry(mapPaintUrl, mapPaintName, mapPaintDescription, true));
        }

    }

    public void setup_commet_source(String comment, String source) {
        Main.main.getCurrentDataSet().addChangeSetTag("source", source);
        Main.main.getCurrentDataSet().addChangeSetTag("comment", comment);
    }

    public void setup_filter(String filters) {
        List<Filter> filterList = new ArrayList<>();
        new Notification("Filter:" + filters).show();
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
