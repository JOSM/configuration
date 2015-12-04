/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openstreetmap.josm.plugins.config;

import org.openstreetmap.josm.Main;

/**
 *
 * @author ruben
 */
public class ChangesetConfig {

    public static void setup_commet_source(String comment, String source) {
        Main.main.getCurrentDataSet().addChangeSetTag("source", source);
        Main.main.getCurrentDataSet().addChangeSetTag("comment", comment);
    }

}
