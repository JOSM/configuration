package org.openstreetmap.josm.plugins;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.openstreetmap.josm.tools.Logging;

public class LoadTaskConfig {

    JMenu jMenu;

    public LoadTaskConfig(JMenu jMenu) {
        this.jMenu = jMenu;
    }

    public void load() {
        JsonReader reader = null;
        try {
            URL obj = URI.create(Settings.URL_TASKS).toURL();
            HttpsURLConnection httpURLConnection = (HttpsURLConnection) obj.openConnection();
            reader = Json.createReader(httpURLConnection.getInputStream());
            JsonObject jsonObject = reader.readObject();
            JsonArray tasksArray = jsonObject.getJsonArray("tasks");
            for (int i = 0; i < tasksArray.size(); i++) {
                JsonObject task = tasksArray.getJsonObject(i);
                String taskname = task.getString("name");
                String taskURL = task.getString("URL");
                jMenu.add(new JMenuItem(new ConfigLayerAction(taskname, taskURL)));

            }

        } catch (IOException e) {
            Logging.trace(e);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

}
