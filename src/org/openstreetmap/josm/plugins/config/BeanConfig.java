package org.openstreetmap.josm.plugins.config;

import org.openstreetmap.josm.plugins.util.TaskLayer;

import java.util.ArrayList;

public class BeanConfig {

    public static ArrayList<TaskLayer> currentLayer = new ArrayList<>();
    public static String filters;
    public static ArrayList<String> layers;
    public static String previous_filters = null;
    public static String actual_filters = null;
    public static String source;
    public static String comment;
    public static ArrayList<String> mappaints;

}
