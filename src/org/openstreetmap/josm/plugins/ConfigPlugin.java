package org.openstreetmap.josm.plugins;

import java.awt.event.KeyEvent;
import java.io.StringReader;
import java.net.URL;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.data.imagery.ImageryInfo;
import org.openstreetmap.josm.data.osm.Filter;
import org.openstreetmap.josm.gui.MapFrame;
import org.openstreetmap.josm.gui.MapFrameListener;
import org.openstreetmap.josm.gui.MapView;
import org.openstreetmap.josm.gui.Notification;
import org.openstreetmap.josm.gui.help.HelpUtil;
import org.openstreetmap.josm.gui.mappaint.MapPaintStyles;
import org.openstreetmap.josm.gui.mappaint.StyleSource;
import org.openstreetmap.josm.gui.preferences.SourceEntry;

import static org.openstreetmap.josm.gui.mappaint.mapcss.ExpressionFactory.Functions.tr;

public class ConfigPlugin extends Plugin {

    public ConfigPlugin(PluginInformation info) {
        super(info);

        try {
            URL obj = new URL("https://api.github.com/gists/321588be2520af4e15ec");
            HttpsURLConnection httpURLConnection = (HttpsURLConnection) obj.openConnection();

            JsonObject jsonObject = Json.createReader(httpURLConnection.getInputStream()).readObject();
            JsonObject files = jsonObject.getJsonObject("files");
            JsonObject taskList = files.getJsonObject("taskList.json");
            String taskString = taskList.getString("content");

            JsonObject taskObject = Json.createReader(new StringReader(taskString)).readObject();

            JsonArray tasksArray = taskObject.getJsonArray("tasks");

            final JMenu loadTaskMenu = Main.main.menu.addMenu(
                    "Task config", tr("Task config"), KeyEvent.VK_K,
                    Main.main.menu.getDefaultMenuPos(), HelpUtil.ht("/Plugin/task")
            );


            for (int i = 0; i < tasksArray.size(); i++) {
                JsonObject task = tasksArray.getJsonObject(i);
                loadTaskMenu.add(new JMenuItem(new ConfigLayerAction(task.getString("name"),task.getString("URL"))));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            new Notification(e1.toString()).show();
        }

    }
}
