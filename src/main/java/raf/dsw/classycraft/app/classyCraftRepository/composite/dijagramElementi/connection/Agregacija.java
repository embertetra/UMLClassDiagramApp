package raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection;

import javafx.util.Pair;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Connection;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;

import java.awt.*;

public class Agregacija extends Connection {
    public Agregacija(String name, ClassyNode parent) {
        super(name, parent);
    }

    public Agregacija(String name, ClassyNode parent, int stroke, Interclass from, Interclass to) {
        super(name, parent, stroke, from, to);
    }
}
