package raf.dsw.classycraft.app.controller.MouseListeners;

import raf.dsw.classycraft.app.gui.swing.view.DijagramView;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

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

        dijagramView.repaint();
    }
}
