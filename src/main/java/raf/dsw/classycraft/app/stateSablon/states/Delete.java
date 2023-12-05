package raf.dsw.classycraft.app.stateSablon.states;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;
import raf.dsw.classycraft.app.stateSablon.State;

import java.awt.*;
import java.util.Iterator;

public class Delete implements State {

    @Override
    public void misKliknut(int x, int y, DijagramView dijagramView) {

        for(Iterator<ElementPainter> iterator = dijagramView.getElementPainterList().iterator(); iterator.hasNext();){
            ElementPainter el = iterator.next();
            if(el.elementAt(new Point(x,y))){
                Dijagram d = (Dijagram) el.getElement().getParent();
                d.removeChild(el.getElement());
                iterator.remove();
            }
        }

    }

    @Override
    public void misOtpusten(int x, int y, DijagramView dijagramView) {

    }

    @Override
    public void misPrivucen(int x, int y, DijagramView dijagramView) {

    }
}
