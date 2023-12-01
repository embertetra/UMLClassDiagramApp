package raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;

import java.awt.*;

public abstract class DijagramElement extends ClassyNode {
    private Color color;
    private int stroke;

    public DijagramElement(String name, ClassyNode parent, Color color, int stroke) {
        super(name, parent);
        this.color = color;
        this.stroke = stroke;
    }
    public DijagramElement(String name, ClassyNode parent) {
        super(name, parent);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getStroke() {
        return stroke;
    }

    public void setStroke(int stroke) {
        this.stroke = stroke;
    }
}
