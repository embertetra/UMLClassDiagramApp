package raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Vidljivost;

import java.awt.*;
import java.util.List;

public abstract class Interclass extends DijagramElement {
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

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Vidljivost getVidljivost() {
        return vidljivost;
    }

    public void setVidljivost(Vidljivost vidljivost) {
        this.vidljivost = vidljivost;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
