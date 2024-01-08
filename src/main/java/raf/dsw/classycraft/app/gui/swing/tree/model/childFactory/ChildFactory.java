package raf.dsw.classycraft.app.gui.swing.tree.model.childFactory;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.DijagramElement;

public abstract class ChildFactory {

    public ClassyNode orderChild(String name, ClassyNode parent, DijagramElement dijagramElement){
        return makeChild(name,parent, dijagramElement);
    }

    public abstract ClassyNode makeChild(String name, ClassyNode parent, DijagramElement dijagramElement);

}
