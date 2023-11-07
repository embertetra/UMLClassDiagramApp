package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Project;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.MessageType;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.jTabbedElements.NotificationJTabbed;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AutorAction extends AbstractClassyAction{

    public AutorAction(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(null));
        putValue(SMALL_ICON, loadIcon("/images/autor.png"));
        putValue(NAME, "Add autor");
        putValue(SHORT_DESCRIPTION, "Add autor");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        ClassyTreeItem selected = (ClassyTreeItem) MainFrame.getInstance().getClassyTree().getSelectedNode();
        if(selected != null && selected.getClassyNode() instanceof Project){
            String input = JOptionPane.showInputDialog("Uneti ime autora:","");
            ((Project) selected.getClassyNode()).setNewAutor(new NotificationJTabbed((ClassyNodeComposite) selected.getClassyNode(), input, 6));
        }
        else
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Potrebno je selektovati projekat pri dodeli autora!", MessageType.ERROR);
    }
}
