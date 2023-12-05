package raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass;

import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.ClassContent;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Klasa extends Interclass {

    private String naziv;

    private List<ClassContent> classContentList;

    public Klasa(String name, ClassyNode parent) {
        super(name, parent);
    }

    public Klasa(String name, ClassyNode parent, int stroke, String naziv, Vidljivost vidljivost, Point position) {
        super(name, parent, stroke, naziv, vidljivost, position);
        this.naziv = "    ";
        classContentList = new ArrayList<>();
    }

    public List<ClassContent> getClassContentList() {
        return classContentList;
    }

    public void setClassContentList(List<ClassContent> classContentList) {
        this.classContentList = classContentList;
    }

    @Override
    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}
