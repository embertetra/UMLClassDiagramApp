package raf.dsw.classycraft.app.classyCraftRepository.implementation;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNodeComposite;

public class Package extends ClassyNodeComposite {
    public Package(String name, ClassyNode parent) {
        super(name, parent);
    }

    public void test(){
        for(ClassyNode c : getChildren()){
            System.out.println(c.getName());
        }
    }

    @Override
    public void addChild(ClassyNode child) {
        if(child != null && child instanceof Dijagram){
            Dijagram dijagram = (Dijagram) child;
            if(!this.getChildren().contains(dijagram)) {
                this.getChildren().add(dijagram);
            }
        }
        else if(child != null && child instanceof Package){
            Package p = (Package) child;
            if(!this.getChildren().contains(p)) {
                this.getChildren().add(p);
            }
        }
    }

    @Override
    public void removeChild(ClassyNode child) {
        if(child != null && child instanceof Package){
            for(ClassyNode c: this.getChildren())
                if(c.equals(child))
                    this.getChildren().remove(c);
        }
    }
}
