package raf.dsw.classycraft.app.controller.MouseListeners;

import raf.dsw.classycraft.app.gui.swing.view.DijagramView;

import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;

public class CreateMouseWheelListener implements MouseWheelListener {
    private DijagramView dijagramView;
    public CreateMouseWheelListener(DijagramView dijagramView) {
        this.dijagramView = dijagramView;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

        dijagramView.setZoomer(true);
        if(e.getWheelRotation() > 0) {
            dijagramView.setZoomFactor(Math.max(dijagramView.getZoomFactor() / 1.1, 0.3));
        }
        else if(e.getWheelRotation() < 0) {
            dijagramView.setZoomFactor(Math.min(dijagramView.getZoomFactor() * 1.1, 2.0));
        }

        /*Point point = e.getPoint();
        AffineTransform at = dijagramView.getAt();
        at = new AffineTransform();
        double xRel = MouseInfo.getPointerInfo().getLocation().x - point.x;
        double yRel = MouseInfo.getPointerInfo().getLocation().y - point.y;
        double zoomDiv = dijagramView.getZoomFactor() / dijagramView.getPrevZoomFactor();
        dijagramView.setxOffset((zoomDiv) * (dijagramView.getxOffset()) + (1 - zoomDiv) * e.getX());
        dijagramView.setyOffset((zoomDiv) * (dijagramView.getyOffset()) + (1 - zoomDiv) * e.getY());
        at.translate(dijagramView.getxOffset(), dijagramView.getyOffset());
        at.scale(dijagramView.getZoomFactor(), dijagramView.getZoomFactor());
        dijagramView.setAt(at);*/

        dijagramView.repaint();
    }
}
