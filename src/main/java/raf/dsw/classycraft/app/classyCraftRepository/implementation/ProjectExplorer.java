package raf.dsw.classycraft.app.classyCraftRepository.implementation;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.MessageType;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

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
        if(child != null && child instanceof Project){
            if(getChildren().contains(child))
                getChildren().remove(child);
        }
    }
}
