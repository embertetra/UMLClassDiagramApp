package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.classyCraftRepository.implementation.Project;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.MessageType;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

public class SaveAction extends AbstractClassyAction{

    public SaveAction() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.SHIFT_MASK));
        putValue(SMALL_ICON, loadIcon("/images/save.png"));
        putValue(NAME, "Save");
        putValue(SHORT_DESCRIPTION, "Save project");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser jfc = new JFileChooser();

        if(MainFrame.getInstance().getClassyTree().getSelectedNode() == null || !((MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode()) instanceof Project)){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Morate selektovati projekat", MessageType.ERROR);
            return;
        }
        Project project = (Project) MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode();
        File projectFile = null;

        if(!project.isChanged()){
            System.out.println("project.isChanged = false");
            return;
        }

        if(project.getFilePath() == null || project.getFilePath().isEmpty()){
            if(jfc.showSaveDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION){
                projectFile = jfc.getSelectedFile();

                project.setFilePath(projectFile.getPath());
            }
        }
        if(projectFile == null || project.getFilePath()==null || project.getFilePath().isEmpty())
            return;
        ApplicationFramework.getInstance().getSerializer().saveProject(project);
        System.out.println("Project saved");
        project.setChanged(false);
    }
}
