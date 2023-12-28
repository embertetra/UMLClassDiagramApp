package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.controller.ExitAction;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class MyMenyBar extends JMenuBar {

    public MyMenyBar(){
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        ExitAction ea = new ExitAction();
        fileMenu.add(ea);
        fileMenu.add(MainFrame.getInstance().getActionManager().getSaveAsAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getSaveAction());
        fileMenu.add(MainFrame.getInstance().getActionManager().getImportAction());

        JMenu editMenu = new JMenu("Edit");
        editMenu.add(MainFrame.getInstance().getActionManager().getNewChildAction());
        editMenu.add(MainFrame.getInstance().getActionManager().getRemoveChild());
        editMenu.add(MainFrame.getInstance().getActionManager().getAutorAction());
        editMenu.add(MainFrame.getInstance().getActionManager().getExportDiagram());
        editMenu.add(MainFrame.getInstance().getActionManager().getSaveTemplate());

        JMenu infoMenu = new JMenu("Info");
        infoMenu.add(MainFrame.getInstance().getActionManager().getAboutUsAction());

        add(fileMenu);
        add(editMenu);
        add(infoMenu);

    }

}
