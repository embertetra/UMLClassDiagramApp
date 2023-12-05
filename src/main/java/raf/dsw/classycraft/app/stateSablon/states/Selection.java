package raf.dsw.classycraft.app.stateSablon.states;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.DijagramElement;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;
import raf.dsw.classycraft.app.stateSablon.State;

import java.awt.*;

public class Selection implements State {


    @Override
    public void misKliknut(int x, int y, DijagramView dijagramView) {

        dijagramView.getSelectionModel().clear();

        ElementPainter el=null;
        for(ElementPainter e : dijagramView.getElementPainterList()){
            if(e.elementAt(new Point(x,y)))
                el = e;
        }
        if(el != null && el instanceof InterclassPainter){
            InterclassPainter ip = (InterclassPainter) el;
            int width = ip.getWidth();
            int height = ip.getHeightUkupno();
            Interclass de = (Interclass) ip.getElement();
            dijagramView.setSelection(new Rectangle(de.getPosition().x - width/2-10, de.getPosition().y - height/2-5,width+20, height+20));
            dijagramView.repaint();
        }

    }

    @Override
    public void misOtpusten(int x, int y, DijagramView dijagramView) {
        dijagramView.setShape(null);
        dijagramView.repaint();
    }

    @Override
    public void misPrivucen(int x, int y, DijagramView dijagramView) {
        dijagramView.setSelection(new Rectangle(x,y,1,1));
    }
}
