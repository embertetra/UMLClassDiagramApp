package raf.dsw.classycraft.app.gui.swing.tree;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Package;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Project;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.ProjectExplorer;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.MessageType;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.tree.model.childFactory.ChildFactory;
import raf.dsw.classycraft.app.gui.swing.tree.model.childFactory.FactoryType;
import raf.dsw.classycraft.app.gui.swing.tree.model.childFactory.FactoryUtils;
import raf.dsw.classycraft.app.gui.swing.tree.view.ClassyTreeView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;

public class ClassyTreeImplementation implements ClassyTree{

    private ClassyTreeView treeView;

    private DefaultTreeModel treeModel;

    private ChildFactory childFactory;

    @Override
    public ClassyTreeView generateTree(ProjectExplorer projectExplorer) {
        ClassyTreeItem root = new ClassyTreeItem(projectExplorer);
        treeModel = new DefaultTreeModel(root);
        treeView = new ClassyTreeView(treeModel);
        return treeView;
    }

    @Override
    public void addChild(ClassyTreeItem parent) {
        if(!(parent.getClassyNode() instanceof ClassyNodeComposite)) {
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Ne moze da se napravi dete Dijagramu!", MessageType.ERROR);
            return;
        }

        ClassyNode child = createChild(parent.getClassyNode());
        if(child != null) {
            parent.add(new ClassyTreeItem(child)); //prikazuje se u JTree-u
            ((ClassyNodeComposite) parent.getClassyNode()).addChild(child); // dodaje se u modelu addChild()

            treeView.expandPath(treeView.getSelectionPath());
            SwingUtilities.updateComponentTreeUI(treeView);
        }
    }

    @Override
    public ClassyTreeItem getSelectedNode() {
        return (ClassyTreeItem) treeView.getLastSelectedPathComponent();
    }


    private ClassyNode createChild(ClassyNode parent){

        FactoryUtils factoryUtils = new FactoryUtils();

        if(parent instanceof ProjectExplorer){
            childFactory = factoryUtils.getChildFactory(FactoryType.PROJECT);
            return childFactory.orderChild("projekat", parent);
        }
        else if(parent instanceof Project){
            childFactory = factoryUtils.getChildFactory(FactoryType.PACKAGE);
            return childFactory.orderChild("paket", parent);
        }
        else if(parent instanceof Package){

            Object[] options = {"Dijagram", "Podpaket"};

            int n = JOptionPane.showOptionDialog(MainFrame.getInstance(),
                    "Da li zelite da kreirate dijagram ili podpaket?", "Kreiranje", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null,     //default ikonica
                    options,  //opcije
                    options[0]); //selektovan po defaultu

            if(n == 1){
                childFactory = factoryUtils.getChildFactory(FactoryType.PACKAGE);
                return  childFactory.orderChild("podpaket", parent);
            }
            else if(n == 0){
                childFactory = factoryUtils.getChildFactory(FactoryType.DIAGRAM);
                return  childFactory.orderChild("dijagram", parent);
            }
            else if(n == -1){

            }
        }
        return null;
    }



}
