package raf.dsw.classycraft.app.commands.implementation;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Connection;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.DijagramElement;
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
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SingleDeleteCommand extends AbstractCommand {

    private List<DijagramElement> list = new ArrayList<>();
    private DijagramView dijagramView;
    private int x;
    private int y;

    public SingleDeleteCommand(int x, int y, DijagramView dijagramView){
        this.dijagramView = dijagramView;
        this.x = x;
        this.y = y;
    }

    @Override
    public void doCommand() {
        ///pronalazenje dijagrama u stablu
        ClassyTreeItem item = null;
        ClassyTreeItem selected = MainFrame.getInstance().getPackageView().getClassyTreeItem();
        for (int i = 0; i < selected.getChildCount(); i++) {
            ClassyTreeItem c = (ClassyTreeItem) selected.getChildAt(i);
            ClassyNode cn = c.getClassyNode();
            if (cn.getName().equals(dijagramView.getClassyNode().getName()))
                item = c;
        }

        Interclass selektovanaKlasa = null;//klasa na koju smo kliknuli. Cuvam je kako bi posle mogao da obrisem sve veze koje su spojene sa njom
        ///brisanje pojedinacog elementa
        for (Iterator<ElementPainter> iterator = dijagramView.getElementPainterList().iterator(); iterator.hasNext(); ) {
            ElementPainter elementPainter = iterator.next();
            ///brisanje pojedinacne interklase
            if (elementPainter instanceof InterclassPainter && elementPainter.elementAt(new Point(x, y))) {
                Interclass klasaBrisanje = (Interclass) elementPainter.getElement();
                selektovanaKlasa = klasaBrisanje;
                if (item != null) {
                    for (int i = 0; i < item.getChildCount(); i++) {
                        if (((ClassyTreeItem) item.getChildAt(i)).getClassyNode() instanceof Interclass) {
                            Interclass inter = (Interclass) ((ClassyTreeItem) item.getChildAt(i)).getClassyNode();
                            if (inter.poredjenje(klasaBrisanje)) {
                                Dijagram dijagram = (Dijagram) item.getClassyNode();
                                Interclass interclass = (Interclass) ((ClassyTreeItem) item.getChildAt(i)).getClassyNode();
                                dijagram.removeChild(interclass);
                                item.remove(i);
                                list.add(interclass);
                                break;
                            }
                        }
                    }
                }
                ClassyTreeImplementation classyTreeImplementation = (ClassyTreeImplementation) MainFrame.getInstance().getClassyTree();
                SwingUtilities.updateComponentTreeUI(classyTreeImplementation.getTreeView());
                iterator.remove();
                break;
            }
            ///brisanje pojedinacne veze
            else if (elementPainter instanceof ConnectionPainter) {
                ConnectionPainter painter = (ConnectionPainter) elementPainter;
                Connection vezaBrisanje = (Connection) elementPainter.getElement();
                Shape shape = makeShape(x, y);
                Line2D line = null;
                if (painter.getPoint1() != null && painter.getPoint2() != null)
                    line = new Line2D.Double(painter.getPoint1().x, painter.getPoint1().y, painter.getPoint2().x, painter.getPoint2().y);
                if (line != null && shape.getBounds2D().intersectsLine(line)) {
                    if (item != null) {
                        for (int i = 0; i < item.getChildCount(); i++) {
                            if (((ClassyTreeItem) item.getChildAt(i)).getClassyNode() instanceof Connection) {
                                Connection cn = (Connection) ((ClassyTreeItem) item.getChildAt(i)).getClassyNode();
                                if (cn.poredjenje(vezaBrisanje)) {
                                    list.add(vezaBrisanje);
                                    item.remove(i);
                                    ((Dijagram) item.getClassyNode()).removeChild(vezaBrisanje);
                                    ClassyTreeImplementation classyTreeImplementation = (ClassyTreeImplementation) MainFrame.getInstance().getClassyTree();
                                    SwingUtilities.updateComponentTreeUI(classyTreeImplementation.getTreeView());
                                    iterator.remove();
                                    break;
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }
        ///brisanje svih connectionsa koji su bili vezani sa klasom koja je obrisana
        if (selektovanaKlasa != null) {
            for (int i = 0; i < item.getChildCount(); i++) {
                ClassyTreeItem cti = (ClassyTreeItem) item.getChildAt(i);
                ClassyNode cn = cti.getClassyNode();
                if (cn instanceof Connection) {
                    Connection cnn = (Connection) cn;
                    if (cnn.getFrom().poredjenje(selektovanaKlasa) || cnn.getTo().poredjenje(selektovanaKlasa)) {
                        ((ClassyTreeItem) item.getChildAt(i)).removeFromParent();
                        i--;
                        ClassyTreeImplementation classyTreeImplementation = (ClassyTreeImplementation) MainFrame.getInstance().getClassyTree();
                        SwingUtilities.updateComponentTreeUI(classyTreeImplementation.getTreeView());
                    }
                }
            }
        }

        ///sredjivanje modela i paintera
        java.util.List<ElementPainter> novaPainterLista = new ArrayList<>();
        List<ClassyNode> novaLista = new ArrayList<>();
        for(int i=0;i< item.getChildCount();i++){
            ClassyTreeItem cti = (ClassyTreeItem) item.getChildAt(i);
            novaLista.add(cti.getClassyNode());
            if(cti.getClassyNode() instanceof Klasa)
                novaPainterLista.add(new KlasaPainter((Klasa) cti.getClassyNode()));
            else if(cti.getClassyNode() instanceof Interfejs)
                novaPainterLista.add(new InterfejsPainter((Interfejs) cti.getClassyNode()));
            else if(cti.getClassyNode() instanceof EnumM)
                novaPainterLista.add(new EnumPainter((EnumM) cti.getClassyNode()));
            else if(cti.getClassyNode() instanceof Agregacija)
                novaPainterLista.add(new AgregacijaPainter((Agregacija) cti.getClassyNode()));
            else if(cti.getClassyNode() instanceof Kompozicija)
                novaPainterLista.add(new KompozicijaPainter((Kompozicija) cti.getClassyNode()));
            else if(cti.getClassyNode() instanceof Generalizacija)
                novaPainterLista.add(new GeneralizacijaPainter((Generalizacija) cti.getClassyNode()));
            else if(cti.getClassyNode() instanceof Zavisnost)
                novaPainterLista.add(new ZavisnostPainter((Zavisnost) cti.getClassyNode()));
        }
        Dijagram d = (Dijagram) item.getClassyNode();
        d.setChildren(novaLista);
        dijagramView.getElementPainterList().clear();
        dijagramView.setElementPainterList(novaPainterLista);
        dijagramView.repaint();
    }

    @Override
    public void undoCommand() {
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

        if(list.get(list.size()-1) instanceof Interclass){
            Interclass interclass = (Interclass) list.get(list.size()-1);
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
        }
        else if(list.get(list.size()-1) instanceof Connection){
            ///
            ///\
            ///
            ///
            //
            //
            //
        }
    }
    public Shape makeShape(int x, int y){
        Shape shape = new GeneralPath();
        ((GeneralPath) shape).moveTo(x - 10, y - 10);
        ((GeneralPath) shape).lineTo(x + 10, y - 10);
        ((GeneralPath) shape).lineTo(x + 10, y + 10);
        ((GeneralPath) shape).lineTo(x - 10, y + 10);
        ((GeneralPath) shape).lineTo(x - 10, y - 10);
        ((GeneralPath) shape).closePath();
        return shape;
    }

}
