package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Interfejs;
import raf.dsw.classycraft.app.commands.AbstractCommand;
import raf.dsw.classycraft.app.commands.implementation.DeleteContentCommand;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.MessageType;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.InterfejsPainter;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ObrisiIzInterfejsaAction extends AbstractClassyAction{

    private InterfejsPainter interfejsPainter;
    private DijagramView dijagramView;

    public ObrisiIzInterfejsaAction(InterclassPainter interclassPainter, DijagramView d){

        interfejsPainter = (InterfejsPainter) interclassPainter;
        dijagramView = d;

        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(null));
        putValue(NAME, "Obrisi element");
        putValue(SHORT_DESCRIPTION, "");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //greske pri unosu:
        if(MainFrame.getInstance().getInterfejsProzor().getLista().getSelectedValue() == null){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Nije odabran element koji se brise!", MessageType.ERROR);
            return;
        }
        AbstractCommand command = new DeleteContentCommand((Interfejs) interfejsPainter.getElement(), dijagramView);
        ((DijagramView) MainFrame.getInstance().getPackageView().getjTabbedPane().getSelectedComponent()).getCommandManager().addCommand(command);
        ((Interfejs) interfejsPainter.getElement()).projectChanged();

        MainFrame.getInstance().getInterfejsProzor().getBgVidljivost().clearSelection();
        MainFrame.getInstance().getInterfejsProzor().getBgTip().clearSelection();
    }
}
