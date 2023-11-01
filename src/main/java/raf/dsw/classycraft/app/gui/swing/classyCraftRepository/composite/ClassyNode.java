package raf.dsw.classycraft.app.gui.swing.classyCraftRepository.composite;

public abstract class ClassyNode {

    private String name;

    private ClassyNode parent;

    public ClassyNode(String name, ClassyNode parent) {
        this.name = name;
        this.parent = parent;
    }

    //

    public String getName() {
        return name;
    }

    public ClassyNode getParent() {
        return parent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParent(ClassyNode parent) {
        this.parent = parent;
    }
}
