package org.openstreetmap.josm.plugins;

import org.openstreetmap.josm.data.imagery.ImageryInfo;
import org.openstreetmap.josm.gui.layer.TMSLayer;

/**
 * Created by aarthychandrasekhar on 09/10/15.
 */
public class TaskLayer extends TMSLayer {
    public TaskLayer(ImageryInfo imageryInfo) {
        super(imageryInfo);
        imageryInfo.setDefaultMaxZoom(19);
        imageryInfo.setDefaultMinZoom(3);
        imageryInfo.setImageryType(ImageryInfo.ImageryType.TMS);

    }

    public String getLayerName() {
        return layerName;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }

    String layerName;

}

