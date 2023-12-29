package raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Vidljivost;
import raf.dsw.classycraft.app.observer.IPublisher;

import java.awt.*;

public abstract class Interclass extends DijagramElement implements IPublisher {

    private String naziv;

    private Vidljivost vidljivost;

    private int size;

    private Point position;

    public Interclass(String name, ClassyNode parent) {
        super(name, parent);
    }

    public Interclass(String name, ClassyNode parent, Color color, int stroke, String naziv, Vidljivost vidljivost, int size, Point position) {
        super(name, parent, color, stroke);
        this.naziv = naziv;
        this.vidljivost = vidljivost;
        this.size = size;
        this.position = position;
    }
    public Interclass(String name, ClassyNode parent, int stroke, String naziv, Vidljivost vidljivost, Point position) {
        super(name, parent, stroke);
        this.naziv = naziv;
        this.vidljivost = vidljivost;
        this.position = position;
    }

    public boolean poredjenje(Interclass i){
        if(position.x == i.getPosition().x && position.y == i.getPosition().y)
            return true;
        else return false;
    }

    public String getNaziv() {
        return naziv;
    }

    public Vidljivost getVidljivost() {
        return vidljivost;
    }

    public void setVidljivost(Vidljivost vidljivost) {
        this.vidljivost = vidljivost;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
        notifySubscribers("state");
    }
}
