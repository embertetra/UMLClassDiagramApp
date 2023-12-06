package raf.dsw.classycraft.app.stateSablon.states;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Connection;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.painters.ConnectionPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;
import raf.dsw.classycraft.app.stateSablon.State;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.util.Iterator;

public class Delete implements State {

    @Override
    public void misKliknut(int x, int y, DijagramView dijagramView) {

        int flag = 0;
        for(Shape s : dijagramView.getSelectionModel()){
            if(s.contains(new Point(x,y)))flag=1;
        }
        ///brisanje multi selekcije
        if (dijagramView.getSelectionModel().size() > 0 && flag == 1) {
            System.out.println("usao");
            for (Iterator<ElementPainter> iterator = dijagramView.getElementPainterList().iterator(); iterator.hasNext(); ) {
                ElementPainter el = iterator.next();

                for (Shape s : dijagramView.getSelectionModel()) {
                    if (el instanceof ConnectionPainter) {
                        ConnectionPainter cp = (ConnectionPainter) el;
                        if (cp.getPoint1() != null && cp.getPoint2() != null && s.getBounds2D().intersectsLine(cp.getPoint1().x, cp.getPoint1().y, cp.getPoint2().x, cp.getPoint2().y)) {
                            Dijagram d = (Dijagram) el.getElement().getParent();
                            d.removeChild(el.getElement());
                            iterator.remove();
                            break;
                        }
                    }
                }
            }
            for (Iterator<ElementPainter> iterator = dijagramView.getElementPainterList().iterator(); iterator.hasNext(); ) {
                ElementPainter el = iterator.next();

                for (Shape s : dijagramView.getSelectionModel()) {
                    if (el instanceof InterclassPainter) {
                        InterclassPainter ip = (InterclassPainter) el;
                        if (s.getBounds2D().intersectsLine(ip.getConnectionPoints().get(0).x, ip.getConnectionPoints().get(0).y,
                                ip.getConnectionPoints().get(1).x, ip.getConnectionPoints().get(1).y) && s.getBounds2D().intersectsLine(ip.getConnectionPoints().get(2).x,
                                ip.getConnectionPoints().get(2).y, ip.getConnectionPoints().get(3).x, ip.getConnectionPoints().get(3).y)) {
                            Dijagram d = (Dijagram) el.getElement().getParent();
                            d.removeChild(el.getElement());
                            iterator.remove();
                            break;
                        }
                    }
                }
            }
            dijagramView.getSelectionModel().clear();
            return;
        }

        for (Iterator<ElementPainter> iterator = dijagramView.getElementPainterList().iterator(); iterator.hasNext(); ) {
            ElementPainter el = iterator.next();
            ///brisanje veze
            if (el instanceof ConnectionPainter) {
                Shape shape = new GeneralPath();
                ((GeneralPath) shape).moveTo(x - 10, y - 10);
                ((GeneralPath) shape).lineTo(x + 10, y - 10);
                ((GeneralPath) shape).lineTo(x + 10, y + 10);
                ((GeneralPath) shape).lineTo(x - 10, y + 10);
                ((GeneralPath) shape).lineTo(x - 10, y - 10);
                ((GeneralPath) shape).closePath();
                ConnectionPainter cp = (ConnectionPainter) el;
                Line2D line = null;
                if(cp.getPoint1() != null && cp.getPoint2() != null)
                    line = new Line2D.Double(cp.getPoint1().x, cp.getPoint1().y, cp.getPoint2().x, cp.getPoint2().y);
                if (cp.getPoint1() != null && cp.getPoint2() != null && shape.getBounds2D().intersectsLine(line)) {
                    Dijagram d = (Dijagram) el.getElement().getParent();
                    d.removeChild(el.getElement());
                    iterator.remove();
                    return;
                }
            }
            //brisanje interclasse
            if (el.elementAt(new Point(x, y))) {
                Dijagram d = (Dijagram) el.getElement().getParent();
                for(ElementPainter ep : dijagramView.getElementPainterList()){
                    if(ep instanceof ConnectionPainter){
                        ConnectionPainter cp = (ConnectionPainter) ep;
                        Connection c = (Connection) cp.getElement();
                        if(c.getFrom() == el.getElement() || c.getTo() == el.getElement())
                            d.removeChild(c);
                    }
                }
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
