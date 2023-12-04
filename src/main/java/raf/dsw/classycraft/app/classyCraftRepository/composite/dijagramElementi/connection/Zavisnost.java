package raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Connection;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;

import java.awt.*;

public class Zavisnost extends Connection {

    public Zavisnost(String name, ClassyNode parent) {
        super(name, parent);
    }

    public Zavisnost(String name, ClassyNode parent, int stroke, Interclass from, Interclass to) {
        super(name, parent, stroke, from, to);
    }
}
