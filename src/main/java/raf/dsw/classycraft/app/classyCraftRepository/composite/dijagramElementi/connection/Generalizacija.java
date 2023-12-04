package raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Connection;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;

import java.awt.*;

public class Generalizacija extends Connection {

    public Generalizacija(String name, ClassyNode parent) {
        super(name, parent);
    }

    public Generalizacija(String name, ClassyNode parent, int stroke, Interclass from, Interclass to) {
        super(name, parent, stroke, from, to);
    }
}
