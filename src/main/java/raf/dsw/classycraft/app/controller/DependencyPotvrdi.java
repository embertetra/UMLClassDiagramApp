package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Zavisnost;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.MessageType;
import raf.dsw.classycraft.app.gui.swing.view.DependencyProzor;
import raf.dsw.classycraft.app.gui.swing.view.KompAgregProzor;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter.ZavisnostPainter;

import java.awt.event.ActionEvent;

public class DependencyPotvrdi extends AbstractClassyAction{
    private DependencyProzor prozor;
    private ZavisnostPainter painter;

    public DependencyPotvrdi(DependencyProzor prozor, ZavisnostPainter painter) {
        this.prozor = prozor;
        this.painter = painter;
        putValue(NAME, "Potvrdi");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(painter != null){
            if(MainFrame.getInstance().getDependencyProzor().getComboBox().getSelectedItem() == "-") {
                ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Niste izabrali polje", MessageType.ERROR);
                return;
            }

            Zavisnost z = (Zavisnost) painter.getElement();
            z.setCallOrInstantiate((String) MainFrame.getInstance().getDependencyProzor().getComboBox().getSelectedItem());
            prozor.setVisible(false);
        }
    }
}
