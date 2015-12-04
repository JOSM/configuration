package org.openstreetmap.josm.plugins.util;

import javax.swing.JOptionPane;

/**
 *
 * @author ruben
 */
public class Print {

    public static void alert(Object object) {
        JOptionPane.showMessageDialog(null, object);
    }

    public static void print(Object object) {
        System.err.println(object);
    }
}
