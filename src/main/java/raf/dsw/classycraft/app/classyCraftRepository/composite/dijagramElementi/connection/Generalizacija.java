package raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection;

import com.fasterxml.jackson.annotation.JsonTypeName;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Connection;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;

import java.awt.*;
@JsonTypeName("generalizacija")
public class Generalizacija extends Connection {

    public Generalizacija(){
        super("", null);
    }
    public Generalizacija(String name, ClassyNode parent) {
        super(name, parent);
    }

    public Generalizacija(String name, ClassyNode parent, int stroke, Interclass from, Interclass to) {
        super(name, parent, stroke, from, to);
    }
}
