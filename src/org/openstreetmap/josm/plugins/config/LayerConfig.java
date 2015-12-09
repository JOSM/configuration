/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openstreetmap.josm.plugins.config;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.data.imagery.ImageryInfo;
import org.openstreetmap.josm.plugins.ConfigPlugin;
import org.openstreetmap.josm.plugins.util.TaskLayer;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.ArrayList;

/**
 *
 * @author ruben
 */
public class LayerConfig {

    String layerName, layerUrl;
    
    public void setup_layers(JsonArray layerArray, ArrayList taskLayers) {


        //remove current layers to prevent duplicate layers
        for (int k =0; k< BeanConfig.currentLayer.size(); k++) {
            Main.main.removeLayer(BeanConfig.currentLayer.get(k));
        }

        //adding new layers
        for (int i = 0; i < layerArray.size(); i++) {
            JsonObject layer = layerArray.getJsonObject(i);
            layerUrl = layer.getString("url");
            layerName = layer.getString("name");

            ImageryInfo imageryInfo = new ImageryInfo();
            imageryInfo.setUrl(layerUrl);
            imageryInfo.setName(layerName);

            TaskLayer taskLayer = new TaskLayer(imageryInfo);
            Main.main.addLayer(taskLayer);
            BeanConfig.currentLayer.add(taskLayer);
            taskLayers.add(taskLayer);
        }

    }

    
    
}
