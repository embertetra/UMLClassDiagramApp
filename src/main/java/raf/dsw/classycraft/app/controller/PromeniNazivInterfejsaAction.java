package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Interfejs;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.MessageType;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.InterfejsPainter;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PromeniNazivInterfejsaAction extends AbstractClassyAction{

    private InterfejsPainter interfejsPainter;

    private DijagramView dijagramView;

    public PromeniNazivInterfejsaAction(InterclassPainter interclassPainter, DijagramView d){

        interfejsPainter = (InterfejsPainter) interclassPainter;
        dijagramView = d;

        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(null));
        putValue(NAME, "Promeni ime");
        putValue(SHORT_DESCRIPTION, "");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if( MainFrame.getInstance().getInterfejsProzor().getTfIme().getText().isEmpty() || MainFrame.getInstance().getInterfejsProzor().getTfIme().getText().equals(" ") ){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Novo ime nije pravilno uneto!", MessageType.ERROR);
            return;
        }

        for(ElementPainter o : dijagramView.getElementPainterList()){
            if (o instanceof InterclassPainter){
                if( ((Interclass) o.getElement()).getNaziv().equals(MainFrame.getInstance().getInterfejsProzor().getTfIme().getText()) ){
                    ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Zeljeno ime je vec rezervisano!", MessageType.ERROR);
                    MainFrame.getInstance().getInterfejsProzor().getTfIme().setText("");
                    return;
                }
            }
        }

        ((Interfejs) interfejsPainter.getElement()).setNaziv(MainFrame.getInstance().getInterfejsProzor().getTfIme().getText());
        dijagramView.repaint();
        MainFrame.getInstance().getInterfejsProzor().getTfIme().setText("");
    }
}
