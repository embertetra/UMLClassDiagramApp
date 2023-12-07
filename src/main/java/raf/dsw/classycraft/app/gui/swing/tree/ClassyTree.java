package raf.dsw.classycraft.app.gui.swing.tree;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.DijagramElement;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.ProjectExplorer;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.tree.view.ClassyTreeView;

public interface ClassyTree {

    //korenski cvor
    ClassyTreeView generateTree(ProjectExplorer projectExplorer);

    //dodaje i u modelu i u tree-u
    void addChild(ClassyTreeItem parent, DijagramElement dijagramElement);

    ClassyTreeItem getSelectedNode();

}
