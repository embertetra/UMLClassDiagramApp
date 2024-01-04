package raf.dsw.classycraft.app.classyCraftRepository.composite.classContent;

import com.fasterxml.jackson.annotation.JsonTypeName;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Vidljivost;

@JsonTypeName("enumElement")
public class EnumElement extends ClassContent {
    public EnumElement(){
        super(null, "", "");
    }
    public EnumElement(Vidljivost vidljivost, String tip, String naziv) {
        super(vidljivost, tip, naziv);
    }


}
