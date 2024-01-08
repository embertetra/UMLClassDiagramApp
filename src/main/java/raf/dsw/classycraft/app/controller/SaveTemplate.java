package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.MessageType;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SaveTemplate extends AbstractClassyAction{
    public SaveTemplate() {
        //putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift S"));
        putValue(SMALL_ICON, loadIcon("/images/save_template.png"));
        putValue(NAME, "Save template");
        putValue(SHORT_DESCRIPTION, "Save template");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(MainFrame.getInstance().getClassyTree().getSelectedNode() == null || !((MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode()) instanceof Dijagram)){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Morate selektovati dijagram", MessageType.ERROR);
            return;
        }

        Dijagram d = (Dijagram) MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode();

        if(d != null && d.getChildren() != null && !d.getChildren().isEmpty()) {
            String input = JOptionPane.showInputDialog("Uneti ime sablona:", "");
            if(input == null || input.isEmpty())
                return;
            ApplicationFramework.getInstance().getSerializer().saveTemplate(d, input);
            MainFrame.getInstance().getGalleryTemplates().refreshFoldredTemplate();
        }
        else if(d != null && d.getChildren() != null && d.getChildren().isEmpty()) {
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Ne mozete sacuvati prazan dijagram", MessageType.ERROR);
        }
    }
}
