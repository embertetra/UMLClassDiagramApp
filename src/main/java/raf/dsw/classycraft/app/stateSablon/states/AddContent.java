package raf.dsw.classycraft.app.stateSablon.states;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Connection;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Agregacija;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Kompozicija;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Zavisnost;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.EnumM;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Interfejs;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Klasa;
import raf.dsw.classycraft.app.controller.*;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painters.ConnectionPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter.AgregacijaPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter.KompozicijaPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter.ZavisnostPainter;
import raf.dsw.classycraft.app.stateSablon.State;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class AddContent implements State {

    @Override
    public void misKliknut(int x, int y, DijagramView dijagramView) {

        InterclassPainter interclassPainter = null;
        ConnectionPainter connectionPainter = null;

        Shape s = new GeneralPath();
        ((GeneralPath)s).moveTo(x-6, y-6);
        ((GeneralPath)s).lineTo(x+6, y-6);
        ((GeneralPath)s).lineTo(x+6, y+6);
        ((GeneralPath)s).lineTo(x-6, y+6);
        ((GeneralPath)s).closePath();

        for(ElementPainter ep : dijagramView.getElementPainterList()){
            if(ep instanceof ConnectionPainter){
                ConnectionPainter cp = (ConnectionPainter) ep;
                Connection c = (Connection) ep.getElement();
                if(s.getBounds().intersectsLine(cp.getPoint1().x, cp.getPoint1().y, cp.getPoint2().x, cp.getPoint2().y)){
                    connectionPainter = (ConnectionPainter) ep;
                }
            }

        }
        for(ElementPainter ep : dijagramView.getElementPainterList()){
            if(ep.elementAt(new Point(x,y)) && ep instanceof InterclassPainter)
               interclassPainter = (InterclassPainter) ep;
        }

        if(connectionPainter != null && interclassPainter == null){
            if(connectionPainter instanceof AgregacijaPainter){
                Agregacija a = (Agregacija) connectionPainter.getElement();
                MainFrame.getInstance().getKompAgregProzor().getTfName().setText(a.getName2());
                MainFrame.getInstance().getKompAgregProzor().getTfTip().setText(a.getTip());
                MainFrame.getInstance().getKompAgregProzor().getCbVidljivost().setSelectedItem(a.getVidljivost());
                MainFrame.getInstance().getKompAgregProzor().getCbKardinalnost().setSelectedItem(a.getKardinalnost());
                MainFrame.getInstance().getKompAgregProzor().setConnectionPainter(connectionPainter);
                MainFrame.getInstance().getKompAgregProzor().setVisible(true);
            }
            else if(connectionPainter instanceof KompozicijaPainter){
                Kompozicija k = (Kompozicija) connectionPainter.getElement();
                MainFrame.getInstance().getKompAgregProzor().getTfName().setText(k.getName2());
                MainFrame.getInstance().getKompAgregProzor().getTfTip().setText(k.getTip());
                MainFrame.getInstance().getKompAgregProzor().getCbVidljivost().setSelectedItem(k.getVidljivost());
                MainFrame.getInstance().getKompAgregProzor().getCbKardinalnost().setSelectedItem(k.getKardinalnost());
                MainFrame.getInstance().getKompAgregProzor().setConnectionPainter(connectionPainter);
                MainFrame.getInstance().getKompAgregProzor().setVisible(true);
            }
            else if(connectionPainter instanceof ZavisnostPainter){
                Zavisnost z = (Zavisnost) connectionPainter.getElement();
                MainFrame.getInstance().getDependencyProzor().getComboBox().setSelectedItem(z.getCallOrInstantiate());
                MainFrame.getInstance().getDependencyProzor().setPainter((ZavisnostPainter) connectionPainter);
                MainFrame.getInstance().getDependencyProzor().setVisible(true);
            }
        }

        if(interclassPainter != null) {
            if (interclassPainter.getElement() instanceof Klasa) {
                Klasa k = (Klasa) interclassPainter.getElement();
                MainFrame.getInstance().getKlasaProzor().setClassContentList(k.getClassContentList());
                MainFrame.getInstance().getKlasaProzor().getJbPromeni().setAction(new PromeniElementUKlasiAction(interclassPainter, dijagramView));
                MainFrame.getInstance().getKlasaProzor().getJbObrisi().setAction(new ObrisiIzKlaseAction(interclassPainter, dijagramView));
                MainFrame.getInstance().getKlasaProzor().getJbIme().setAction(new PromenaNazivaKlaseAction(interclassPainter, dijagramView));
                MainFrame.getInstance().getKlasaProzor().getJbDodaj().setAction(new DodajUKlasuAction(interclassPainter, dijagramView));
                MainFrame.getInstance().getKlasaProzor().setVisible(true);
            } else if (interclassPainter.getElement() instanceof Interfejs) {
                Interfejs i = (Interfejs) interclassPainter.getElement();
                MainFrame.getInstance().getInterfejsProzor().setMetodeList(i.getMetodeList());
                MainFrame.getInstance().getInterfejsProzor().getJbPromeni().setAction(new PromeniElementUInterfejsuAction(interclassPainter, dijagramView));
                MainFrame.getInstance().getInterfejsProzor().getJbObrisi().setAction(new ObrisiIzInterfejsaAction(interclassPainter, dijagramView));
                MainFrame.getInstance().getInterfejsProzor().getJbIme().setAction(new PromenaNazivaInterfejsaAction(interclassPainter, dijagramView));
                MainFrame.getInstance().getInterfejsProzor().getJbDodaj().setAction(new DodajUInterfejsAction(interclassPainter, dijagramView));
                MainFrame.getInstance().getInterfejsProzor().setVisible(true);
            } else if (interclassPainter.getElement() instanceof EnumM) {
                EnumM e = (EnumM) interclassPainter.getElement();
                MainFrame.getInstance().getEnumProzor().setEnumMList(e.getListEnuma());
                MainFrame.getInstance().getEnumProzor().getJbPromeni().setAction(new PromeniElementUEnumuAction(interclassPainter, dijagramView));
                MainFrame.getInstance().getEnumProzor().getJbObrisi().setAction(new ObrisiIzEnumaAction(interclassPainter, dijagramView));
                MainFrame.getInstance().getEnumProzor().getJbIme().setAction(new PromenaNazivaEnumaAction(interclassPainter, dijagramView));
                MainFrame.getInstance().getEnumProzor().getJbDodaj().setAction(new DodajUEnumAction(interclassPainter, dijagramView));
                MainFrame.getInstance().getEnumProzor().setVisible(true);
            }
        }
        dijagramView.getSelectionModel().clear();
        dijagramView.setSelection(null);
        dijagramView.repaint();
        System.out.println("Dodat kontent");
    }

    @Override
    public void misOtpusten(int x, int y, DijagramView dijagramView) {

    }

    @Override
    public void misPrivucen(int x, int y, DijagramView dijagramView) {
        dijagramView.getSelectionModel().clear();
        dijagramView.setSelection(null);
        dijagramView.repaint();
    }

    @Override
    public void misDragged(int x, int y, DijagramView dijagramView) {

    }

    @Override
    public void wheelMove(int x, int y, DijagramView dijagramView) {

    }
}
