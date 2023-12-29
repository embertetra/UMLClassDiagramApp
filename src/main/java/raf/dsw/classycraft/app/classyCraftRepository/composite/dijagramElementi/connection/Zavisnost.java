package raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection;

import com.fasterxml.jackson.annotation.JsonTypeName;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Connection;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;

import java.awt.*;
@JsonTypeName("zavisnost")
public class Zavisnost extends Connection {
    private String callOrInstantiate;

    public Zavisnost(){
        super("", null);
    }
    public Zavisnost(String name, ClassyNode parent) {
        super(name, parent);
    }

    public Zavisnost(String name, ClassyNode parent, int stroke, Interclass from, Interclass to) {
        super(name, parent, stroke, from, to);
        callOrInstantiate = "-";
    }

    public String getCallOrInstantiate() {
        return callOrInstantiate;
    }

    public void setCallOrInstantiate(String callOrInstantiate) {
        this.callOrInstantiate = callOrInstantiate;
    }
}
