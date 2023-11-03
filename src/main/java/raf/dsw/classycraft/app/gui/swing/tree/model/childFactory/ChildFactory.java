package raf.dsw.classycraft.app.gui.swing.tree.model.childFactory;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Package;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Project;

public class ChildFactory {

    public ChildFactory() {
    }

    public ClassyNodeComposite createChild(String name, ClassyNode parent, ChildType childType){

        if(childType == ChildType.PROJECT){
            return new Project(name, parent);
        }
        else if(childType == ChildType.PACKAGE){
            return new Package(name, parent);
        }
        return null;
    }

}
