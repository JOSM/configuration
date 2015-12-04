/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openstreetmap.josm.plugins.config;

import java.util.ArrayList;
import javax.json.JsonArray;
import javax.json.JsonObject;
import org.openstreetmap.josm.gui.preferences.SourceEntry;

/**
 *
 * @author ruben
 */
public class MapstyleConfig {
    
        public static void setup_mappaints(JsonArray mappaints) {
        
        
        ArrayList<SourceEntry> mapPaintStyleSourceEntries = new ArrayList<SourceEntry>();
        for (int j = 0; j < mappaints.size(); j++) {
            JsonObject mapPaint = mappaints.getJsonObject(j);
            String mapPaintName = mapPaint.getString("name");
            String mapPaintDescription = mapPaint.getString("description");
            String mapPaintUrl = mapPaint.getString("url");
            mapPaintStyleSourceEntries.add(new SourceEntry(mapPaintUrl, mapPaintName, mapPaintDescription, true));
        }

    }
}
