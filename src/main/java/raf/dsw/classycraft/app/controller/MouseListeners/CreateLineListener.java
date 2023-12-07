package raf.dsw.classycraft.app.controller.MouseListeners;

import javafx.util.Pair;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

public class CreateLineListener implements MouseMotionListener {
    private DijagramView d;

    public CreateLineListener(DijagramView d) {
        this.d = d;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point2D point = e.getPoint();
        try {
            point = d.getAt().inverseTransform(e.getPoint(), null);
        } catch (NoninvertibleTransformException ex) {
            throw new RuntimeException(ex);
        }

            if (d.getLine() != null && d.getLine().getKey() != null) {
                d.setLine(new Pair<>(d.getLine().getKey(), new Point((int) point.getX(), (int) point.getY())));
                d.repaint();
            }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
