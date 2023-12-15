package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Connection;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Agregacija;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Kompozicija;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.MessageType;
import raf.dsw.classycraft.app.gui.swing.view.KompAgregProzor;
import raf.dsw.classycraft.app.gui.swing.view.painters.ConnectionPainter;

import java.awt.event.ActionEvent;

public class KompAregPotvrdi extends AbstractClassyAction{

    private KompAgregProzor prozor;
    private ConnectionPainter connectionPainter;

    public KompAregPotvrdi(KompAgregProzor kompAgregProzor, ConnectionPainter connection) {
        this.prozor = kompAgregProzor;
        this.connectionPainter = connection;
        putValue(NAME, "Potvrdi");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(prozor.getLblname().getText().isEmpty() || prozor.getLblname().getText().equals(" ")) {
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Niste uneli ime", MessageType.ERROR);
            return;
        }
        else if(prozor.getLblTip().getText().isEmpty() || prozor.getLblTip().getText().equals(" ")) {
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Niste uneli tip promenljive", MessageType.ERROR);
            return;
        }
        else if(prozor.getCbVidljivost().getSelectedItem() == "-") {
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Niste odabrali vidljivost", MessageType.ERROR);
            return;
        }
        else if(prozor.getCbKardinalnost().getSelectedItem() == "-") {
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Niste odabrali kardinalnost", MessageType.ERROR);
            return;
        }

        if(connectionPainter != null){
            Connection con = (Connection) connectionPainter.getElement();
            if(con instanceof Agregacija){
                Agregacija a = (Agregacija) con;
                a.setName2(prozor.getTfName().getText());
                a.setTip(prozor.getTfTip().getText());
                a.setKardinalnost((String) prozor.getCbKardinalnost().getSelectedItem());
                a.setVidljivost((String) prozor.getCbVidljivost().getSelectedItem());
            }
            else if(con instanceof Kompozicija){
                Kompozicija k = (Kompozicija) con;
                k.setName2(prozor.getTfName().getText());
                k.setTip(prozor.getTfTip().getText());
                k.setKardinalnost((String) prozor.getCbKardinalnost().getSelectedItem());
                k.setVidljivost((String) prozor.getCbVidljivost().getSelectedItem());
            }
        }

        prozor.setVisible(false);
    }

    public KompAgregProzor getProzor() {
        return prozor;
    }

    public void setProzor(KompAgregProzor kompAgregProzor) {
        this.prozor = kompAgregProzor;
    }

    public ConnectionPainter getConnectionPainter() {
        return connectionPainter;
    }

    public void setConnectionPainter(ConnectionPainter connectionPainter) {
        this.connectionPainter = connectionPainter;
    }
}
