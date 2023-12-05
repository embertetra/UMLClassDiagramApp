package raf.dsw.classycraft.app.controller.dvoklikNaPaket;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Package;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Project;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.ProjectExplorer;
import raf.dsw.classycraft.app.gui.swing.tree.ClassyTreeImplementation;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.tree.view.ClassyTreeView;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.PackageView;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseListener {

    ClassyTreeImplementation classyTreeImplementation = (ClassyTreeImplementation) MainFrame.getInstance().getClassyTree();
    ClassyTreeView treeView;

    public MouseListener() {
        if (classyTreeImplementation != null) {
            treeView = classyTreeImplementation.getTreeView();
            treeView.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    ClassyTreeItem selected = (ClassyTreeItem) MainFrame.getInstance().getClassyTree().getSelectedNode();
                    if ((selected instanceof ClassyTreeItem && selected.getClassyNode() instanceof Package)) {
                        if (e.getClickCount() == 2) {
                            ((Package) selected.getClassyNode()).addSubscriber(MainFrame.getInstance().getPackageView());
                            MainFrame.getInstance().getPackageView().getjTabbedPane().removeAll();
                            for (ClassyNode c : ((Package) selected.getClassyNode()).getChildren()) {
                                if (c instanceof Dijagram) {
                                    DijagramView dijagramView = new DijagramView(c);
                                    ((Dijagram) c).addSubscriber(dijagramView);
                                    //MainFrame.getInstance().getPackageView().addInTabList(dijagramView);
                                    MainFrame.getInstance().getPackageView().getjTabbedPane().addTab(c.getName(), dijagramView);
                                }
                            }
                            //setovanje parenta packageView-u
                            MainFrame.getInstance().getPackageView().setParent(selected.getClassyNode());

                            ///ispisivanje projekta i autora na packageView
                            ClassyNode parent = selected.getClassyNode();
                            while(true){
                                if(parent instanceof Project) {
                                    MainFrame.getInstance().getPackageView().promeniNazivAutora(((Project) parent).getAutor());
                                    break;
                                }
                                else {
                                    parent = parent.getParent();
                                }
                            }
                            ((Project) parent).addSubscriber(MainFrame.getInstance().getPackageView());
                            MainFrame.getInstance().getPackageView().promeniNazivProjekta(parent.getName());
                            ((ProjectExplorer)parent.getParent()).addSubscriber(MainFrame.getInstance().getPackageView());

                        }
                    }
                }
            });
        }
    }
}
