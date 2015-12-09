/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openstreetmap.josm.plugins.config;
import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.data.osm.event.AbstractDatasetChangedEvent;



public class ChangesetConfig {

    public void set_changeset_comment_source(String comment, String source) {
        try {
            if (Main.main.hasEditLayer()) {
                //set changeset source and comment
                Main.main.getCurrentDataSet().addChangeSetTag("source", source);
                Main.main.getCurrentDataSet().addChangeSetTag("comment", comment);
            }
        }
        catch (Exception ex) {

        }
    }
}