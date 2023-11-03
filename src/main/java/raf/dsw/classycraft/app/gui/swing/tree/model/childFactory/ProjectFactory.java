package raf.dsw.classycraft.app.gui.swing.tree.model.childFactory;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Project;

import javax.swing.tree.TreeNode;

public class ProjectFactory extends ChildFactory {

    public ProjectFactory() {

    }

    @Override
    public ClassyNode makeChild(String name, ClassyNode parent) {

        return new Project(name, parent);

    }
}
