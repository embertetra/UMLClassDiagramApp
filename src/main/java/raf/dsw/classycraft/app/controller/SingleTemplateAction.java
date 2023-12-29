package raf.dsw.classycraft.app.controller;

import javafx.scene.control.TreeView;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Agregacija;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Generalizacija;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Kompozicija;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Zavisnost;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.EnumM;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Interfejs;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Klasa;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Package;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.tree.ClassyTreeImplementation;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.tree.view.ClassyTreeView;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.PackageView;
import raf.dsw.classycraft.app.gui.swing.view.painters.ConnectionPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter.AgregacijaPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter.GeneralizacijaPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter.KompozicijaPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter.ZavisnostPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.EnumPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.InterfejsPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.KlasaPainter;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.io.File;

public class SingleTemplateAction extends AbstractClassyAction {

    private String name;
    private File file;
    private Dijagram prosledjen;

    public SingleTemplateAction(String name) {
        this.name = name;
        prosledjen = null;
        //putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        putValue(SMALL_ICON, loadIcon("/images/newDiagram.png"));
        putValue(NAME, name);
        putValue(SHORT_DESCRIPTION, name);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        prosledjen = MainFrame.getInstance().getGalleryTemplates().getProsledjen();

        for (File f : MainFrame.getInstance().getGalleryTemplates().getListFiles()) {
            if (f.getName().equals(name))
                file = f;
        }

        if(file == null){

            ClassyTreeItem paket = MainFrame.getInstance().getClassyTree().getSelectedNode();
            ClassyTreeItem dijagramTree = new ClassyTreeItem(prosledjen);
            paket.add(dijagramTree);

            prosledjen.setParent(paket.getClassyNode());
            ((Package)paket.getClassyNode()).addChild(prosledjen);

            DijagramView dijagramView = new DijagramView(prosledjen);
            insertNewJTabb(dijagramView, prosledjen.getParent(), paket);

            ClassyTreeImplementation classyTreeImplementation = (ClassyTreeImplementation) MainFrame.getInstance().getClassyTree();
            ClassyTreeView treeView = classyTreeImplementation.getTreeView();
            treeView.expandPath(treeView.getSelectionPath());
            SwingUtilities.updateComponentTreeUI(classyTreeImplementation.getTreeView());

            MainFrame.getInstance().getGalleryTemplates().setVisible(false);
            return;
        }

        //loadujem fajl i dodeljujem mu ime DijagramX
        Dijagram dijagram = ApplicationFramework.getInstance().getSerializer().loadTemplate(file);
        dijagram.setName(prosledjen.getName());
        ///dodajem u drvo
        ClassyTreeItem paket = MainFrame.getInstance().getClassyTree().getSelectedNode();
        ClassyTreeItem dijagramTree = new ClassyTreeItem(dijagram);
        paket.add(dijagramTree);
        ///dodajem u model
        dijagram.setParent(paket.getClassyNode());
        ((Package) paket.getClassyNode()).addChild(dijagram);

        ///dodajem svu decu u stablo i kreiram paintere
        DijagramView dijagramView = new DijagramView(dijagram);
        for (ClassyNode c : dijagram.getChildren()) {
            dijagramTree.add(new ClassyTreeItem(c));
            createPainter(c, dijagramView);
        }
        insertNewJTabb(dijagramView, dijagram.getParent(), paket);


        ///update stabla i prosirenje
        ClassyTreeImplementation classyTreeImplementation = (ClassyTreeImplementation) MainFrame.getInstance().getClassyTree();
        ClassyTreeView treeView = classyTreeImplementation.getTreeView();
        treeView.expandPath(treeView.getSelectionPath());
        TreePath tp = new TreePath(dijagramTree.getPath());
        treeView.expandPath(tp);
        SwingUtilities.updateComponentTreeUI(classyTreeImplementation.getTreeView());

        MainFrame.getInstance().getGalleryTemplates().setVisible(false);

    }

    public void insertNewJTabb(DijagramView dijagramView, ClassyNode selected, ClassyTreeItem classyTreeItem) {
        PackageView paket = null;
        for (PackageView p : MainFrame.getInstance().getListaPackageView()) {
            if (p.getClassyNode().getName().equals(selected.getName())) {
                paket = p;
            }
        }

        if (paket != null) {
            paket.getjTabbedPane().addTab(dijagramView.getClassyNode().getName(), dijagramView);
            paket.getjTabbedPane().addTab(dijagramView.getClassyNode().getName(), dijagramView);
            int tmp=0;
            for(int i=0;i<paket.getjTabbedPane().getTabCount();i++){
                if(paket.getjTabbedPane().getTitleAt(i).equals(dijagramView.getClassyNode().getName())) {
                    tmp++;
                }
                if(tmp==2){
                    paket.getjTabbedPane().remove(i-1);
                    break;
                }
            }
            dijagramView.repaint();
        }
        else {
            paket = new PackageView();
            paket.setClassyNode(selected);
            ((Package)selected).addSubscriber(paket);
            paket.setClassyTreeItem(classyTreeItem);
            paket.getjTabbedPane().addTab(dijagramView.getClassyNode().getName(), dijagramView);
            MainFrame.getInstance().getListaPackageView().add(paket);
        }
    }

    public void createPainter(ClassyNode cn, DijagramView dijagramView) {
        ElementPainter ep = null;
        if (cn instanceof Klasa) {
            Klasa k = (Klasa) cn;
            k.addSubscriber(dijagramView);
            ep = new KlasaPainter(k);
        } else if (cn instanceof Interfejs) {
            Interfejs i = (Interfejs) cn;
            i.addSubscriber(dijagramView);
            ep = new InterfejsPainter(i);
        } else if (cn instanceof EnumM) {
            EnumM en = (EnumM) cn;
            en.addSubscriber(dijagramView);
            ep = new EnumPainter(en);
        } else if (cn instanceof Agregacija) {
            Agregacija a = (Agregacija) cn;
            ep = new AgregacijaPainter(a);
        } else if (cn instanceof Generalizacija) {
            Generalizacija g = (Generalizacija) cn;
            ep = new GeneralizacijaPainter(g);
        } else if (cn instanceof Kompozicija) {
            Kompozicija k = (Kompozicija) cn;
            ep = new KompozicijaPainter(k);
        } else if (cn instanceof Zavisnost) {
            Zavisnost z = (Zavisnost) cn;
            ep = new ZavisnostPainter(z);
        }
        if (ep != null) {
            dijagramView.getElementPainterList().add(ep);
        }

    }

    public void setProsledjen(Dijagram prosledjen) {
        this.prosledjen = prosledjen;
    }
}
