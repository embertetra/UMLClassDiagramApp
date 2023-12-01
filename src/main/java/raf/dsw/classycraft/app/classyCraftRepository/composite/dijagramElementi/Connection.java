package raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.DijagramElement;

import java.awt.*;

public abstract class Connection extends DijagramElement {

    private Interclass from;
    private Interclass to;

    public Connection(String name, ClassyNode parent) {
        super(name, parent);
    }
    public Connection(String name, ClassyNode parent, Color color, int stroke, Interclass from, Interclass to) {
        super(name, parent, color, stroke);
        this.from = from;
        this.to = to;
    }

    public Interclass getFrom() {
        return from;
    }

    public void setFrom(Interclass from) {
        this.from = from;
    }

    public Interclass getTo() {
        return to;
    }

    public void setTo(Interclass to) {
        this.to = to;
    }
}
