package raf.dsw.classycraft.app.controller.MouseListeners.dvoklikNaPaket;

import com.sun.tools.javac.Main;
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
                    ClassyTreeItem selected = MainFrame.getInstance().getClassyTree().getSelectedNode();
                    ClassyNode selectedNode = null;
                    if(selected != null)
                        selectedNode = selected.getClassyNode();
                    PackageView paket = null;
                    if ((selected instanceof ClassyTreeItem && selectedNode != null && selectedNode instanceof Package)) {
                        if (e.getClickCount() == 2) {
                            /*
                            for (PackageView p : MainFrame.getInstance().getListaPackageView()) {
                                if (p.getClassyNode().getName().equals(selectedNode.getName())) {
                                    paket = p;
                                }
                            }
                            if (paket == null) {
                                System.out.println("nul je");
                                paket = new PackageView();
                                MainFrame.getInstance().setPackageView(paket);
                                ((Package) selectedNode).addSubscriber(MainFrame.getInstance().getPackageView());
                                paket.setClassyNode(selectedNode);
                                for (ClassyNode c : ((Package) selectedNode).getChildren()) {
                                    if (c instanceof Dijagram) {
                                        DijagramView dijagramView = new DijagramView(c);
                                        ((Dijagram) c).addSubscriber(dijagramView);
                                        paket.getjTabbedPane().addTab(c.getName(), dijagramView);
                                    }
                                }
                                MainFrame.getInstance().setPackageView(paket);
                                MainFrame.getInstance().getListaPackageView().add(paket);

                            } else {
                                System.out.println("nije null");
                                MainFrame.getInstance().setPackageView(paket);
                            }
                            System.out.println(paket.getjTabbedPane().getTabCount());
                            */

                            ///OVO TREBA MENJATIIIIIII, Ako obrisem sve jtabove gubim sve dijagramViewove !>!>!>!>!>!?!
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
                            MainFrame.getInstance().getPackageView().setClassyNode(selected.getClassyNode());


                            ///ispisivanje projekta i autora na packageView trebalo bi da ostaje kao i pre
                            while(true){
                                if(selectedNode instanceof Project) {
                                    MainFrame.getInstance().getPackageView().promeniNazivAutora(((Project) selectedNode).getAutor());
                                    break;
                                }
                                else {
                                    selectedNode = selectedNode.getParent();
                                }
                            }
                            ((Project) selectedNode).addSubscriber(MainFrame.getInstance().getPackageView());
                            MainFrame.getInstance().getPackageView().promeniNazivProjekta(selectedNode.getName());
                            ((ProjectExplorer)selectedNode.getParent()).addSubscriber(MainFrame.getInstance().getPackageView());

                        }
                    }
                }
            });
        }
    }
}
