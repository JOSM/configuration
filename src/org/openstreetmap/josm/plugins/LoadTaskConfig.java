package org.openstreetmap.josm.plugins;

import org.openstreetmap.josm.plugins.action.AddNewConfigTaskAction;
import org.openstreetmap.josm.plugins.config.JOSMConfig;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import org.openstreetmap.josm.actions.JosmAction;
import org.openstreetmap.josm.gui.Notification;

public class LoadTaskConfig {

    public static ArrayList<JOSMConfig> load() {

        ArrayList<JOSMConfig> josmConfigs = new ArrayList<>();
        try {
            URL obj = new URL(Settings.URL_TASKS);
            HttpsURLConnection httpURLConnection = (HttpsURLConnection) obj.openConnection();
            JsonObject jsonObject = Json.createReader(httpURLConnection.getInputStream()).readObject();
            JsonArray tasksArray = jsonObject.getJsonArray("tasks");
            for (int i = 0; i < tasksArray.size(); i++) {
                JsonObject task = tasksArray.getJsonObject(i);
                String taskname = task.getString("name");
                String taskDescription = task.getString("description");
                String taskURL = task.getString("URL");
                josmConfigs.add(new JOSMConfig(taskname,taskDescription, taskURL));

            }  

        } catch (IOException e) {
            e.printStackTrace();

        } return josmConfigs;
    }

}
