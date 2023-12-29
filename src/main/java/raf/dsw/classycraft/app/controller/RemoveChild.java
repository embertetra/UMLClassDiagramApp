package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.DijagramElement;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Package;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Project;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.ProjectExplorer;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.MessageType;
import raf.dsw.classycraft.app.gui.swing.tree.ClassyTreeImplementation;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.PackageView;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Iterator;

public class RemoveChild extends AbstractClassyAction {

    public RemoveChild() {
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/images/bin.png"));
        putValue(NAME, "Remove child");
        putValue(SHORT_DESCRIPTION, "Remove child");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ClassyTreeItem selected = (ClassyTreeItem) MainFrame.getInstance().getClassyTree().getSelectedNode();
        if (selected != null) {

            ///ako korisnik zeli da obrise ProjectExplorer
            if (selected.getClassyNode() instanceof ProjectExplorer)
                ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("ProjectExplorer nije moguce obrisati!", MessageType.ERROR);
            ///ako zeli da obrise projekat
            if (selected.getClassyNode() instanceof Project) {

                Object[] options = {"Da", "Ne"};
                int n = JOptionPane.showOptionDialog(MainFrame.getInstance(),
                        "Da li ste sigurni da zelite da obrisete paket?",
                        "Brisanje paketa",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, //Difoltna ikonica
                        options,
                        options[1]);

                if (n == 0) {
                    MainFrame.getInstance().getClassyTree().getSelectedNode().removeFromParent();
                    ClassyNodeComposite cns = (ClassyNodeComposite) selected.getClassyNode().getParent();
                    if (cns.getChildren().contains(selected.getClassyNode()))
                        cns.removeChild(selected.getClassyNode());
                    ClassyTreeImplementation classyTreeImplementation = (ClassyTreeImplementation) MainFrame.getInstance().getClassyTree();
                    SwingUtilities.updateComponentTreeUI(classyTreeImplementation.getTreeView());
                }

            }
            ///potrebno prepraviti
            ///brisanje dijagramElemenata
            /*else if (selected.getClassyNode() instanceof DijagramElement) {
                DijagramView dijagramView = (DijagramView) MainFrame.getInstance().getPackageView().getjTabbedPane().getSelectedComponent();
                if (dijagramView != null) {
                    for (Iterator<ElementPainter> iterator = dijagramView.getElementPainterList().iterator(); iterator.hasNext(); ) {
                        ElementPainter el = iterator.next();
                        if (el.getElement() == selected.getClassyNode()) {
                            iterator.remove();
                            dijagramView.repaint();
                            MainFrame.getInstance().getClassyTree().getSelectedNode().removeFromParent();
                        }
                    }
                    dijagramView.getSelectionModel().clear();
                }
                else {
                    ClassyNode c = MainFrame.getInstance().getClassyTree().getSelectedNode().getClassyNode();
                    ((ClassyNodeComposite)c.getParent()).removeChild(c);
                    MainFrame.getInstance().getClassyTree().getSelectedNode().removeFromParent();
                }
                ClassyTreeImplementation classyTreeImplementation = (ClassyTreeImplementation) MainFrame.getInstance().getClassyTree();
                SwingUtilities.updateComponentTreeUI(classyTreeImplementation.getTreeView());
            }*/
            ///ako zeli da obrise paket ili dijagram
            else if(selected.getClassyNode() instanceof DijagramElement){
                ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Dijagram elemente mozete brisati na samom dijagramu", MessageType.INFO);
            }
            else if (!(selected.getClassyNode() instanceof ProjectExplorer)) {
                if (selected.getClassyNode() instanceof Package) {
                    for (Iterator<PackageView> iterator = MainFrame.getInstance().getListaPackageView().iterator(); iterator.hasNext(); ) {
                        PackageView pv = iterator.next();
                        if (pv.getClassyNode() == selected.getClassyNode()) {
                            iterator.remove();
                            System.out.println(pv.getClassyNode().getName());
                        }
                    }
                }
                MainFrame.getInstance().getClassyTree().getSelectedNode().removeFromParent();
                ClassyNodeComposite cns = (ClassyNodeComposite) selected.getClassyNode().getParent();
                if (cns.getChildren().contains(selected.getClassyNode()))
                    cns.removeChild(selected.getClassyNode());
                ClassyTreeImplementation classyTreeImplementation = (ClassyTreeImplementation) MainFrame.getInstance().getClassyTree();
                SwingUtilities.updateComponentTreeUI(classyTreeImplementation.getTreeView());
            }
            ///brise selekciju nakon brisanja
            ClassyTreeImplementation cti = (ClassyTreeImplementation) MainFrame.getInstance().getClassyTree();
            cti.getTreeView().clearSelection();
        }
        ///ako nista nije selektovano
        else
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Nije selektovano polje za brisanje", MessageType.ERROR);
    }
}
