package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Klasa;
import raf.dsw.classycraft.app.commands.AbstractCommand;
import raf.dsw.classycraft.app.commands.implementation.ObrisiContentCommand;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.MessageType;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.KlasaPainter;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ObrisiIzKlaseAction extends AbstractClassyAction{

    private KlasaPainter klasaPainter;
    private DijagramView dijagramView;

    public ObrisiIzKlaseAction(InterclassPainter interclassPainter, DijagramView d){

        klasaPainter = (KlasaPainter) interclassPainter;
        dijagramView = d;

        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(null));
        putValue(NAME, "Obrisi element");
        putValue(SHORT_DESCRIPTION, "");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //greske pri unosu:
        if(MainFrame.getInstance().getKlasaProzor().getLista().getSelectedValue() == null){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Nije odabran element koji se brise!", MessageType.ERROR);
            return;
        }

        /*
        ((Klasa) klasaPainter.getElement()).getClassContentList().remove(MainFrame.getInstance().getKlasaProzor().getLista().getSelectedValue());
        MainFrame.getInstance().getKlasaProzor().setClassContentList(((Klasa) klasaPainter.getElement()).getClassContentList());
        dijagramView.repaint();
        */
        AbstractCommand command = new ObrisiContentCommand((Klasa) klasaPainter.getElement(), dijagramView);
        ((DijagramView) MainFrame.getInstance().getPackageView().getjTabbedPane().getSelectedComponent()).getCommandManager().addCommand(command);

        MainFrame.getInstance().getKlasaProzor().getBg().clearSelection();
        MainFrame.getInstance().getKlasaProzor().getBgVidljivost().clearSelection();
        MainFrame.getInstance().getKlasaProzor().getBgTip().clearSelection();
    }
}
