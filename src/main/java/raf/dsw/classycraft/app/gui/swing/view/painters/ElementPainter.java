package raf.dsw.classycraft.app.gui.swing.view.painters;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.DijagramElement;

import java.awt.*;

public abstract class ElementPainter {

    protected DijagramElement element;

    //iz njega cemo da izvucemo recept
    public ElementPainter(DijagramElement element){
        this.element = element;
    }

    public abstract void draw(Graphics2D g);

    public abstract boolean elementAt(DijagramElement dijagramElement, Point point);

    public DijagramElement getElement() {
        return element;
    }
}
