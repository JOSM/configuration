package org.openstreetmap.josm.plugins;

import java.io.IOException;
import java.net.URL;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class LoadTaskConfig {

    JMenu jMenu;

    public LoadTaskConfig(JMenu jMenu) {
        this.jMenu = jMenu;
    }

    public void load() {
        try {
            URL obj = new URL(Settings.URL_TASKS);
            HttpsURLConnection httpURLConnection = (HttpsURLConnection) obj.openConnection();
            JsonObject jsonObject = Json.createReader(httpURLConnection.getInputStream()).readObject();
            JsonArray tasksArray = jsonObject.getJsonArray("tasks");
            for (int i = 0; i < tasksArray.size(); i++) {
                JsonObject task = tasksArray.getJsonObject(i);
                String taskname = task.getString("name");
                String taskURL = task.getString("URL");
                jMenu.add(new JMenuItem(new ConfigLayerAction(taskname, taskURL)));

            }

        } catch (IOException e) {


        }
    }

}
