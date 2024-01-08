package raf.dsw.classycraft.app.gui.swing.tree.model.childFactory;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.DijagramElement;

public class ConnectionFactory extends ChildFactory{
    @Override
    public ClassyNode makeChild(String name, ClassyNode parent, DijagramElement dijagramElement) {
        return dijagramElement;
    }
}
