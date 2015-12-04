package org.openstreetmap.josm.plugins.config;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import javax.json.Json;
import javax.json.JsonObject;
import javax.net.ssl.HttpsURLConnection;
import org.openstreetmap.josm.actions.JosmAction;
import org.openstreetmap.josm.gui.Notification;
import org.openstreetmap.josm.plugins.util.Print;

public class JOSMConfig extends JosmAction {

    FilterConfig filterConfig = new FilterConfig();
    BeanConfig beanConfig = new BeanConfig();

    String URL;

    public JOSMConfig(String name, String URL) {
        super(name, null, name, null, true);
        this.URL = URL;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {

            URL obj = new URL(URL);
            HttpsURLConnection httpURLConnection = (HttpsURLConnection) obj.openConnection();
            JsonObject jsonObject = Json.createReader(httpURLConnection.getInputStream()).readObject();
            JsonObject task = jsonObject.getJsonObject("task");

            //layers        
            LayerConfig.setup_layers(task.getJsonArray("layers"));

            //mappaints
            MapstyleConfig.setup_mappaints(task.getJsonArray("mappaints"));

            //comment and soure for changesets.
            ChangesetConfig.setup_commet_source(task.getString("comment"), task.getString("source"));

            //filters
            beanConfig.setActual_filters(task.getString("filters"));
            Print.print(beanConfig.getPrevious_filters() + "===" + beanConfig.getActual_filters());
            filterConfig.add_filter(beanConfig);
            Print.print(beanConfig.getPrevious_filters() + "===" + beanConfig.getActual_filters());

        } catch (IOException e1) {
            new Notification("E:" + e1.toString()).show();
        }

    }
}
