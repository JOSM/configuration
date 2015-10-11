package org.openstreetmap.josm.plugins;

import oauth.signpost.http.HttpResponse;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.actions.JosmAction;
import org.openstreetmap.josm.data.Preferences;
import org.openstreetmap.josm.data.imagery.ImageryInfo;
import org.openstreetmap.josm.gui.MapView;
import org.openstreetmap.josm.gui.Notification;
import org.openstreetmap.josm.tools.Shortcut;
import sun.net.www.http.HttpClient;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.stream.JsonParser;
import javax.net.ssl.HttpsURLConnection;
import javax.print.DocFlavor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.prefs.PreferenceChangeListener;

/**
 * Created by aarthychandrasekhar on 09/10/15.
 */
public class MyPluginAddLayerAction extends JosmAction {
    ArrayList <TaskLayer> taskLayers = new ArrayList<TaskLayer>();

    public MyPluginAddLayerAction() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int j=0 ; j< taskLayers.size(); j++) {
            Main.main.removeLayer(taskLayers.get(j));
        }

        String url = "https://api.github.com/gists/c02c7c8817084a826110";
        String taskString, layerName, layerUrl;

        try {
            URL obj = new URL(url);
            HttpsURLConnection httpURLConnection = (HttpsURLConnection) obj.openConnection();

            JsonObject jsonObject = Json.createReader(httpURLConnection.getInputStream()).readObject();
            JsonObject files = jsonObject.getJsonObject("files");
            JsonObject taskingmanager = files.getJsonObject("taskingmanager.json");
            taskString = taskingmanager.getString("content");

            JsonObject taskObject = Json.createReader(new StringReader(taskString)).readObject();
            JsonObject task = taskObject.getJsonObject("task");
            JsonArray layerArray = task.getJsonArray("layers");

            for (int i = 0; i < layerArray.size(); i++) {
                JsonObject layer = layerArray.getJsonObject(i);
                layerUrl = layer.getString("url");
                layerName = layer.getString("name");
                new Notification("Name:"+layerName+"   URL:"+layerUrl).show();

                ImageryInfo imageryInfo = new ImageryInfo();
                imageryInfo.setUrl(layerUrl);
                imageryInfo.setName(layerName);

                TaskLayer taskLayer = new TaskLayer(imageryInfo);
                Main.main.addLayer(taskLayer);
                taskLayers.add(taskLayer);
            }
            

        } catch (IOException e1) {
            e1.printStackTrace();
            new Notification(e1.toString()).show();
        }
    }
}
