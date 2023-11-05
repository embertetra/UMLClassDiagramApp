package raf.dsw.classycraft.app.gui.swing.tree.model.childFactory;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;

import javax.swing.tree.TreeNode;

public class DiagramFactory extends ChildFactory {


    public DiagramFactory() {

    }

    @Override
    public ClassyNode makeChild(String name, ClassyNode parent) {
        return new Dijagram(name, parent);
    }
}
