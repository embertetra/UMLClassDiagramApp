package raf.dsw.classycraft.app.classyCraftRepository.composite;

import com.fasterxml.jackson.annotation.JsonSetter;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Project;

import java.util.ArrayList;
import java.util.List;
public abstract class ClassyNodeComposite extends ClassyNode{

    List<ClassyNode> children;

    public ClassyNodeComposite(String name, ClassyNode parent){
        super(name, parent);
        this.children = new ArrayList<>();
        projectChanged();
    }

    public ClassyNodeComposite(String name, ClassyNode parent, List<ClassyNode> children) {
        super(name, parent);
        this.children = children;
        projectChanged();
    }

    public abstract void addChild(ClassyNode child);

    public abstract void removeChild(ClassyNode child);

    public List<ClassyNode> getChildren() {
        return children;
    }

    @JsonSetter
    public void setChildren(List<ClassyNode> children) {
        projectChanged();
        this.children = children;
    }

    private void projectChanged(){
        ClassyNode c = this;
        while(c!=null && !(c instanceof Project)){
            c = c.getParent();
        }
        if(c != null)
            ((Project) c).setChanged(true);
    }

}
