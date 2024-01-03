package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.controller.ExitAction;

import javax.swing.*;
import java.awt.*;

public class MyToolBar extends JToolBar {
    public MyToolBar(){
        super(HORIZONTAL);
        setFloatable(false);

        ExitAction ea = new ExitAction();
        add(ea);
        addSeparator(new Dimension(20,0));
        add(MainFrame.getInstance().getActionManager().getNewChildAction());
        add(MainFrame.getInstance().getActionManager().getRemoveChild());
        add(MainFrame.getInstance().getActionManager().getAutorAction());
        add(MainFrame.getInstance().getActionManager().getSaveAsAction());
        add(MainFrame.getInstance().getActionManager().getSaveAction());
        add(MainFrame.getInstance().getActionManager().getImportAction());
        add(MainFrame.getInstance().getActionManager().getExportDiagram());
        add(MainFrame.getInstance().getActionManager().getSaveTemplate());
        add(MainFrame.getInstance().getActionManager().getExportProjectToCode());
        add(MainFrame.getInstance().getActionManager().getUndoAction());
        add(MainFrame.getInstance().getActionManager().getRedoAction());
    }
}
