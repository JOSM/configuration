package org.openstreetmap.josm.plugins;
import org.openstreetmap.josm.gui.mappaint.MapPaintStyles;
import org.openstreetmap.josm.gui.preferences.SourceEntry;
import org.openstreetmap.josm.gui.preferences.map.MapPaintPreference;

/**
 * Created by aarthychandrasekhar on 13/10/15.
 */
public class TaskMapPaint extends MapPaintPreference.MapPaintPrefHelper {

    private TaskMapPaint(SourceEntry sourceEntry) {
        MapPaintStyles.addStyle(sourceEntry);
    }
}

