package raf.dsw.classycraft.app.stateSablon.states;

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
import raf.dsw.classycraft.app.stateSablon.State;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Delete implements State {

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

    @Override
    public void misKliknut(int x, int y, DijagramView dijagramView) {
        ///prebaci i makeShape metodu!!!!


        ///pronalazenje dijagrama u stablu
        ClassyTreeItem item = null;
        ClassyTreeItem selected = MainFrame.getInstance().getPackageView().getClassyTreeItem();
        for (int i = 0; i < selected.getChildCount(); i++) {
            ClassyTreeItem c = (ClassyTreeItem) selected.getChildAt(i);
            ClassyNode cn = c.getClassyNode();
            if (cn.getName().equals(dijagramView.getClassyNode().getName()))
                item = c;
        }

        int flag = 0;
        for (Shape s : dijagramView.getSelectionModel()) {
            if (s.contains(new Point(x, y))) flag = 1;
        }
        ///brisanje multiselekcije
        if(dijagramView.getSelectionModel().size() > 0 && flag == 1){
            for(Iterator<ElementPainter> iterator = dijagramView.getElementPainterList().iterator(); iterator.hasNext();){
                ElementPainter elementPainter = iterator.next();
                for(Shape s : dijagramView.getSelectionModel()){
                    ///brisanje svih klasa
                    if(elementPainter instanceof InterclassPainter){
                        InterclassPainter painter = (InterclassPainter) elementPainter;
                        if (s.getBounds2D().intersectsLine(painter.getConnectionPoints().get(0).x, painter.getConnectionPoints().get(0).y,
                                painter.getConnectionPoints().get(1).x, painter.getConnectionPoints().get(1).y) && s.getBounds2D().intersectsLine(painter.getConnectionPoints().get(2).x,
                                painter.getConnectionPoints().get(2).y, painter.getConnectionPoints().get(3).x, painter.getConnectionPoints().get(3).y)) {
                            Dijagram d = (Dijagram) painter.getElement().getParent();
                            if (item != null) {
                                for (int i = 0; i < item.getChildCount(); i++) {
                                    if (item.getChildAt(i) != null) {
                                        ClassyTreeItem tn = (ClassyTreeItem) item.getChildAt(i);
                                        ClassyNode cn = ((ClassyTreeItem) tn).getClassyNode();
                                        if (cn instanceof Interclass) {
                                            Interclass in = (Interclass)cn;
                                            if(in.poredjenje((Interclass) painter.getElement())) {
                                                item.remove(i);
                                                ClassyTreeImplementation classyTreeImplementation = (ClassyTreeImplementation) MainFrame.getInstance().getClassyTree();
                                                SwingUtilities.updateComponentTreeUI(classyTreeImplementation.getTreeView());
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            d.removeChild(painter.getElement());
                            iterator.remove();
                            break;
                        }

                    }
                    ///brisanje svih veza
                    else if(elementPainter instanceof ConnectionPainter){
                        ConnectionPainter painter = (ConnectionPainter) elementPainter;
                        if (painter.getPoint1() != null && painter.getPoint2() != null && s.getBounds2D().intersectsLine(painter.getPoint1().x, painter.getPoint1().y, painter.getPoint2().x, painter.getPoint2().y)) {
                            Dijagram d = (Dijagram) painter.getElement().getParent();
                            if (item != null) {
                                for (int i = 0; i < item.getChildCount(); i++) {
                                    if (item.getChildAt(i) != null) {
                                        ClassyTreeItem tn = (ClassyTreeItem) item.getChildAt(i);
                                        ClassyNode cn = ((ClassyTreeItem) tn).getClassyNode();
                                        if (cn instanceof Connection) {
                                            Connection cnn = (Connection)cn;
                                            if(cnn.poredjenje((Connection) painter.getElement())) {
                                                item.remove(i);
                                                ClassyTreeImplementation classyTreeImplementation = (ClassyTreeImplementation) MainFrame.getInstance().getClassyTree();
                                                SwingUtilities.updateComponentTreeUI(classyTreeImplementation.getTreeView());
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            d.removeChild(painter.getElement());
                            iterator.remove();
                            break;
                        }
                    }
                }
            }
            dijagramView.getSelectionModel().clear();
        }
        else {
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
        }
        ///sredjivanje modela i paintera
        List<ElementPainter> novaPainterLista = new ArrayList<>();
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
    public void misOtpusten(int x, int y, DijagramView dijagramView) {

    }

    @Override
    public void misPrivucen(int x, int y, DijagramView dijagramView) {

    }

    @Override
    public void misDragged(int x, int y, DijagramView dijagramView) {

    }

    @Override
    public void wheelMove(int x, int y, DijagramView dijagramView) {

    }
}
