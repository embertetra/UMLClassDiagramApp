package raf.dsw.classycraft.app.controller.MouseListeners.dvoklikNaPaket;

import com.sun.tools.javac.Main;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.DijagramElement;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Agregacija;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Generalizacija;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Kompozicija;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Zavisnost;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.EnumM;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Interfejs;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Klasa;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Package;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Project;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.ProjectExplorer;
import raf.dsw.classycraft.app.controller.MouseListeners.MouseController;
import raf.dsw.classycraft.app.gui.swing.tree.ClassyTreeImplementation;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.tree.view.ClassyTreeView;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.PackageView;
import raf.dsw.classycraft.app.gui.swing.view.painters.ConnectionPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter.AgregacijaPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter.GeneralizacijaPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter.KompozicijaPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter.ZavisnostPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.EnumPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.InterfejsPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.KlasaPainter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

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
                    if ((selected instanceof ClassyTreeItem &&  selectedNode instanceof Package)) {
                        if (e.getClickCount() == 2) {
                            for (PackageView p : MainFrame.getInstance().getListaPackageView()) {
                                if (p.getClassyNode().getName().equals(selectedNode.getName())) {
                                    paket = p;
                                }
                            }
                            if (paket == null) {
                                paket = new PackageView();
                                paket.setClassyTreeItem(selected);
                                MainFrame.getInstance().getListaPackageView().add(paket);
                                ((Package) selectedNode).addSubscriber(paket);
                                paket.setClassyNode(selectedNode);
                                for (ClassyNode c : ((Package) selectedNode).getChildren()) {
                                    if (c instanceof Dijagram) {
                                        DijagramView dijagramView = new DijagramView(c);
                                        ((Dijagram) c).addSubscriber(dijagramView);
                                        paket.getjTabbedPane().addTab(c.getName(),dijagramView);

                                        ///prolazim kroz svu decu dijagrama i kreiram paintere za njih
                                        ///prvo za interclasse
                                        for(ClassyNode cn : ((Dijagram)c).getChildren()){
                                            InterclassPainter ip = null;
                                            if(cn instanceof Klasa){
                                                Klasa k = (Klasa) cn;
                                                k.addSubscriber(dijagramView);
                                                ip = new KlasaPainter(k);
                                            }
                                            else if(cn instanceof Interfejs){
                                                Interfejs i = (Interfejs) cn;
                                                i.addSubscriber(dijagramView);
                                                ip = new InterfejsPainter(i);
                                            }
                                            else if(cn instanceof EnumM){
                                                EnumM en = (EnumM) cn;
                                                en.addSubscriber(dijagramView);
                                                ip = new EnumPainter(en);
                                            }
                                            if(ip != null)
                                                dijagramView.getElementPainterList().add(ip);
                                        }
                                        ///sada za konekcije
                                        for(ClassyNode cn : ((Dijagram)c).getChildren()){
                                            ConnectionPainter cp = null;
                                            if(cn instanceof Agregacija){
                                                Agregacija a = (Agregacija) cn;
                                                cp = new AgregacijaPainter(a);
                                            }
                                            else if(cn instanceof Generalizacija){
                                                Generalizacija g = (Generalizacija) cn;
                                                cp = new GeneralizacijaPainter(g);
                                            }
                                            else if(cn instanceof Kompozicija){
                                                Kompozicija k = (Kompozicija) cn;
                                                cp = new KompozicijaPainter(k);
                                            }
                                            else if(cn instanceof Zavisnost){
                                                Zavisnost z = (Zavisnost) cn;
                                                cp = new ZavisnostPainter(z);
                                            }
                                            if(cp != null)
                                                dijagramView.getElementPainterList().add(cp);
                                        }
                                    }
                                }
                                MainFrame.getInstance().setPackageView(paket);
                            } else {
                                MainFrame.getInstance().setPackageView(paket);
                            }

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
