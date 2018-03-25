package org.openstreetmap.josm.plugins;

import org.openstreetmap.josm.actions.JosmAction;
import org.openstreetmap.josm.data.imagery.ImageryInfo;
import org.openstreetmap.josm.data.osm.DataSet;
import org.openstreetmap.josm.data.osm.Filter;
import org.openstreetmap.josm.data.osm.event.AbstractDatasetChangedEvent;
import org.openstreetmap.josm.data.osm.event.DataSetListenerAdapter;
import org.openstreetmap.josm.data.preferences.sources.SourceEntry;
import org.openstreetmap.josm.data.preferences.sources.SourceType;
import org.openstreetmap.josm.gui.MapFrameListener;
import org.openstreetmap.josm.gui.Notification;
import org.openstreetmap.josm.gui.dialogs.FilterTableModel;
import org.openstreetmap.josm.gui.layer.LayerManager.LayerAddEvent;
import org.openstreetmap.josm.gui.layer.LayerManager.LayerChangeListener;
import org.openstreetmap.josm.gui.layer.LayerManager.LayerOrderChangeEvent;
import org.openstreetmap.josm.gui.layer.LayerManager.LayerRemoveEvent;
import org.openstreetmap.josm.gui.layer.OsmDataLayer;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.net.ssl.HttpsURLConnection;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.openstreetmap.josm.gui.MainApplication.getMap;

/**
 * Created by aarthychandrasekhar on 09/10/15.
 */
public class ConfigLayerAction extends JosmAction implements DataSetListenerAdapter.Listener, LayerChangeListener {
    ArrayList<TaskLayer> taskLayers = new ArrayList<TaskLayer>();
    ArrayList<SourceEntry> mapPaintStyleSourceEntries = new ArrayList<SourceEntry>();
    List<Filter> filterList = new ArrayList<Filter>();
    DataSetListenerAdapter dataSetListenerAdapter = new DataSetListenerAdapter(this);
    String changesetSource,changesetComment;
    MapFrameListener mapFrameListener;
    boolean alreadyLoaded = false;
    String URL;
    public ConfigLayerAction(String name, String URL) {
        super(name, null, name, null , true);
        this.URL = URL;
    }

    private void updateFilters () {
        FilterTableModel filterTableModel = getMap().filterDialog.getFilterModel();

        List<Filter> existingFilters = filterTableModel.getFilters();
        for (int i = 0; i < existingFilters.size(); i++) {
            filterTableModel.removeFilter(i);
        }

        for(Filter f: filterList){
            filterTableModel.addFilter(f);
        }
        filterTableModel.executeFilters();
    }

    private void updateChangesetDetails () {
        DataSet ds = getLayerManager().getEditDataSet();
        if (ds != null) {
            //set changeset source and comment
            ds.addChangeSetTag("source", changesetSource);
            ds.addChangeSetTag("comment", changesetComment);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (TaskLayer taskLayer : taskLayers) {
            getLayerManager().removeLayer(taskLayer);
        }

        String layerName, layerUrl;


        try {
            URL obj = new URL(URL);
            HttpsURLConnection httpURLConnection = (HttpsURLConnection) obj.openConnection();

            JsonObject jsonObject = Json.createReader(httpURLConnection.getInputStream()).readObject();
            JsonObject project = jsonObject.getJsonObject("project");
            JsonObject changeset = project.getJsonObject("changeset");
            JsonArray layerArray = project.getJsonArray("imagery");
            JsonArray mapPaintStyles = project.getJsonArray("mapcss");

            changesetSource =  changeset.getString("source");
            changesetComment = changeset.getString("comment");
            updateChangesetDetails();

            JsonArray filters = project.getJsonArray("filters");
            for (int i = 0; i < filters.size(); i++) {
                JsonObject filter = filters.getJsonObject(i);
                Filter f1 = new Filter();
                f1.text = filter.getString("filter");
                f1.hiding = true;
                filterList.add(f1);
            }
            updateFilters();

            //remove current layers to prevent duplicate layers
            for (int k =0; k < ConfigPlugin.currentLayer.size(); k++) {
                getLayerManager().removeLayer(ConfigPlugin.currentLayer.get(k));
            }
            ConfigPlugin.currentLayer.clear();

            //adding new layers
            for (int i = 0; i < layerArray.size(); i++) {
                JsonObject layer = layerArray.getJsonObject(i);
                layerUrl = layer.getString("url");
                layerName = layer.getString("name");

                ImageryInfo imageryInfo = new ImageryInfo();
                imageryInfo.setUrl(layerUrl);
                imageryInfo.setName(layerName);

                TaskLayer taskLayer = new TaskLayer(imageryInfo);
                getLayerManager().addLayer(taskLayer);
                ConfigPlugin.currentLayer.add(taskLayer);
                taskLayers.add(taskLayer);
            }
            //removing current mappaint

            for (int j = 0; j < mapPaintStyles.size(); j++) {
                JsonObject mapPaint = mapPaintStyles.getJsonObject(j);
                String mapPaintName = mapPaint.getString("name");
                String mapPaintDescription = mapPaint.getString("description");
                String mapPaintUrl = mapPaint.getString("url");
                mapPaintStyleSourceEntries.add(new SourceEntry(SourceType.MAP_PAINT_STYLE, mapPaintUrl,mapPaintName,mapPaintDescription,true));
            }

            getLayerManager().addLayerChangeListener(this);
            alreadyLoaded = false;
        } catch (Exception e1) {
            e1.printStackTrace();
            new Notification("ConfigLayerAction:"+e1.toString()).show();
        }
    }

    @Override
    public void processDatasetEvent(AbstractDatasetChangedEvent abstractDatasetChangedEvent) {
        updateChangesetDetails();
    }

    @Override
    public void layerOrderChanged(LayerOrderChangeEvent e) {

    }

    @Override
    public void layerAdded(LayerAddEvent e) {
        if (e.getAddedLayer() instanceof OsmDataLayer) {
            registerNewLayer((OsmDataLayer) e.getAddedLayer());
        }
    }


    @Override
    public void layerRemoving(LayerRemoveEvent e) {
        if (e.getRemovedLayer() instanceof OsmDataLayer) {
            unRegisterNewLayer((OsmDataLayer) e.getRemovedLayer());
        }
    }
    private void registerNewLayer(OsmDataLayer layer) {
        if(alreadyLoaded != true) {
            layer.getDataSet().addDataSetListener(dataSetListenerAdapter);
            updateFilters();
//            for (SourceEntry sc : mapPaintStyleSourceEntries) {
//                boolean flag = false;
//                List<StyleSource> styleSources = MapPaintStyles.getStyles().getStyleSources();
//                for (StyleSource ss : styleSources) {
//                    if (ss.url.equals(sc.url)) {
//                        flag = true;
//                    }
//                }
//                if (!flag) {
//                    MapPaintStyles.addStyle(sc);
//                }
//            }
            alreadyLoaded = true;

        }

    }

    private void unRegisterNewLayer(OsmDataLayer layer) {
        layer.getDataSet().removeDataSetListener(dataSetListenerAdapter);
    }

}