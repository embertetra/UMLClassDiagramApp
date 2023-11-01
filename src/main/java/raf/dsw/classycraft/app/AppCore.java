package raf.dsw.classycraft.app;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.core.ClassyRepository;
import raf.dsw.classycraft.app.gui.swing.classyCraftRepository.ClassyRepositoryImplementation;
import raf.dsw.classycraft.app.gui.swing.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.gui.swing.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.gui.swing.classyCraftRepository.implementation.Package;
import raf.dsw.classycraft.app.gui.swing.classyCraftRepository.implementation.Project;
import raf.dsw.classycraft.app.gui.swing.classyCraftRepository.implementation.ProjectExplorer;

public class AppCore {
    public static void main(String[] args) {
        ApplicationFramework appCore = ApplicationFramework.getInstance();
        ClassyRepository classyRepository = new ClassyRepositoryImplementation();
        appCore.initialize(classyRepository);
    }
}