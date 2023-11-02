package raf.dsw.classycraft.app.classyCraftRepository.composite;

import java.util.ArrayList;
import java.util.List;

public abstract class ClassyNodeComposite extends ClassyNode{

    List<ClassyNode> children;

    public ClassyNodeComposite(String name, ClassyNode parent){
        super(name, parent);
        this.children = new ArrayList<>();
    }

    public ClassyNodeComposite(String name, ClassyNode parent, List<ClassyNode> children) {
        super(name, parent);
        this.children = children;
    }

    public abstract void addChild(ClassyNode child);

    public abstract void removeChild(ClassyNode child);

    public ClassyNode getChildByName(String name){
        for(ClassyNode child : this.getChildren()){
            if(name.equals(child.getName()))
                return child;
        }
        return null;
    }

    public List<ClassyNode> getChildren() {
        return children;
    }

    public void setChildren(List<ClassyNode> children) {
        this.children = children;
    }
}
