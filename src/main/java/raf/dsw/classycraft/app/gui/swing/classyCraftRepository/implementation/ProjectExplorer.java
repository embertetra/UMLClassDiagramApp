package raf.dsw.classycraft.app.gui.swing.classyCraftRepository.implementation;

import raf.dsw.classycraft.app.gui.swing.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.gui.swing.classyCraftRepository.composite.ClassyNodeComposite;

public class ProjectExplorer extends ClassyNodeComposite {
    // root klasa = postoji samo jedna instanca

    public ProjectExplorer(String name){
        super(name, null);
    }

    @Override
    public void addChild(ClassyNode child) {
        if(child != null && child instanceof Project){
            Project project = (Project) child;
            if(!this.getChildren().contains(project)) {
                this.getChildren().add(project);
            }
        }
    }

    @Override
    public void removeChild(ClassyNode child) {
        // poziva se message generator za ispis greske
    }
}
