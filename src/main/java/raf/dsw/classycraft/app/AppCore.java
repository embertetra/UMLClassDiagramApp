package raf.dsw.classycraft.app;

import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.core.ClassyRepository;
import raf.dsw.classycraft.app.classyCraftRepository.ClassyRepositoryImplementation;

public class AppCore {
    public static void main(String[] args) {
        ApplicationFramework appCore = ApplicationFramework.getInstance();
        ClassyRepository classyRepository = new ClassyRepositoryImplementation();
        appCore.initialize(classyRepository);
    }
}