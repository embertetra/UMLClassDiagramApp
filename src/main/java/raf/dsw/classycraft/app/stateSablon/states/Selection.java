package raf.dsw.classycraft.app.stateSablon.states;
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
        dijagramView.setSelection(null);
        dijagramView.setShape(null);
        dijagramView.repaint();
    }

    @Override
    public void misPrivucen(int x, int y, DijagramView dijagramView) {
        dijagramView.getSelectionModel().clear();
        dijagramView.setSelection(new Rectangle(x,y,-1,-1));
    }

    @Override
    public void misDragged(int x, int y, DijagramView d) {
        if (d.getSelection() != null) {
            d.setShape(new GeneralPath());
            ((GeneralPath) d.getShape()).moveTo(d.getSelection().x, d.getSelection().y);
            ((GeneralPath) d.getShape()).lineTo(x, d.getSelection().y);
            ((GeneralPath) d.getShape()).lineTo(x, y);
            ((GeneralPath) d.getShape()).lineTo(d.getSelection().x, y);
            ((GeneralPath) d.getShape()).closePath();
            d.repaint();
        }
        d.getSelectionModel().clear();
        for (ElementPainter el : d.getElementPainterList()) {
            if (el instanceof InterclassPainter) {
                InterclassPainter ip = (InterclassPainter) el;
                Interclass ic = (Interclass) ip.getElement();
                Rectangle rec = new Rectangle(ic.getPosition().x - ip.getWidth() / 2 - 5, ic.getPosition().y - ip.getHeightUkupno() / 2,
                        ip.getWidth() + 10, ip.getHeightUkupno() + 10);
                if (d.getShape() != null && d.getShape().intersects(rec))
                    d.getSelectionModel().add(new Rectangle(rec.x - 5, rec.y - 5, rec.width + 10, rec.height + 10));
            } else if (el instanceof ConnectionPainter) {
                ConnectionPainter cp = (ConnectionPainter) el;
                if (d.getShape() != null && cp.getPoint1() != null && cp.getPoint2() != null && d.getShape().getBounds2D().intersectsLine(cp.getPoint1().x, cp.getPoint1().y, cp.getPoint2().x, cp.getPoint2().y)) {
                    Shape shape2 = new GeneralPath();
                    ((GeneralPath) shape2).moveTo(cp.getPoint1().x, cp.getPoint1().y);
                    ((GeneralPath) shape2).lineTo(cp.getPoint1().x, cp.getPoint2().y);
                    ((GeneralPath) shape2).lineTo(cp.getPoint2().x, cp.getPoint2().y);
                    ((GeneralPath) shape2).lineTo(cp.getPoint2().x, cp.getPoint1().y);
                    ((GeneralPath) shape2).lineTo(cp.getPoint1().x, cp.getPoint1().y);
                    d.getSelectionModel().add(shape2);
                }
            }
        }
    }

    @Override
    public void wheelMove(int x, int y, DijagramView dijagramView) {

    }
}
