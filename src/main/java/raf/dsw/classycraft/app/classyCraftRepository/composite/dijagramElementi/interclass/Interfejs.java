package raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass;

import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.ClassContent;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.Metode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;

import java.awt.*;
import java.util.List;

public class Interfejs extends Interclass {

    private List<Metode> metodeList;

    public Interfejs(String name, ClassyNode parent) {
        super(name, parent);
    }

    public Interfejs(String name, ClassyNode parent, Color color, int stroke, String naziv, Vidljivost vidljivost, int size, int position, Point location) {
        super(name, parent, color, stroke, naziv, vidljivost, size, position, location);
    }
}
