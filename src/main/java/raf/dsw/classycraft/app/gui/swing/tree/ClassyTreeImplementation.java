package raf.dsw.classycraft.app.gui.swing.tree;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
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
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Dijagram ne moze imati podklasu!", MessageType.ERROR);
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

        FactoryUtils factoryUtils = ApplicationFramework.getInstance().getFactoryUtils();

        if(parent instanceof ProjectExplorer){
            childFactory = factoryUtils.getChildFactory(FactoryType.PROJECT);

            int cnt = 1;
            Project project = new Project("Project" + String.valueOf(cnt), parent);
            while(((ProjectExplorer) parent).getChildren().contains(project)){
                cnt++;
                project = new Project("Project" + String.valueOf(cnt), parent);
            }

            return childFactory.orderChild(project.getName(), parent);
        }
        else if(parent instanceof Project){
            childFactory = factoryUtils.getChildFactory(FactoryType.PACKAGE);

            int cnt = 1;
            Package pck = new Package("Package" + String.valueOf(cnt), parent);
            while(((Project) parent).getChildren().contains(pck)){
                cnt++;
                pck = new Package("Package" + String.valueOf(cnt), parent);
            }

            return childFactory.orderChild(pck.getName(), parent);
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

                int cnt = 1;
                Package pck = new Package("SubPackage" + String.valueOf(cnt), parent);
                while(((Package) parent).getChildren().contains(pck)){
                    cnt++;
                    pck = new Package("SubPackage" + String.valueOf(cnt), parent);
                }

                return  childFactory.orderChild(pck.getName(), parent);
            }
            else if(n == 0){
                childFactory = factoryUtils.getChildFactory(FactoryType.DIAGRAM);

                int cnt = 1;
                Dijagram dijagram = new Dijagram("Dijagram" + String.valueOf(cnt), parent);
                while(((Package) parent).getChildren().contains(dijagram)){
                    cnt++;
                    dijagram = new Dijagram("Dijagram" + String.valueOf(cnt), parent);
                }

                return  childFactory.orderChild(dijagram.getName(), parent);
            }
        }
        return null;
    }

    public ClassyTreeView getTreeView() {
        return treeView;
    }
}
