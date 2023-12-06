package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.EnumM;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.MessageType;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.EnumPainter;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DodajUEnumAction extends AbstractClassyAction{
    private EnumPainter enumPainter;
    private DijagramView dijagramView;

    public DodajUEnumAction(InterclassPainter interclassPainter, DijagramView d) {

        enumPainter = (EnumPainter) interclassPainter;
        dijagramView = d;

        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(null));
        putValue(NAME, "Dodaj");
        putValue(SHORT_DESCRIPTION, "");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //greske pri unosu podataka:
        if(MainFrame.getInstance().getEnumProzor().getTfNaziv().getText().isEmpty() || MainFrame.getInstance().getEnumProzor().getTfNaziv().getText().equals(" ")){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Nije upisano ime!", MessageType.ERROR);
            return;
        }

        String naziv = MainFrame.getInstance().getEnumProzor().getTfNaziv().getText().toUpperCase();

        //provera da li je duplikat
        if(((EnumM) enumPainter.getElement()).getListEnuma().contains(naziv)){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Zeljeno ime je vec rezervisano!", MessageType.ERROR);
            MainFrame.getInstance().getEnumProzor().getTfNaziv().setText("");
            return;
        }
        ((EnumM) enumPainter.getElement()).getListEnuma().add(naziv);

        dijagramView.repaint();
        MainFrame.getInstance().getEnumProzor().getTfNaziv().setText("");
    }
}
