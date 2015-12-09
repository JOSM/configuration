package org.openstreetmap.josm.plugins.config;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import javax.net.ssl.HttpsURLConnection;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.actions.JosmAction;
import org.openstreetmap.josm.data.osm.Filter;
import org.openstreetmap.josm.data.osm.event.AbstractDatasetChangedEvent;
import org.openstreetmap.josm.data.osm.event.DataSetListenerAdapter;
import org.openstreetmap.josm.gui.MapView;
import org.openstreetmap.josm.gui.Notification;
import org.openstreetmap.josm.gui.dialogs.FilterTableModel;
import org.openstreetmap.josm.gui.layer.Layer;
import org.openstreetmap.josm.gui.layer.OsmDataLayer;
import org.openstreetmap.josm.plugins.util.TaskLayer;

public class JOSMConfig extends JosmAction implements DataSetListenerAdapter.Listener, MapView.LayerChangeListener {

    LayerConfig layerConfig = new LayerConfig();
    FilterConfig filterConfig = new FilterConfig();
    ChangesetConfig changesetConfig = new ChangesetConfig();
    MapstyleConfig mapstyleConfig = new MapstyleConfig();

    //variables
    String changesetSource, changesetComment;
    DataSetListenerAdapter dataSetListenerAdapter = new DataSetListenerAdapter(this);
    ArrayList<TaskLayer> taskLayers = new ArrayList<TaskLayer>();
    String URL;


    public JOSMConfig(String name, String description, String URL) {
        super(name, null, description, null, true);
        this.URL = URL;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            MapView.addLayerChangeListener(this);
            URL obj = new URL(URL);
            HttpsURLConnection httpURLConnection = (HttpsURLConnection) obj.openConnection();
            JsonObject jsonObject = Json.createReader(httpURLConnection.getInputStream()).readObject();
            JsonObject project = jsonObject.getJsonObject("project");
            JsonObject changeset = project.getJsonObject("changeset");
            changesetSource = changeset.getString("source");
            changesetComment = changeset.getString("comment");
            //layers
            layerConfig.setup_layers(project.getJsonArray("imagery"), taskLayers);

            //mappaints
            mapstyleConfig.setup_mappaints(project.getJsonArray("mapcss"));

            //filters
            filterConfig.add_filters(project.getJsonArray("filters"));

        } catch (IOException e1) {
            new Notification("E:" + e1.toString()).show();

        }

    }
    //implenting methods to register layers and send the values of changeset comment and source
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

    @Override
    public void processDatasetEvent(AbstractDatasetChangedEvent abstractDatasetChangedEvent) {
        changesetConfig.set_changeset_comment_source(changesetComment, changesetSource);

    }
    private void registerNewLayer(OsmDataLayer layer) {
        layer.data.addDataSetListener(dataSetListenerAdapter);
        }

    private void unRegisterNewLayer(OsmDataLayer layer) {
        layer.data.removeDataSetListener(dataSetListenerAdapter);
    }

}



