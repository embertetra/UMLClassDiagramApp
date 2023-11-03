package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.controller.ExitAction;
import raf.dsw.classycraft.app.controller.RemoveChild;

import javax.swing.*;

public class MyToolBar extends JToolBar {
    public MyToolBar(){
        super(HORIZONTAL);
        setFloatable(false);

        ExitAction ea = new ExitAction();
        add(ea);
        add(MainFrame.getInstance().getActionManager().getNewChildAction());
        add(MainFrame.getInstance().getActionManager().getRemoveChild());
    }
}
