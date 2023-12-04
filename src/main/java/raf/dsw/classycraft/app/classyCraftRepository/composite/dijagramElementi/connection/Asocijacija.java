package raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Connection;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;

public class Asocijacija extends Connection {
    public Asocijacija(String name, ClassyNode parent) {
        super(name, parent);
    }

    public Asocijacija(String name, ClassyNode parent, int stroke, Interclass from, Interclass to) {
        super(name, parent, stroke, from, to);
    }
}
