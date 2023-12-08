package raf.dsw.classycraft.app.controller.MouseListeners;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.painters.ConnectionPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.GeneralPath;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

public class CreateSelectionListener implements MouseMotionListener {
    private DijagramView d;
    public CreateSelectionListener(DijagramView dijagramView) {
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

        if (d.getSelection() != null) {
            d.setShape(new GeneralPath());
            ((GeneralPath) d.getShape()).moveTo(d.getSelection().x, d.getSelection().y);
            ((GeneralPath) d.getShape()).lineTo(point.getX(), d.getSelection().y);
            ((GeneralPath) d.getShape()).lineTo(point.getX(), point.getY());
            ((GeneralPath) d.getShape()).lineTo(d.getSelection().x, point.getY());
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
    public void mouseMoved(MouseEvent e) {

    }
}
