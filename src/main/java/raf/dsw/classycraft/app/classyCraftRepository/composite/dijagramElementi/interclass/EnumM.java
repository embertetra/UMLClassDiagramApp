package raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EnumM extends Interclass {

    private String naziv;
    private List<String> listEnuma;

    public EnumM(String name, ClassyNode parent) {
        super(name, parent);
    }

    public EnumM(String name, ClassyNode parent, Color color, int stroke, String naziv, Vidljivost vidljivost, int size, Point position) {
        super(name, parent, color, stroke, naziv, vidljivost, size, position);
    }
    public EnumM(String name, ClassyNode parent, int stroke, String naziv, Vidljivost vidljivost, Point position) {
        super(name, parent, stroke, naziv, vidljivost, position);
        this.naziv = "    ";
        listEnuma = new ArrayList<>();
    }

    public List<String> getListEnuma() {
        return listEnuma;
    }

    public void setListEnuma(List<String> listEnuma) {
        this.listEnuma = listEnuma;
    }

    @Override
    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}
