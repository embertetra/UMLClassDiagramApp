package raf.dsw.classycraft.app.classyCraftRepository.composite.classContent;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Vidljivost;

public class Metode extends ClassContent {
    public Metode(Vidljivost vidljivost, String tip, String naziv) {
        super(vidljivost, tip, naziv);
    }

    @Override
    public String toString() {
        String s = null;
        if(vidljivost == Vidljivost.PRIVATE)
            s =  "-" + naziv + "(): " + tip;
        else if(vidljivost == Vidljivost.PUBLIC)
            s =  "+" + naziv + "(): " + tip;
        else if(vidljivost == Vidljivost.PROTECTED)
            s =  "#" + naziv + "(): " + tip;
        return s;
    }
}
