package raf.dsw.classycraft.app.stateSablon.states;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Klasa;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Vidljivost;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.KlasaPainter;
import raf.dsw.classycraft.app.stateSablon.State;

import java.awt.*;

public class AddInterclass implements State {

    private String interclass;

    @Override
    public void misKliknut(int x, int y, DijagramView dijagramView) {

        Dijagram d = (Dijagram)dijagramView.getClassyNode();

        System.out.println("kreirana nova klasica :) " + interclass);
        d.addSubscriber(dijagramView);
        //d.addSubscriber(new DijagramView(null));
        Klasa klasa = new Klasa("name", dijagramView.getClassyNode(), 2, "naziv", Vidljivost.PUBLIC, new Point(x,y));
        KlasaPainter klasaPainter = new KlasaPainter(klasa);
        dijagramView.getElementPainterList().add(klasaPainter);
        d.addChild(klasa);
    }

    @Override
    public void misOtpusten(int x, int y, DijagramView dijagramView) {

    }

    @Override
    public void misPrivucen(int x, int y, DijagramView dijagramView) {

    }

    public void setInterclass(String interclass) {
        this.interclass = interclass;
    }
}
