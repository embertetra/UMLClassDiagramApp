package raf.dsw.classycraft.app.controller;

import com.sun.tools.javac.Main;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Package;
import raf.dsw.classycraft.app.gui.swing.tree.ClassyTreeImplementation;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.tree.view.ClassyTreeView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseListener {

    ClassyTreeImplementation classyTreeImplementation = (ClassyTreeImplementation) MainFrame.getInstance().getClassyTree();
    ClassyTreeView treeView;

    public MouseListener() {
        if (classyTreeImplementation != null) {
            treeView = classyTreeImplementation.getTreeView();
            treeView.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    ClassyTreeItem selected = (ClassyTreeItem) MainFrame.getInstance().getClassyTree().getSelectedNode();
                    if ((selected.getClassyNode() instanceof Package)){
                        if (e.getClickCount() == 2) {
                            ///NAPRAVI ZA JTABBED
                        }
                    }
                }
            });
        } else System.out.println("test nije prosao");
    }
}
