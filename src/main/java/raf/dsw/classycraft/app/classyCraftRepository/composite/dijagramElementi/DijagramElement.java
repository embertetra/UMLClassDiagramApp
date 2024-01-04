package raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Project;

import java.awt.*;

public abstract class DijagramElement extends ClassyNode {
    private Color color;
    private int stroke;

    public DijagramElement(String name, ClassyNode parent, Color color, int stroke) {
        super(name, parent);
        this.color = color;
        this.stroke = stroke;
        projectChanged();
    }
    public DijagramElement(String name, ClassyNode parent, int stroke) {
        super(name, parent);
        this.stroke = stroke;
        projectChanged();
    }
    public DijagramElement(String name, ClassyNode parent) {
        super(name, parent);
        projectChanged();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        projectChanged();
        this.color = color;
    }

    public int getStroke() {
        return stroke;
    }

    public void setStroke(int stroke) {
        this.stroke = stroke;
        projectChanged();
    }
    private void projectChanged(){
        ClassyNode c = this;
        while(c!=null && !(c instanceof Project)){
            c = c.getParent();
        }
        if(c != null)
            ((Project) c).setChanged(true);
    }

}
