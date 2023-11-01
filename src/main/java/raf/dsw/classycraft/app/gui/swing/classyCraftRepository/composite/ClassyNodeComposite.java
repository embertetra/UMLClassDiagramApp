package raf.dsw.classycraft.app.gui.swing.classyCraftRepository.composite;

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

    public abstract void removeChildren(ClassyNode child);
}
