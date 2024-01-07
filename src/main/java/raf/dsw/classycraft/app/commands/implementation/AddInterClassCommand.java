package raf.dsw.classycraft.app.commands.implementation;

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
import raf.dsw.classycraft.app.commands.AbstractCommand;
import raf.dsw.classycraft.app.gui.swing.tree.ClassyTreeImplementation;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
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
import java.util.ArrayList;
import java.util.List;

public class AddInterClassCommand extends AbstractCommand {

    private Interclass interclass;
    private DijagramView dijagramView;

    public AddInterClassCommand(Interclass interclass, DijagramView dijagramView) {
        this.interclass = interclass;
        this.dijagramView = dijagramView;
    }

    @Override
    public void doCommand() {
        //AffineTransform at = dijagramView.getAt();

        System.out.println("koordinate pre do commande " + interclass.getPosition());
        ///odredjivanje dijagrama unutar stabla
        ClassyTreeItem item = null;
        ClassyTreeItem selected = MainFrame.getInstance().getPackageView().getClassyTreeItem();
        for (int i = 0; i < selected.getChildCount(); i++) {
            ClassyTreeItem c = (ClassyTreeItem) selected.getChildAt(i);
            ClassyNode cn = c.getClassyNode();
            if (cn.getName().equals(dijagramView.getClassyNode().getName()))
                item = c;
        }
        Dijagram d = (Dijagram) dijagramView.getClassyNode();

        if (interclass instanceof Klasa) {
            Klasa klasa = (Klasa)interclass;
            klasa.addSubscriber(dijagramView);
            KlasaPainter klasaPainter = new KlasaPainter(klasa);
            dijagramView.getElementPainterList().add(klasaPainter);
            d.addChild(klasa);///dodoavanje u model
            MainFrame.getInstance().getClassyTree().addChild(item, klasa);///dodavanje u stablo
        } else if (interclass instanceof Interfejs) {
            Interfejs interfejs = (Interfejs)interclass;
            interfejs.addSubscriber(dijagramView);
            InterfejsPainter interfejsPainter = new InterfejsPainter(interfejs);
            dijagramView.getElementPainterList().add(interfejsPainter);
            d.addChild(interfejs);
            MainFrame.getInstance().getClassyTree().addChild(item, interfejs);
        } else if (interclass instanceof EnumM) {
            EnumM enumM = (EnumM)interclass;
            enumM.addSubscriber(dijagramView);
            EnumPainter enumPainter = new EnumPainter(enumM);
            dijagramView.getElementPainterList().add(enumPainter);
            d.addChild(enumM);
            MainFrame.getInstance().getClassyTree().addChild(item, enumM);
        }
        fixModel(item);
    }

    @Override
    public void undoCommand() {
        System.out.println("koordinate pre undo commande " + interclass.getPosition());
        dijagramView.getSelectionModel().clear();
        ///odredjivanje dijagrama unutar stabla
        ClassyTreeItem item = null;
        ClassyTreeItem selected = MainFrame.getInstance().getPackageView().getClassyTreeItem();
        for (int i = 0; i < selected.getChildCount(); i++) {
            ClassyTreeItem c = (ClassyTreeItem) selected.getChildAt(i);
            ClassyNode cn = c.getClassyNode();
            if (cn.getName().equals(dijagramView.getClassyNode().getName()))
                item = c;
        }
        //fixModel(item);
        for(int j=dijagramView.getElementPainterList().size()-1; j>=0; j--){
            ElementPainter elementPainter = dijagramView.getElementPainterList().get(j);
            ///brisanje pojedinacne interklase
            if (elementPainter instanceof InterclassPainter) {
                Interclass klasaBrisanje = (Interclass) elementPainter.getElement();
                if (item != null) {
                    for (int i = 0; i < item.getChildCount(); i++) {
                        if (((ClassyTreeItem) item.getChildAt(i)).getClassyNode() instanceof Interclass) {
                            Interclass inter = (Interclass) ((ClassyTreeItem) item.getChildAt(i)).getClassyNode();
                            if (inter.poredjenje(interclass)) {
                                System.out.println("orbisano" + interclass.getPosition());
                                item.remove(i);
                                break;
                            }
                        }
                    }
                }

                ClassyTreeImplementation classyTreeImplementation = (ClassyTreeImplementation) MainFrame.getInstance().getClassyTree();
                SwingUtilities.updateComponentTreeUI(classyTreeImplementation.getTreeView());
                break;
            }
        }

        fixModel(item);
    }

    private void fixModel(ClassyTreeItem item){
        ///sredjivanje modela i paintera
        List<ElementPainter> novaPainterLista = new ArrayList<>();
        List<ClassyNode> novaLista = new ArrayList<>();
        for (int i = 0; i < item.getChildCount(); i++) {
            ClassyTreeItem cti = (ClassyTreeItem) item.getChildAt(i);
            novaLista.add(cti.getClassyNode());
            if (cti.getClassyNode() instanceof Klasa) {
                ((Klasa)cti.getClassyNode()).addSubscriber(dijagramView);
                novaPainterLista.add(new KlasaPainter((Klasa) cti.getClassyNode()));
            }
            else if (cti.getClassyNode() instanceof Interfejs) {
                ((Interfejs)cti.getClassyNode()).addSubscriber(dijagramView);
                novaPainterLista.add(new InterfejsPainter((Interfejs) cti.getClassyNode()));
            }
            else if (cti.getClassyNode() instanceof EnumM) {
                ((EnumM)cti.getClassyNode()).addSubscriber(dijagramView);
                novaPainterLista.add(new EnumPainter((EnumM) cti.getClassyNode()));
            }
            else if (cti.getClassyNode() instanceof Agregacija) {
                novaPainterLista.add(new AgregacijaPainter((Agregacija) cti.getClassyNode()));
            }
            else if (cti.getClassyNode() instanceof Kompozicija) {
                novaPainterLista.add(new KompozicijaPainter((Kompozicija) cti.getClassyNode()));
            }
            else if (cti.getClassyNode() instanceof Generalizacija) {
                novaPainterLista.add(new GeneralizacijaPainter((Generalizacija) cti.getClassyNode()));
            }
            else if (cti.getClassyNode() instanceof Zavisnost) {
                novaPainterLista.add(new ZavisnostPainter((Zavisnost) cti.getClassyNode()));
            }
        }
        Dijagram d = (Dijagram) item.getClassyNode();
        d.setChildren(novaLista);
        dijagramView.getElementPainterList().clear();
        dijagramView.setElementPainterList(novaPainterLista);
        dijagramView.repaint();
    }

}
