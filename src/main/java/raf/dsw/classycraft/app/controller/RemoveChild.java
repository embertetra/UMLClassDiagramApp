package raf.dsw.classycraft.app.controller;

import com.sun.tools.javac.Main;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.ProjectExplorer;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.MessageType;
import raf.dsw.classycraft.app.gui.swing.tree.ClassyTreeImplementation;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.tree.view.ClassyTreeView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class RemoveChild extends AbstractClassyAction{

    public RemoveChild() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/images/bin.png"));
        putValue(NAME, "Remove child");
        putValue(SHORT_DESCRIPTION, "Remove child");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ClassyTreeItem selected = (ClassyTreeItem) MainFrame.getInstance().getClassyTree().getSelectedNode();
        if(selected != null) {
            if(selected.getClassyNode() instanceof ProjectExplorer)
                ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("ProjectExplorer nije moguce obrisati!", MessageType.ERROR);
            else {
                MainFrame.getInstance().getClassyTree().getSelectedNode().removeFromParent();
                ClassyNodeComposite cns = (ClassyNodeComposite) selected.getClassyNode().getParent();
                if(cns.getChildren().contains(selected.getClassyNode()))
                    cns.removeChild(selected.getClassyNode());
                ClassyTreeImplementation classyTreeImplementation = (ClassyTreeImplementation) MainFrame.getInstance().getClassyTree();
                SwingUtilities.updateComponentTreeUI(classyTreeImplementation.getTreeView());
            }
            ClassyTreeImplementation cti = (ClassyTreeImplementation) MainFrame.getInstance().getClassyTree();
            cti.getTreeView().clearSelection();
        }
        else
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Nije selektovano polje za brisanje", MessageType.ERROR);
    }
}
