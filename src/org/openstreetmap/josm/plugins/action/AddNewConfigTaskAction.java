package org.openstreetmap.josm.plugins.action;

import java.awt.event.ActionEvent;
import org.openstreetmap.josm.actions.JosmAction;
import org.openstreetmap.josm.plugins.ui.AddNewConfigTaskDialog;

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
