package org.openstreetmap.josm.plugins;

import java.awt.event.ActionEvent;
import org.openstreetmap.josm.actions.JosmAction;

/**
 *
 * @author ruben
 */
public class AddNewConfigTaskAction extends JosmAction {

    public AddNewConfigTaskAction(String name) {
        super(name, null, name, null, true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AddNewConfigTaskDialog dialog = AddNewConfigTaskDialog.getInstance();
        dialog.setVisible(true);
    }
}
