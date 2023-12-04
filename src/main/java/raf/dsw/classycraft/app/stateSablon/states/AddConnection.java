package raf.dsw.classycraft.app.stateSablon.states;

import javafx.util.Pair;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Agregacija;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Generalizacija;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Kompozicija;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Zavisnost;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter.AgregacijaPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter.GeneralizacijaPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter.KompozicijaPainter;
import raf.dsw.classycraft.app.stateSablon.State;

import java.awt.*;

public class AddConnection implements State {

    private String connection;
    private Agregacija a;
    private Kompozicija k;
    private Generalizacija g;
    private Zavisnost z;
    @Override
    public void misKliknut(int x, int y, DijagramView dijagramView) {

    }

    @Override
    public void misOtpusten(int x, int y, DijagramView dijagramView) {

        Dijagram d = (Dijagram)dijagramView.getClassyNode();
        d.addSubscriber(dijagramView);

        if(connection.equals("agregacija")){
            for(ElementPainter e : dijagramView.getElementPainterList()){
                if(e instanceof InterclassPainter) {
                    if (e.elementAt(new Point(x, y))){
                        a.setTo((Interclass) e.getElement());
                    }
                }
            }
            if(a.getTo() != null){
                AgregacijaPainter ap = new AgregacijaPainter(a);
                dijagramView.getElementPainterList().add(ap);
                d.addChild(a);
            }
        }
        else if(connection.equals("kompozicija")){
            for(ElementPainter e : dijagramView.getElementPainterList()){
                if(e instanceof InterclassPainter) {
                    if (e.elementAt(new Point(x, y))){
                        k.setTo((Interclass) e.getElement());
                    }
                }
            }
            if(k.getTo() != null){
                KompozicijaPainter kp = new KompozicijaPainter(k);
                dijagramView.getElementPainterList().add(kp);
                d.addChild(k);
            }
        }
        else if(connection.equals("generalizacija")){
            for(ElementPainter e : dijagramView.getElementPainterList()){
                if(e instanceof InterclassPainter) {
                    if (e.elementAt(new Point(x, y))){
                        g.setTo((Interclass) e.getElement());
                    }
                }
            }
            if(g.getTo() != null){
                GeneralizacijaPainter gp = new GeneralizacijaPainter(g);
                dijagramView.getElementPainterList().add(gp);
                d.addChild(g);
            }
        }
        else if(connection.equals("zavisnost")){

        }
        dijagramView.setLine(new Pair<>(new Point(-1,-1), new Point(0,0)));
        dijagramView.repaint();
    }

    @Override
    public void misPrivucen(int x, int y, DijagramView dijagramView) {
        if(connection != null) {
            if (connection.equals("agregacija")) {
                a = new Agregacija("agregacija", dijagramView.getClassyNode(), 2, null, null);
                for (ElementPainter e : dijagramView.getElementPainterList()) {
                    if (e instanceof InterclassPainter) {
                        if (e.elementAt(new Point(x, y))) {
                            a.setFrom((Interclass) e.getElement());
                            dijagramView.setLine(new Pair<>(new Point(x, y), null));
                        }
                    }
                }
            } else if (connection.equals("kompozicija")) {

                k = new Kompozicija("kompozicija", dijagramView.getClassyNode(), 2, null, null);
                for (ElementPainter e : dijagramView.getElementPainterList()) {
                    if (e instanceof InterclassPainter) {
                        if (e.elementAt(new Point(x, y))) {
                            k.setFrom((Interclass) e.getElement());
                            dijagramView.setLine(new Pair<>(new Point(x, y), null));
                        }
                    }
                }

            } else if (connection.equals("generalizacija")) {

                g = new Generalizacija("generalizacija", dijagramView.getClassyNode(), 2, null, null);
                for (ElementPainter e : dijagramView.getElementPainterList()) {
                    if (e instanceof InterclassPainter) {
                        if (e.elementAt(new Point(x, y))) {
                            g.setFrom((Interclass) e.getElement());
                            dijagramView.setLine(new Pair<>(new Point(x, y), null));
                        }
                    }
                }

            } else if (connection.equals("zavisnost")) {

            }
        }

    }

    public void setConnection(String connection) {
        this.connection = connection;
    }
}
