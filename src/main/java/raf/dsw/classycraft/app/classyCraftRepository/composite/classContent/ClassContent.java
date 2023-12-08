package raf.dsw.classycraft.app.classyCraftRepository.composite.classContent;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Vidljivost;

public class ClassContent {

    protected Vidljivost vidljivost;
    protected String tip;
    protected String naziv;

    public ClassContent(Vidljivost vidljivost, String tip, String naziv) {
        this.vidljivost = vidljivost;
        this.tip = tip;
        this.naziv = naziv;
    }

    public String getVidljivost() {

        if(vidljivost == Vidljivost.PUBLIC)
            return "+";
        else if(vidljivost == Vidljivost.PRIVATE)
            return "-";
        else if(vidljivost == Vidljivost.PROTECTED)
            return "#";
        else return "~";
    }

    public String getTip() {
        return tip;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setVidljivost(Vidljivost vidljivost) {
        this.vidljivost = vidljivost;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}
