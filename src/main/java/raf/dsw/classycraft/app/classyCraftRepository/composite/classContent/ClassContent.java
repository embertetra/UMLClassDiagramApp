package raf.dsw.classycraft.app.classyCraftRepository.composite.classContent;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Vidljivost;
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Atributi.class, name = "atribut"),
        @JsonSubTypes.Type(value = EnumElement.class, name = "enumElement"),
        @JsonSubTypes.Type(value = Metode.class, name = "metoda"),

})
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

    public void setVidljivost(String s) {
        if(s.equals("+"))
            vidljivost = Vidljivost.PUBLIC;
        else if(s.equals("-"))
            vidljivost = Vidljivost.PRIVATE;
        else vidljivost = Vidljivost.PROTECTED;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}
