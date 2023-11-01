package raf.dsw.classycraft.app.core;

import raf.dsw.classycraft.app.gui.swing.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.gui.swing.classyCraftRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.gui.swing.classyCraftRepository.implementation.ProjectExplorer;

public interface ClassyRepository {
    ProjectExplorer getRoot();

    void addChild(ClassyNodeComposite parent, ClassyNode child);

}
