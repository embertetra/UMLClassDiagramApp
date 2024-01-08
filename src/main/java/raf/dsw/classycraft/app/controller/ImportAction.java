package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Project;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.ProjectExplorer;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class ImportAction extends AbstractClassyAction{

    public ImportAction() {
        //putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/images/import.png"));
        putValue(NAME, "Import project");
        putValue(SHORT_DESCRIPTION, "Import project");
    }
    @Override
    public void actionPerformed(ActionEvent arg0) {
        JFileChooser jfc = new JFileChooser();

        if (jfc.showOpenDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
            try {
                File file = jfc.getSelectedFile();
                Project p = ApplicationFramework.getInstance().getSerializer().loadProject(file);

                int index = 1;
                int flag = 1;
                ProjectExplorer root = ApplicationFramework.getInstance().getClassyRepository().getRoot();

                String base = p.getName();
                while(flag == 1) {
                    flag = 0;
                    for (ClassyNode c : root.getChildren()) {
                        if (c.getName().equals(p.getName())) {
                            String s = "(" + index + ")";
                            p.setName(base + s);
                            index++;
                            flag = 1;
                            break;
                        }
                    }
                }

                MainFrame.getInstance().getClassyTree().loadProject(p);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
