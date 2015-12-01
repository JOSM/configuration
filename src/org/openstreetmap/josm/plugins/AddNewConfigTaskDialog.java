package org.openstreetmap.josm.plugins;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import org.openstreetmap.josm.Main;
import static org.openstreetmap.josm.gui.mappaint.mapcss.ExpressionFactory.Functions.tr;
import org.openstreetmap.josm.tools.ImageProvider;
import org.openstreetmap.josm.tools.WindowGeometry;

/**
 *
 * @author ruben
 */
@SuppressWarnings("serial")
public final class AddNewConfigTaskDialog extends JDialog {

    static private AddNewConfigTaskDialog instance = null;

    static public AddNewConfigTaskDialog getInstance() {
        if (instance == null) {
            instance = new AddNewConfigTaskDialog();
        }
        return instance;
    }

    static public final Dimension PREFERRED_SIZE = new Dimension(150, 200);
    private OKAction okAction = null;
    private CancelAction cancelAction = null;

    //constructor
    protected AddNewConfigTaskDialog() {
        build();
    }

    protected void build() {
        getContentPane().setLayout(new BorderLayout());
        // basic UI properties
        setModal(true);
        setSize(PREFERRED_SIZE);
        setTitle(tr("Add New Config Task"));
        JPanel pnlTagGrid = buildButtonRow();
        getContentPane().add(pnlTagGrid, BorderLayout.CENTER);

    }

    protected JPanel buildButtonRow() {
        //ok and cancel button
        JPanel pnl = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnl.add(new JButton(okAction = new OKAction()));
        pnl.add(new JButton(cancelAction = new CancelAction()));
        return pnl;
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            new WindowGeometry(
                    getClass().getName() + ".geometry",
                    WindowGeometry.centerInWindow(
                            Main.parent,
                            PREFERRED_SIZE
                    )
            ).applySafe(this);
        } else if (isShowing()) {
            new WindowGeometry(this).remember(getClass().getName() + ".geometry");
        }
        super.setVisible(visible);
    }

    class CancelAction extends AbstractAction {

        public CancelAction() {
            putValue(NAME, tr("Cancel"));
            putValue(SMALL_ICON, ImageProvider.get("cancel"));
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
            putValue(SHORT_DESCRIPTION, tr("Abort tag editing and close dialog"));
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            setVisible(false);
        }
    }

    class OKAction extends AbstractAction {

        public OKAction() {
            putValue(NAME, tr("OK"));
            putValue(SMALL_ICON, ImageProvider.get("ok"));
            putValue(SHORT_DESCRIPTION, tr("Apply edited tags and close dialog"));
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl ENTER"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showConfirmDialog(null, "ok");
        }

    }
}
