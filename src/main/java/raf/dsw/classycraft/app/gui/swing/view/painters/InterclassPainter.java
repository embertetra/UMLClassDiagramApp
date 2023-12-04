package raf.dsw.classycraft.app.gui.swing.view.painters;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.DijagramElement;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class InterclassPainter extends ElementPainter {

    protected int width;
    protected int heightUkupno;

    protected java.util.List<Point> connectionPoints;

    public InterclassPainter(DijagramElement element) {
        super(element);
        connectionPoints = new ArrayList<>();
    }
    public int getWidth() {
        return width;
    }

    public int getHeightUkupno() {
        return heightUkupno;
    }

    public List<Point> getConnectionPoints() {
        return connectionPoints;
    }
}
