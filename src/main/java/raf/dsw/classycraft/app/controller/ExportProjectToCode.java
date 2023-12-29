package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Package;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Project;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.MessageType;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class ExportProjectToCode extends AbstractClassyAction{
    public ExportProjectToCode() {
        putValue(SMALL_ICON, loadIcon("/images/exportProjectToCode.png"));
        putValue(NAME, "Export as code");
        putValue(SHORT_DESCRIPTION, "Export as code");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        ClassyTreeItem selectedTree = MainFrame.getInstance().getClassyTree().getSelectedNode();
        if(selectedTree == null || !(selectedTree.getClassyNode() instanceof Project)){
            if(selectedTree != null)
                ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Samo celokupan projekat moze da se exportuje kao kod", MessageType.ERROR);
            return;
        }
        ClassyNode selectedNode = selectedTree.getClassyNode();

        File file = null;
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Odaberite folder u kom zelite sa exportujete projekat");
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if(jfc.showSaveDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION)
            file = jfc.getCurrentDirectory();

        if(file == null)
            return;

        file = new File(file.getPath() + "/" + selectedNode.getName());

        if(file.mkdir()) {

            kreirajDir(file, (ClassyNodeComposite) selectedNode);

        }
        else {
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Folder pod nazivom vaseg projekta vec postoji na zeljenoj lokaciji", MessageType.ERROR);
            return;
        }
    }
    public void kreirajDir(File file, ClassyNodeComposite node){

        for(ClassyNode c : node.getChildren()){
            if(c instanceof Package){
                File f = new File(file.getPath() + "/" + c.getName());
                f.mkdir();
                kreirajDir(f, (ClassyNodeComposite) c);
            }
            else if(c instanceof Dijagram)
        }

    }
}
