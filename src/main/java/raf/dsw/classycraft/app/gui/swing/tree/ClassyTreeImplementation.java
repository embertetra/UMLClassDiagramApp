package raf.dsw.classycraft.app.gui.swing.tree;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.DijagramElement;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.EnumM;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Interfejs;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Klasa;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Package;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Project;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.ProjectExplorer;
import raf.dsw.classycraft.app.controller.MouseListeners.dvoklikNaPaket.MouseListener;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.MessageType;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.tree.model.childFactory.FactoryUtils;
import raf.dsw.classycraft.app.gui.swing.tree.view.ClassyTreeView;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.EnumPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.InterfejsPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.KlasaPainter;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class ClassyTreeImplementation implements ClassyTree {

    private ClassyTreeView treeView;

    private DefaultTreeModel treeModel;


    @Override
    public ClassyTreeView generateTree(ProjectExplorer projectExplorer) {
        ClassyTreeItem root = new ClassyTreeItem(projectExplorer);
        treeModel = new DefaultTreeModel(root);
        treeView = new ClassyTreeView(treeModel);
        MainFrame.getInstance().getActionManager().setMouseListener(new MouseListener());
        return treeView;
    }

    @Override
    public void addChild(ClassyTreeItem parent, DijagramElement dijagramElement) {

        if (parent != null && !(parent.getClassyNode() instanceof ClassyNodeComposite)) {
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("DijagramElement ne moze imati podklasu!", MessageType.ERROR);
            return;
        }
        ClassyNode child = createChild(parent.getClassyNode(), dijagramElement);
        if (child != null) {
            child.setParent(parent.getClassyNode());
            parent.add(new ClassyTreeItem(child)); //prikazuje se u JTree-u
            if (!(child instanceof DijagramElement)) {
                ((ClassyNodeComposite) parent.getClassyNode()).addChild(child); // dodaje se u modelu addChild()
            }
            treeView.expandPath(treeView.getSelectionPath());
            TreePath tp = new TreePath(parent.getPath());
            treeView.expandPath(tp);
            SwingUtilities.updateComponentTreeUI(treeView);
        }
    }

    @Override
    public ClassyTreeItem getSelectedNode() {
        return (ClassyTreeItem) treeView.getLastSelectedPathComponent();
    }

    public void insertAllChildren(ClassyNodeComposite parent, ClassyTreeItem parentTree) {

        for (ClassyNode c : parent.getChildren()) {
            if (c instanceof Package || c instanceof Dijagram) {
                ClassyTreeItem treeItem = new ClassyTreeItem(c);
                parentTree.add(treeItem);
                insertAllChildren((ClassyNodeComposite) c, treeItem);
            } else {
                parentTree.add(new ClassyTreeItem(c));
                TreePath tp = new TreePath(parentTree.getPath());
                treeView.expandPath(tp);
            }
        }

    }

    @Override
    public void loadProject(Project project) {

        ClassyTreeItem loadedProject = new ClassyTreeItem(project);
        ((ClassyTreeItem) treeModel.getRoot()).add(loadedProject);

        ClassyNodeComposite classyNodeComposite = (ClassyNodeComposite) ((ClassyTreeItem) treeModel.getRoot()).getClassyNode();
        classyNodeComposite.addChild(project);

        insertAllChildren(project, loadedProject);

        project.setParent(ApplicationFramework.getInstance().getClassyRepository().getRoot());

        TreePath tp = new TreePath(loadedProject.getPath());
        treeView.expandPath(tp);
        treeView.expandPath(treeView.getSelectionPath());
        SwingUtilities.updateComponentTreeUI(treeView);

    }


    private ClassyNode createChild(ClassyNode parent, DijagramElement dijagramElement) {

        FactoryUtils factoryUtils = ApplicationFramework.getInstance().getFactoryUtils();

        if (parent != null) {
            return factoryUtils.generateChild(parent, dijagramElement);
        }
        return null;
    }

    public ClassyTreeView getTreeView() {
        return treeView;
    }
}
