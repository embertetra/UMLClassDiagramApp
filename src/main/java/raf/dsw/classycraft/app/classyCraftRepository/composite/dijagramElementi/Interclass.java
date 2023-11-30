package raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Vidljivost;

import java.awt.*;
import java.util.List;

public abstract class Interclass extends DijagramElement {
    private String naziv;
    private Vidljivost vidljivost;
    private int size;
    private int position;
    private Point location;

    public Interclass(String name, ClassyNode parent) {
        super(name, parent);
    }

    public Interclass(String name, ClassyNode parent, Color color, int stroke, String naziv, Vidljivost vidljivost, int size, int position, Point location) {
        super(name, parent, color, stroke);
        this.naziv = naziv;
        this.vidljivost = vidljivost;
        this.size = size;
        this.position = position;
        this.location = location;
    }
}
