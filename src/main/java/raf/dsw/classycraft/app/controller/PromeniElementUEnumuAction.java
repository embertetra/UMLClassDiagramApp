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

public class PromeniElementUEnumuAction extends AbstractClassyAction{

    private EnumPainter enumPainter;
    private DijagramView dijagramView;

    public PromeniElementUEnumuAction(InterclassPainter interclassPainter, DijagramView d){

        enumPainter = (EnumPainter) interclassPainter;
        dijagramView = d;

        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(null));
        putValue(NAME, "Promeni element");
        putValue(SHORT_DESCRIPTION, "");

    }
    @Override
    public void actionPerformed(ActionEvent e) {

        //greske pri unosu:
        if(MainFrame.getInstance().getEnumProzor().getLista().getSelectedValue() == null){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Nije odabran element iz liste!", MessageType.ERROR);
            return;
        }
        else if(MainFrame.getInstance().getEnumProzor().getTfNaziv().getText().isEmpty() || MainFrame.getInstance().getEnumProzor().getTfNaziv().getText().equals(" ")){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Nije upisan novi naziv enuma!", MessageType.ERROR);
            return;
        }
        else if(((EnumM) enumPainter.getElement()).getListEnuma().contains(MainFrame.getInstance().getEnumProzor().getTfNaziv().getText().toUpperCase())){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Zeljen naziv je zauzet!", MessageType.ERROR);
            MainFrame.getInstance().getEnumProzor().getTfNaziv().setText("");
            return;
        }
        else if(MainFrame.getInstance().getEnumProzor().getTfNaziv().getText().toUpperCase().trim().contains(" ")){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Naziv enuma ne sme da sadrzi razmak!", MessageType.ERROR);
            MainFrame.getInstance().getEnumProzor().getTfNaziv().setText("");
            return;
        }

        //promena
        String naziv = MainFrame.getInstance().getEnumProzor().getTfNaziv().getText().toUpperCase();

        int index = 0;
        for(int i=0; i<((EnumM) enumPainter.getElement()).getListEnuma().size(); i++){
            if( ((EnumM) enumPainter.getElement()).getListEnuma().get(i).equals(MainFrame.getInstance().getEnumProzor().getLista().getSelectedValue())){
                ((EnumM) enumPainter.getElement()).projectChanged();
                index = i;
            }
        }

        MainFrame.getInstance().getEnumProzor().getEnumMList().set(index, naziv);


        dijagramView.repaint();
        MainFrame.getInstance().getEnumProzor().getTfIme().setText("");
        MainFrame.getInstance().getEnumProzor().getTfNaziv().setText("");
        MainFrame.getInstance().getEnumProzor().setEnumMList(((EnumM) enumPainter.getElement()).getListEnuma());
    }
}
