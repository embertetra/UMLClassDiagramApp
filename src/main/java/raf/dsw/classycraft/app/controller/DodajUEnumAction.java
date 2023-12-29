package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.EnumM;
import raf.dsw.classycraft.app.commands.AbstractCommand;
import raf.dsw.classycraft.app.commands.implementation.DodajContentCommand;
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
        putValue(NAME, "Dodaj element");
        putValue(SHORT_DESCRIPTION, "");
    }

    @Override
    public void actionPerformed(ActionEvent e) {



        //greske pri unosu podataka:
        if(MainFrame.getInstance().getEnumProzor().getTfNaziv().getText().isEmpty() || MainFrame.getInstance().getEnumProzor().getTfNaziv().getText().equals(" ")){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Nije upisan naziv enuma!", MessageType.ERROR);
            return;
        }

        String naziv = MainFrame.getInstance().getEnumProzor().getTfNaziv().getText().toUpperCase();

        //provera da li je duplikat
        if(((EnumM) enumPainter.getElement()).getListEnuma().contains(naziv)){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Zeljen naziv je zauzet!", MessageType.ERROR);
            MainFrame.getInstance().getEnumProzor().getTfNaziv().setText("");
            return;
        }
        else if(naziv.trim().contains(" ")){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Naziv enuma ne sme da sadrzi razmak!", MessageType.ERROR);
            MainFrame.getInstance().getEnumProzor().getTfNaziv().setText("");
            return;
        }

        //((EnumM) enumPainter.getElement()).getListEnuma().add(naziv);
        //dijagramView.repaint();
        AbstractCommand command = new DodajContentCommand(null, null, naziv, (EnumM) enumPainter.getElement(), dijagramView);
        ((DijagramView) MainFrame.getInstance().getPackageView().getjTabbedPane().getSelectedComponent()).getCommandManager().addCommand(command);

        MainFrame.getInstance().getEnumProzor().getTfNaziv().setText("");
        MainFrame.getInstance().getEnumProzor().setEnumMList(((EnumM) enumPainter.getElement()).getListEnuma());
    }
}
