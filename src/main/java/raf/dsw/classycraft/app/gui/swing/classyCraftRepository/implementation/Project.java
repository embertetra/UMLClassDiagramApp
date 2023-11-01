package raf.dsw.classycraft.app.gui.swing.classyCraftRepository.implementation;

import raf.dsw.classycraft.app.gui.swing.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.gui.swing.classyCraftRepository.composite.ClassyNodeComposite;

import java.util.Random;

public class Project extends ClassyNodeComposite {


    public Project(String name, ClassyNode parent) {
        super(name, parent);
    }

    @Override
    public void addChild(ClassyNode child) {
        if(child != null && child instanceof Package){
            Package pck = (Package) child;
            if(!this.getChildren().contains(pck)) {
                this.getChildren().add(pck);
            }
        }
    }

    @Override
    public void removeChild(ClassyNode child) {
        if(child != null && child instanceof Project){
            for(ClassyNode c: this.getChildren())
                if(c.equals(child))
                    this.getChildren().remove(c);
        }
    }

}
