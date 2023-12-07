package raf.dsw.classycraft.app.controller.MouseListeners;

import javafx.util.Pair;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class CreateLineListener implements MouseMotionListener {
    private DijagramView d;

    public CreateLineListener(DijagramView d) {
        this.d = d;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (d.getLine() != null && d.getLine().getKey() != null) {
            d.setLine(new Pair<>(d.getLine().getKey(), new Point(e.getX(),e.getY())));
            d.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
