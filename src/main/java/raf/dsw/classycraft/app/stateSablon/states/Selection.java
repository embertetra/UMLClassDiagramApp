package raf.dsw.classycraft.app.stateSablon.states;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.DijagramElement;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.painters.ConnectionPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;
import raf.dsw.classycraft.app.stateSablon.State;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class Selection implements State {


    @Override
    public void misKliknut(int x, int y, DijagramView dijagramView) {

        dijagramView.getSelectionModel().clear();

        ElementPainter el=null;
        for(ElementPainter e : dijagramView.getElementPainterList()){
            if(e instanceof ConnectionPainter){
                Shape s = new GeneralPath();
                ((GeneralPath)s).moveTo(x-10,y-10);
                ((GeneralPath)s).lineTo(x+10,y-10);
                ((GeneralPath)s).lineTo(x+10,y+10);
                ((GeneralPath)s).lineTo(x-10,y+10);
                ((GeneralPath)s).lineTo(x-10,y-10);
                ((GeneralPath)s).closePath();
                if(s.getBounds().intersectsLine(((ConnectionPainter) e).getPoint1().x, ((ConnectionPainter) e).getPoint1().y, ((ConnectionPainter) e).getPoint2().x, ((ConnectionPainter) e).getPoint2().y)){
                    el = e;
                }
            }
            else if(e.elementAt(new Point(x,y)))
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
        else if(el != null && el instanceof ConnectionPainter){
            System.out.println("usao ovde");
            ConnectionPainter cp = (ConnectionPainter) el;
            Shape s = new GeneralPath();
            ((GeneralPath)s).moveTo(cp.getPoint1().x, cp.getPoint1().y);
            ((GeneralPath)s).lineTo(cp.getPoint1().x, cp.getPoint2().y);
            ((GeneralPath)s).lineTo(cp.getPoint2().x, cp.getPoint2().y);
            ((GeneralPath)s).lineTo(cp.getPoint2().x, cp.getPoint1().y);
            ((GeneralPath)s).lineTo(cp.getPoint1().x, cp.getPoint1().y);
            ((GeneralPath)s).closePath();
            dijagramView.setSelection(new Rectangle((int) s.getBounds().getMinX(), (int) s.getBounds().getMinY(), s.getBounds().width, s.getBounds().height));
            dijagramView.repaint();
        }

    }

    @Override
    public void misOtpusten(int x, int y, DijagramView dijagramView) {
        dijagramView.removeSelectionListener();
        dijagramView.setShape(null);
        dijagramView.repaint();
    }

    @Override
    public void misPrivucen(int x, int y, DijagramView dijagramView) {
        dijagramView.setSelectionListener();
        dijagramView.setSelection(new Rectangle(x,y,-1,-1));
    }
}
