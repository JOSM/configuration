package org.openstreetmap.josm.plugins.config;

import java.util.ArrayList;

public class BeanConfig {

    public static ArrayList<String> layers;
    public String previous_filters;
    public String actual_filters;
    public static String source;
    public static String commnet;
    public static ArrayList<String> mappaints;

    public String getPrevious_filters() {
        return previous_filters;
    }

    public void setPrevious_filters(String previous_filters) {
        this.previous_filters = previous_filters;
    }

    public String getActual_filters() {
        return actual_filters;
    }

    public void setActual_filters(String actual_filters) {
        this.actual_filters = actual_filters;
    }
    
    
}
