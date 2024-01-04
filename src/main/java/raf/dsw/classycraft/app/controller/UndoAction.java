package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class UndoAction extends AbstractClassyAction {

    public UndoAction(){
        putValue(SMALL_ICON, loadIcon("/images/undo.png"));
        putValue(NAME, "Undo");
        putValue(SHORT_DESCRIPTION, "Undo");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ((DijagramView)MainFrame.getInstance().getPackageView().getjTabbedPane().getSelectedComponent()).getCommandManager().undoCommand();
    }
}

