package raf.dsw.classycraft.app.controller.MouseListeners;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Connection;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.painters.ConnectionPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

public class CreateTranslationListener implements MouseMotionListener {
    private DijagramView d;

    public CreateTranslationListener(DijagramView dijagramView) {
        this.d = dijagramView;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        Point2D point = e.getPoint();
        try {
            point = d.getAt().inverseTransform(e.getPoint(), null);
        } catch (NoninvertibleTransformException ex) {
            throw new RuntimeException(ex);
        }

        if (d.getStartPoint() != null) {
            int diffX = (int) (point.getX() - d.getStartPoint().x);
            int diffY = (int) (point.getY() - d.getStartPoint().y);

            if (d.getFlag1() != null) {
                for (ElementPainter ep : d.getElementPainterList()) {
                    if (ep instanceof InterclassPainter) {
                        InterclassPainter ip = (InterclassPainter) ep;
                        Interclass ic = (Interclass) ip.getElement();
                        if (ip == d.getFlag1()) {
                            ic.setPosition(new Point(d.getStartPoint().x + diffX - d.getxDragOffset(), d.getStartPoint().y + diffY - d.getyDragOffset()));
                            //d.repaint();
                            return;
                        }
                    }
                }
            } else if (d.getMoveSelections() != null && d.getMoveSelections().size() > 0) {
                for (InterclassPainter ip : d.getMoveSelections()) {
                    Interclass ic = (Interclass) ip.getElement();
                    ic.setPosition(new Point(d.getStartPoint().x + diffX - ip.getxDragOffset(),
                            d.getStartPoint().y + diffY - ip.getyDragOffset()));
                }
                //d.repaint();
                return;
            } else {
                for (ElementPainter ep : d.getElementPainterList()) {
                    if (ep instanceof InterclassPainter) {
                        InterclassPainter ip = ((InterclassPainter) ep);
                        ((Interclass) ip.getElement()).setPosition(new Point(d.getStartPoint().x + diffX - ip.getxDragOffset(),
                                d.getStartPoint().y + diffY - ip.getyDragOffset()));
                    }
                }
                for (ElementPainter ep : d.getElementPainterList()) {
                    if (ep instanceof ConnectionPainter) {
                        ConnectionPainter cp = (ConnectionPainter) ep;
                        Interclass from = ((Connection) cp.getElement()).getFrom();
                        Interclass to = ((Connection) cp.getElement()).getTo();
                        if(d.getStartPoint()!= null) {
                            from.setPosition(new Point(d.getStartPoint().x + diffX - cp.getDragOffset1().x,
                                    d.getStartPoint().y + diffY - cp.getDragOffset1().y));
                            to.setPosition(new Point(d.getStartPoint().x + diffX - cp.getDragOffset2().x,
                                    d.getStartPoint().y + diffY - cp.getDragOffset2().y));
                        }
                    }
                }
            }
            d.repaint();
        }


    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
