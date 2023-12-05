package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.EnumM;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Interfejs;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.MessageType;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.EnumPainter;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PromenaNazivaEnumaAction extends AbstractClassyAction{

    private EnumPainter enumPainter;

    private DijagramView dijagramView;

    public PromenaNazivaEnumaAction(InterclassPainter interclassPainter, DijagramView d){

        enumPainter = (EnumPainter) interclassPainter;
        dijagramView = d;

        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(null));
        putValue(NAME, "Promeni ime");
        putValue(SHORT_DESCRIPTION, "");

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if( MainFrame.getInstance().getEnumProzor().getTfIme().getText().isEmpty() || MainFrame.getInstance().getEnumProzor().getTfIme().getText().equals(" ") ){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Novo ime nije pravilno uneto!", MessageType.ERROR);
            return;
        }

        for(ElementPainter o : dijagramView.getElementPainterList()){
            if (o instanceof InterclassPainter){
                if( ((Interclass) o.getElement()).getNaziv().equals(MainFrame.getInstance().getEnumProzor().getTfIme().getText()) ){
                    ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Zeljeno ime je vec rezervisano!", MessageType.ERROR);
                    MainFrame.getInstance().getEnumProzor().getTfIme().setText("");
                    return;
                }
            }
        }

        ((EnumM) enumPainter.getElement()).setNaziv(MainFrame.getInstance().getEnumProzor().getTfIme().getText());
        dijagramView.repaint();
        MainFrame.getInstance().getEnumProzor().getTfIme().setText("");
    }
}
