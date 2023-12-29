package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.EnumM;
import raf.dsw.classycraft.app.commands.AbstractCommand;
import raf.dsw.classycraft.app.commands.implementation.ObrisiContentCommand;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.MessageType;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.EnumPainter;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ObrisiIzEnumaAction extends AbstractClassyAction{
    private EnumPainter enumPainter;
    private DijagramView dijagramView;

    public ObrisiIzEnumaAction(InterclassPainter interclassPainter, DijagramView d){

        enumPainter = (EnumPainter) interclassPainter;
        dijagramView = d;

        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(null));
        putValue(NAME, "Obrisi element");
        putValue(SHORT_DESCRIPTION, "");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //greske pri unosu:
        if(MainFrame.getInstance().getEnumProzor().getLista().getSelectedValue() == null){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Nije odabran element koji se brise!", MessageType.ERROR);
            return;
        }
    /*
        ((EnumM) enumPainter.getElement()).getListEnuma().remove(MainFrame.getInstance().getEnumProzor().getLista().getSelectedValue());
        MainFrame.getInstance().getEnumProzor().setEnumMList(((EnumM) enumPainter.getElement()).getListEnuma());
        dijagramView.repaint();

     */
        AbstractCommand command = new ObrisiContentCommand((EnumM) enumPainter.getElement(), dijagramView);
        ((DijagramView) MainFrame.getInstance().getPackageView().getjTabbedPane().getSelectedComponent()).getCommandManager().addCommand(command);
    }
}
