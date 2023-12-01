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

    public Interfejs(String name, ClassyNode parent, Color color, int stroke, String naziv, Vidljivost vidljivost, int size, Point position) {
        super(name, parent, color, stroke, naziv, vidljivost, size, position);
    }

    public List<Metode> getMetodeList() {
        return metodeList;
    }

    public void setMetodeList(List<Metode> metodeList) {
        this.metodeList = metodeList;
    }
}
