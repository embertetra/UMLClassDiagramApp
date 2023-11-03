package raf.dsw.classycraft.app.gui.swing.tree.model.childFactory;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;

public abstract class ChildFactory {

    public ClassyNode orderChild(String name, ClassyNode parent){
        return makeChild(name,parent);
    }

    public abstract ClassyNode makeChild(String name, ClassyNode parent);

}
