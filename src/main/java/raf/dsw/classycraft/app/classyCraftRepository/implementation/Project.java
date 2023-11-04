package raf.dsw.classycraft.app.classyCraftRepository.implementation;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.observer.IPublisher;
import raf.dsw.classycraft.app.observer.ISubscriber;

import java.util.ArrayList;
import java.util.List;

public class Project extends ClassyNodeComposite {


    public Project(String name, ClassyNode parent) {
        super(name, parent);
    }

    @Override
    public void addChild(ClassyNode child) {
        if (child != null && child instanceof Package) {
            Package pck = (Package) child;
            if (!this.getChildren().contains(pck)) {
                this.getChildren().add(pck);
            }
        }
    }

    @Override
    public void removeChild(ClassyNode child) {
        if (child != null && child instanceof Package) {
            if (getChildren().contains(child))
                getChildren().remove(child);
        }
    }


}
