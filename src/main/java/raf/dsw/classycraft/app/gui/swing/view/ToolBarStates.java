package raf.dsw.classycraft.app.gui.swing.view;

import javax.swing.*;

public class ToolBarStates extends JToolBar {

    public ToolBarStates() {
        super(VERTICAL);
        setFloatable(false);
        add(MainFrame.getInstance().getActionManager().getMouseStateAction());
        add(MainFrame.getInstance().getActionManager().getAddInterclassAction());
        add(MainFrame.getInstance().getActionManager().getAddConnectionAction());
        add(MainFrame.getInstance().getActionManager().getAddContectAction());
        add(MainFrame.getInstance().getActionManager().getSelectionAction());
        add(MainFrame.getInstance().getActionManager().getDeleteAction());
    }
}
