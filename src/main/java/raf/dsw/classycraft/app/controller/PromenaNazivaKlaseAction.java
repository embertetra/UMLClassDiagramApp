package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Klasa;
import raf.dsw.classycraft.app.commands.AbstractCommand;
import raf.dsw.classycraft.app.commands.implementation.AddNameCommand;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.MessageType;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.KlasaPainter;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PromenaNazivaKlaseAction extends AbstractClassyAction{

    private KlasaPainter klasaPainter;

    private DijagramView dijagramView;

    public PromenaNazivaKlaseAction(InterclassPainter interclassPainter, DijagramView d){

        klasaPainter = (KlasaPainter) interclassPainter;
        dijagramView = d;

        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(null));
        putValue(NAME, "Promeni ime");
        putValue(SHORT_DESCRIPTION, "");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //greske pri unosu
        if( MainFrame.getInstance().getKlasaProzor().getTfIme().getText().isEmpty() || MainFrame.getInstance().getKlasaProzor().getTfIme().getText().equals(" ") ){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Novo ime nije pravilno uneto!", MessageType.ERROR);
            return;
        }
        else if(MainFrame.getInstance().getKlasaProzor().getTfIme().getText().trim().contains(" ")){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Ime ne sme da sadrzi razmak!", MessageType.ERROR);
            return;
        }

        //provera da nije duplikat ime u odnosu na ostale klase
        for(ElementPainter o : dijagramView.getElementPainterList()){
            if (o instanceof InterclassPainter){
                if( ((Interclass) o.getElement()).getNaziv().equals(MainFrame.getInstance().getKlasaProzor().getTfIme().getText()) ){
                    ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Zeljeno ime je vec rezervisano!", MessageType.ERROR);
                    MainFrame.getInstance().getKlasaProzor().getTfIme().setText("");
                    return;
                }
            }
        }
/*
        //menjanje imena
        ((Klasa) klasaPainter.getElement()).setNaziv(MainFrame.getInstance().getKlasaProzor().getTfIme().getText());
        ((Klasa) klasaPainter.getElement()).projectChanged();
        dijagramView.repaint();
*/
        AbstractCommand command = new AddNameCommand(MainFrame.getInstance().getKlasaProzor().getTfIme().getText(), ((Klasa) klasaPainter.getElement()).getNaziv(), (Klasa) klasaPainter.getElement(), dijagramView);
        ((DijagramView)MainFrame.getInstance().getPackageView().getjTabbedPane().getSelectedComponent()).getCommandManager().addCommand(command);

        ((Klasa) klasaPainter.getElement()).projectChanged();

        MainFrame.getInstance().getKlasaProzor().getTfIme().setText("");
        MainFrame.getInstance().getKlasaProzor().getBg().clearSelection();
        MainFrame.getInstance().getKlasaProzor().getBgVidljivost().clearSelection();
        MainFrame.getInstance().getKlasaProzor().getBgTip().clearSelection();
    }
}
