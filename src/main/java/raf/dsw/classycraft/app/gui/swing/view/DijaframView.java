package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;

import javax.swing.*;

public class DijaframView extends JPanel {

    ClassyNode classyNode;

    public DijaframView(ClassyNode classyNode) {

        this.classyNode = classyNode;

    }

    public ClassyNode getClassyNode() {
        return classyNode;
    }

    public void setClassyNode(ClassyNode classyNode) {
        this.classyNode = classyNode;
    }
}
