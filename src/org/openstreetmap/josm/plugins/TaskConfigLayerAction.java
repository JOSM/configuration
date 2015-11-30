package org.openstreetmap.josm.plugins;
import org.openstreetmap.josm.actions.JosmAction;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.data.imagery.ImageryInfo;
import org.openstreetmap.josm.data.osm.Filter;
import org.openstreetmap.josm.data.osm.event.*;
import org.openstreetmap.josm.gui.MapFrame;
import org.openstreetmap.josm.gui.MapFrameListener;
import org.openstreetmap.josm.gui.MapView;
import org.openstreetmap.josm.gui.Notification;
import org.openstreetmap.josm.gui.dialogs.FilterTableModel;
import org.openstreetmap.josm.gui.layer.Layer;
import org.openstreetmap.josm.gui.layer.OsmDataLayer;
import org.openstreetmap.josm.gui.mappaint.MapPaintStyles;
import org.openstreetmap.josm.gui.mappaint.StyleSource;
import org.openstreetmap.josm.gui.preferences.SourceEntry;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aarthychandrasekhar on 09/10/15.
 */
public class TaskConfigLayerAction extends JosmAction implements DataSetListenerAdapter.Listener, MapView.LayerChangeListener {
    ArrayList<TaskLayer> taskLayers = new ArrayList<TaskLayer>();
    ArrayList<SourceEntry> mapPaintStyleSourceEntries = new ArrayList<SourceEntry>();
    List<Filter> filterList = new ArrayList<Filter>();
    DataSetListenerAdapter dataSetListenerAdapter = new DataSetListenerAdapter(this);
    String changesetSource,changesetComment,filters;
    MapFrameListener mapFrameListener;
    public TaskConfigLayerAction(String name) {
        super(name, null, name, null , true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int j = 0; j < taskLayers.size(); j++) {
            Main.main.removeLayer(taskLayers.get(j));
        }
        String url = JOptionPane.showInputDialog(Main.parent, "Enter gist URL");
        String taskString, layerName, layerUrl;


        try {
            URL obj = new URL(url);
            HttpsURLConnection httpURLConnection = (HttpsURLConnection) obj.openConnection();

            JsonObject jsonObject = Json.createReader(httpURLConnection.getInputStream()).readObject();
            JsonObject files = jsonObject.getJsonObject("files");
            JsonObject taskingmanager = files.getJsonObject("taskingmanager.json");
            taskString = taskingmanager.getString("content");

            JsonObject taskObject = Json.createReader(new StringReader(taskString)).readObject();
            JsonObject task = taskObject.getJsonObject("task");
            JsonArray layerArray = task.getJsonArray("layers");
            JsonArray mapPaintStyles = task.getJsonArray("mappaints");
            changesetSource =  task.getString("source");
            changesetComment = task.getString("comment");
            filters = task.getString("filters");
            new Notification("Filter:"+filters).show();
            Filter f1 = new Filter();
            f1.text = filters;
            f1.hiding = true;
            filterList.add(f1);


            for (int i = 0; i < layerArray.size(); i++) {
                JsonObject layer = layerArray.getJsonObject(i);
                layerUrl = layer.getString("url");
                layerName = layer.getString("name");

                ImageryInfo imageryInfo = new ImageryInfo();
                imageryInfo.setUrl(layerUrl);
                imageryInfo.setName(layerName);

                TaskLayer taskLayer = new TaskLayer(imageryInfo);
                Main.main.addLayer(taskLayer);
                taskLayers.add(taskLayer);
            }
            for (int j = 0; j < mapPaintStyles.size(); j++) {
                JsonObject mapPaint = mapPaintStyles.getJsonObject(j);
                String mapPaintName = mapPaint.getString("name");
                String mapPaintDescription = mapPaint.getString("description");
                String mapPaintUrl = mapPaint.getString("url");

                mapPaintStyleSourceEntries.add(new SourceEntry(mapPaintUrl,mapPaintName,mapPaintDescription,true));
            }
            if(mapFrameListener==null) Main.removeMapFrameListener(mapFrameListener);

            mapFrameListener = new MapFrameListener(){
                @Override
                public void mapFrameInitialized(final MapFrame mapFrame, MapFrame mapFrame1) {
                    for (SourceEntry sc : mapPaintStyleSourceEntries) {
                        boolean flag = false;
                        List<StyleSource> styleSources = MapPaintStyles.getStyles().getStyleSources();
                        for(StyleSource ss: styleSources) {
                            if(ss.url.equals(sc.url)) {
                                flag = true;
                            }
                        }
                        if(!flag)
                            MapPaintStyles.addStyle(sc);
                    }
                }


            };
            Main.addMapFrameListener(mapFrameListener);
            MapView.addLayerChangeListener(this);
        } catch (Exception e1) {
            e1.printStackTrace();
            new Notification(e1.toString()).show();
        }
    }

    @Override
    public void processDatasetEvent(AbstractDatasetChangedEvent abstractDatasetChangedEvent) {
        if (Main.main.hasEditLayer()) {
            //set changeset source and comment
            Main.main.getCurrentDataSet().addChangeSetTag("source", changesetSource);
            Main.main.getCurrentDataSet().addChangeSetTag("comment", changesetComment);
        }
    }

    @Override
    public void activeLayerChange(Layer layer, Layer layer1) {

    }

    @Override
    public void layerAdded(Layer layer) {
        if (layer instanceof OsmDataLayer) {
            registerNewLayer((OsmDataLayer) layer);
        }
    }


    @Override
    public void layerRemoved(Layer layer) {
        if (layer instanceof OsmDataLayer) {
            unRegisterNewLayer((OsmDataLayer) layer);
        }
    }
    private void registerNewLayer(OsmDataLayer layer) {
        layer.data.addDataSetListener(dataSetListenerAdapter);
        FilterTableModel filterTableModel = Main.map.filterDialog.getFilterModel();
        for(Filter f: filterList){
            filterTableModel.addFilter(f);
            filterTableModel.executeFilters();
        }
    }

    private void unRegisterNewLayer(OsmDataLayer layer) {
        layer.data.removeDataSetListener(dataSetListenerAdapter);
    }

}