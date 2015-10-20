package org.openstreetmap.josm.plugins;
import org.openstreetmap.josm.actions.JosmAction;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.data.imagery.ImageryInfo;
import org.openstreetmap.josm.data.osm.event.*;
import org.openstreetmap.josm.gui.MapFrame;
import org.openstreetmap.josm.gui.MapFrameListener;
import org.openstreetmap.josm.gui.MapView;
import org.openstreetmap.josm.gui.Notification;
import org.openstreetmap.josm.gui.layer.Layer;
import org.openstreetmap.josm.gui.layer.OsmDataLayer;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by aarthychandrasekhar on 09/10/15.
 */
public class MyPluginAddLayerAction extends JosmAction implements DataSetListenerAdapter.Listener, MapView.LayerChangeListener {
    ArrayList<TaskLayer> taskLayers = new ArrayList<TaskLayer>();
    DataSetListenerAdapter dataSetListenerAdapter = new DataSetListenerAdapter(this);

    public MyPluginAddLayerAction() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int j = 0; j < taskLayers.size(); j++) {
            Main.main.removeLayer(taskLayers.get(j));
        }

        String url = JOptionPane.showInputDialog(Main.parent, "Enter gist URL");
        String taskString, layerName, layerUrl, mapPaint, mapPaintName, mapPaintTitle;
        mapPaint = "https://raw.githubusercontent.com/Andygol/josm-styles/master/created_in_2015.mapcss";
        mapPaintName = "recent edits";
        mapPaintTitle = "recent edits";


        //MapPaintStyles.addStyle(new SourceEntry("https://raw.githubusercontent.com/Andygol/josm-styles/master/created_in_2015.mapcss","Name","description",true));

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

            for (int i = 0; i < layerArray.size(); i++) {
                JsonObject layer = layerArray.getJsonObject(i);
                layerUrl = layer.getString("url");
                layerName = layer.getString("name");
                new Notification("Name:" + layerName + "   URL:" + layerUrl).show();

                ImageryInfo imageryInfo = new ImageryInfo();
                imageryInfo.setUrl(layerUrl);
                imageryInfo.setName(layerName);

                TaskLayer taskLayer = new TaskLayer(imageryInfo);
                Main.main.addLayer(taskLayer);
                taskLayers.add(taskLayer);
            }

            //Editing the changeset comment and source tags
            Main.addMapFrameListener(new MapFrameListener() {
                @Override
                public void mapFrameInitialized(final MapFrame mapFrame, MapFrame mapFrame1) {
                    Main.map.addPropertyChangeListener(new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            Main.main.getEditLayer().data.addChangeSetTag("source", "aarthy");
                        }
                    });
                }
            });
            MapView.addLayerChangeListener(this);
        } catch (IOException e1) {
            e1.printStackTrace();
            new Notification(e1.toString()).show();
        }
    }

    @Override
    public void processDatasetEvent(AbstractDatasetChangedEvent abstractDatasetChangedEvent) {
        if (Main.main.hasEditLayer()) {
            Main.main.getCurrentDataSet().addChangeSetTag("source", "hello");
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
    }

    private void unRegisterNewLayer(OsmDataLayer layer) {
        layer.data.removeDataSetListener(dataSetListenerAdapter);
    }

}