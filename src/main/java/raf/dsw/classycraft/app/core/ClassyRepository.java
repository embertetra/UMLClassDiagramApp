package raf.dsw.classycraft.app.core;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.ProjectExplorer;

public interface ClassyRepository {
    ProjectExplorer getRoot();

    void addChild(ClassyNodeComposite parent, ClassyNode child);

}
