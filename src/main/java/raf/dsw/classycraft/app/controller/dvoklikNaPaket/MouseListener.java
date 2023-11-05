package raf.dsw.classycraft.app.controller.dvoklikNaPaket;

import com.sun.tools.javac.Main;
import javafx.scene.layout.Pane;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Package;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Project;
import raf.dsw.classycraft.app.gui.swing.tree.ClassyTreeImplementation;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.tree.view.ClassyTreeView;
import raf.dsw.classycraft.app.gui.swing.view.DijaframView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import javax.swing.*;
import javax.swing.tree.TreePath;
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
                            MainFrame.getInstance().getPackageView().getjTabbedPane().removeAll();
                            for (ClassyNode c : ((Package) selected.getClassyNode()).getChildren()) {
                                if (c instanceof Dijagram) {

                                    ///dodavanje tabova u listu i u sam JTabb
                                    ///Kad se bude crtalo ovde treba dodati proveru da li taj dijagramView vec postoji
                                    ///kako bi uzeo vec postojeci sa crtezom a ne kreirao novi svaki put
                                    DijaframView dijaframView = new DijaframView(c);
                                    MainFrame.getInstance().getPackageView().addInTabList(dijaframView);
                                    MainFrame.getInstance().getPackageView().getjTabbedPane().addTab(c.getName(), dijaframView);

                                }
                            }
                            MainFrame.getInstance().getPackageView().setParent(selected.getClassyNode());

                            ClassyNode parent = selected.getClassyNode();
                            while(true){
                                if(parent instanceof Project)
                                    break;
                                else parent = parent.getParent();
                            }
                            MainFrame.getInstance().getPackageView().promeniNazivProjekta(parent.getName());

                        }
                    }
                }
            });
        } else System.out.println("test nije prosao");
    }
}