package raf.dsw.classycraft.app.stateSablon.states;

import javafx.util.Pair;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Agregacija;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Generalizacija;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Kompozicija;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Zavisnost;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter.AgregacijaPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter.GeneralizacijaPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter.KompozicijaPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter.ZavisnostPainter;
import raf.dsw.classycraft.app.stateSablon.State;

import java.awt.*;

public class AddConnection implements State {

    private String connection;
    @Override
    public void misKliknut(int x, int y, DijagramView dijagramView) {

    }

    @Override
    public void misOtpusten(int x, int y, DijagramView dijagramView) {
        System.out.println("zavrsio sam ovde (x,y) " + x + "," + y);
        dijagramView.setLine(new Pair<>(new Point(0,0), new Point(0,0)));
        dijagramView.repaint();
    }

    @Override
    public void misPrivucen(int x, int y, DijagramView dijagramView) {


        Dijagram d = (Dijagram)dijagramView.getClassyNode();
        d.addSubscriber(dijagramView);

        if(connection.equals("agregacija")) {
            Agregacija a = new Agregacija("ime", null);
            dijagramView.setLine(new Pair<>(new Point(x, y), null));
            AgregacijaPainter ap = new AgregacijaPainter(a);
            dijagramView.getElementPainterList().add(ap);
            d.addChild(a);
        }
        else if(connection.equals("generalizacija")) {
            dijagramView.setLine(new Pair<>(new Point(x, y), null));
            Generalizacija g = new Generalizacija("ime", null);
            GeneralizacijaPainter gp = new GeneralizacijaPainter(g);
            dijagramView.getElementPainterList().add(gp);
            d.addChild(g);
        }
        else if(connection.equals("kompozicija")) {
            dijagramView.setLine(new Pair<>(new Point(x, y), null));
            Kompozicija k = new Kompozicija("ime", null);
            KompozicijaPainter kp = new KompozicijaPainter(k);
            dijagramView.getElementPainterList().add(kp);
            d.addChild(k);
        }
        else if(connection.equals("zavisnost")) {
            dijagramView.setLine(new Pair<>(new Point(x, y), null));
            Zavisnost z = new Zavisnost("ime", null);
            ZavisnostPainter zp = new ZavisnostPainter(z);
            dijagramView.getElementPainterList().add(zp);
            d.addChild(z);
        }
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }
}
