package raf.dsw.classycraft.app.classyCraftRepository.composite.classContent;
import com.fasterxml.jackson.annotation.JsonTypeName;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Vidljivost;

@JsonTypeName("atribut")
public class Atributi extends ClassContent {

    public Atributi(){
        super(null, "", "");
    }
    public Atributi(Vidljivost vidljivost, String tip, String naziv) {
        super(vidljivost, tip, naziv);
    }

    @Override
    public String toString() {
        String s = null;
        if(vidljivost == Vidljivost.PRIVATE)
            s = "-" + naziv + ": " + tip;
        else if(vidljivost == Vidljivost.PUBLIC)
            s = "+" + naziv + ": " + tip;
        else if(vidljivost == Vidljivost.PROTECTED)
            s = "#" + naziv + ": " + tip;
        return s;
    }


}
