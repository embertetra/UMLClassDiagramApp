package raf.dsw.classycraft.app.gui.swing.tree.model;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import javax.swing.tree.DefaultMutableTreeNode;

public class ClassyTreeItem extends DefaultMutableTreeNode{

    private ClassyNode classyNode;

    public ClassyTreeItem(ClassyNode classyNode){
        this.classyNode = classyNode;
    }

    //toString - kako bi se prikazao u app
    @Override
    public String toString() {
        return classyNode.getName();
    }

    // kako bi TreeSellEditor mogao da promeni ime
    public void setName(String name){
        this.classyNode.setName(name);
    }

    public ClassyNode getClassyNode() {
        return classyNode;
    }

    public void setClassyNode(ClassyNode classyNode) {
        this.classyNode = classyNode;
    }
}
