package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.EnumM;
import raf.dsw.classycraft.app.commands.AbstractCommand;
import raf.dsw.classycraft.app.commands.implementation.AddNameCommand;
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

        //greske pri unosu
        if( MainFrame.getInstance().getEnumProzor().getTfIme().getText().isEmpty() || MainFrame.getInstance().getEnumProzor().getTfIme().getText().equals(" ") ){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Novo ime nije pravilno uneto!", MessageType.ERROR);
            return;
        }
        else if(MainFrame.getInstance().getEnumProzor().getTfIme().getText().trim().contains(" ")){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Ime ne sme da sadrzi razmak!", MessageType.ERROR);
            return;
        }

        //provera da nije duplikat ime u odnosu na ostale enume
        for(ElementPainter o : dijagramView.getElementPainterList()){
            if (o instanceof InterclassPainter){
                if( ((Interclass) o.getElement()).getNaziv().equals(MainFrame.getInstance().getEnumProzor().getTfIme().getText()) ){
                    ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Zeljeno ime je vec rezervisano!", MessageType.ERROR);
                    MainFrame.getInstance().getEnumProzor().getTfIme().setText("");
                    return;
                }
            }
        }
        /*
        //menjanje imena
        ((EnumM) enumPainter.getElement()).setNaziv(MainFrame.getInstance().getEnumProzor().getTfIme().getText());
        ((EnumM) enumPainter.getElement()).projectChanged();
        dijagramView.repaint();
        */
        AbstractCommand command = new AddNameCommand(MainFrame.getInstance().getEnumProzor().getTfIme().getText(), ((EnumM) enumPainter.getElement()).getNaziv(), (EnumM) enumPainter.getElement(), dijagramView);
        ((DijagramView)MainFrame.getInstance().getPackageView().getjTabbedPane().getSelectedComponent()).getCommandManager().addCommand(command);

        ((EnumM) enumPainter.getElement()).projectChanged();

        MainFrame.getInstance().getEnumProzor().getTfIme().setText("");
    }
}
