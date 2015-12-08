/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openstreetmap.josm.plugins.config;

import java.util.ArrayList;
import java.util.List;
import javax.json.JsonArray;
import javax.json.JsonObject;

import org.openstreetmap.josm.gui.mappaint.MapPaintStyles;
import org.openstreetmap.josm.gui.mappaint.StyleSource;
import org.openstreetmap.josm.gui.preferences.SourceEntry;

public class MapstyleConfig {
    
        public  void setup_mappaints(JsonArray mappaints) {
        
        
       try{
        ArrayList<SourceEntry> mapPaintStyleSourceEntries = new ArrayList<>();
        for (int j = 0; j < mappaints.size(); j++) {
            JsonObject mapPaint = mappaints.getJsonObject(j);
            String mapPaintName = mapPaint.getString("name");
            String mapPaintDescription = mapPaint.getString("description");
            String mapPaintUrl = mapPaint.getString("url");
            mapPaintStyleSourceEntries.add(new SourceEntry(mapPaintUrl, mapPaintName, mapPaintDescription, true));

            for (SourceEntry sc : mapPaintStyleSourceEntries) {
                boolean flag = false;
                List<StyleSource> styleSources = MapPaintStyles.getStyles().getStyleSources();
                for (StyleSource ss : styleSources) {
                    if (ss.url.equals(sc.url)) {
                        flag = true;
                    }
                }
                if (!flag) {
                    MapPaintStyles.addStyle(sc);

                }
            }

            }
        }
       catch (Exception ex){

       }

    }
}
