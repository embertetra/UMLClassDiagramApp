package raf.dsw.classycraft.app.gui.swing.view.painters;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.DijagramElement;

import java.awt.*;

public abstract class ConnectionPainter extends ElementPainter {
    protected Point point1;
    protected Point point2;
    protected Point dragOffset1;
    protected Point dragOffset2;

    public ConnectionPainter(DijagramElement element) {
        super(element);
    }

    public Point getPoint1() {
        return point1;
    }

    public void setPoint1(Point point1) {
        this.point1 = point1;
    }

    public Point getPoint2() {
        return point2;
    }

    public void setPoint2(Point point2) {
        this.point2 = point2;
    }

    public Point getDragOffset1() {
        return dragOffset1;
    }

    public void setDragOffset1(Point dragOffset1) {
        this.dragOffset1 = dragOffset1;
    }

    public Point getDragOffset2() {
        return dragOffset2;
    }

    public void setDragOffset2(Point dragOffset2) {
        this.dragOffset2 = dragOffset2;
    }
}
