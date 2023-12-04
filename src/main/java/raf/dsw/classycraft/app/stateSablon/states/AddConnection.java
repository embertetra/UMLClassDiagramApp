package raf.dsw.classycraft.app.stateSablon.states;

import javafx.util.Pair;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Agregacija;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter.AgregacijaPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.KlasaPainter;
import raf.dsw.classycraft.app.stateSablon.State;

import java.awt.*;

public class AddConnection implements State {

    private String connection;
    private Agregacija a;
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
        else if(connection.equals("generalizacija")){

        }
        else if(connection.equals("kompozicija")){

        }
        else if(connection.equals("zavisnost")){

        }

        dijagramView.setLine(new Pair<>(new Point(-1,-1), new Point(0,0)));
        dijagramView.repaint();
    }

    @Override
    public void misPrivucen(int x, int y, DijagramView dijagramView) {

        if(connection.equals("agregacija")){
            a = new Agregacija("agregacija", dijagramView.getClassyNode(),2, null, null);
            for(ElementPainter e : dijagramView.getElementPainterList()){
                if(e instanceof KlasaPainter) {
                    if (e.elementAt(new Point(x, y))){
                        a.setFrom((Interclass) e.getElement());
                        dijagramView.setLine(new Pair<>(new Point(x,y), null));
                    }
                }
            }
        }
        else if(connection.equals("generalizacija")){

        }
        else if(connection.equals("kompozicija")){

        }
        else if(connection.equals("zavisnost")){

        }


    }

    public void setConnection(String connection) {
        this.connection = connection;
    }
}
