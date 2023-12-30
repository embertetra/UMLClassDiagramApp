package raf.dsw.classycraft.app.commands.implementation;

import javafx.util.Pair;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Connection;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.DijagramElement;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.*;
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
import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter.*;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.EnumPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.InterfejsPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.KlasaPainter;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class SingleDeleteCommand extends AbstractCommand {

    private List<DijagramElement> list = new ArrayList<>();
    private List<Connection> veze = new ArrayList<>();
    private DijagramView dijagramView;
    private int x;
    private int y;
    private ClassyTreeItem item;

    public SingleDeleteCommand(int x, int y, DijagramView dijagramView, ClassyTreeItem item){
        this.dijagramView = dijagramView;
        this.x = x;
        this.y = y;
        this.item = item;
    }

    @Override
    public void doCommand() {
        Interclass selektovanaKlasa = null;//klasa na koju smo kliknuli
        ///brisanje pojedinacog elementa
        for (int j=dijagramView.getElementPainterList().size()-1; j>=0; j--) {
            ElementPainter elementPainter = dijagramView.getElementPainterList().get(j);
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
                        veze.add(cnn);
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
        for(int i=0; i<item.getChildCount(); i++){
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
        Dijagram d = (Dijagram) dijagramView.getClassyNode();

        for (DijagramElement dijagramElement : list) {
            if (dijagramElement instanceof Interclass) {
                Interclass interclass = (Interclass) dijagramElement;
                if (interclass instanceof Klasa) {
                    Klasa klasa = (Klasa) interclass;
                    klasa.addSubscriber(dijagramView);
                    KlasaPainter klasaPainter = new KlasaPainter(klasa);
                    dijagramView.getElementPainterList().add(klasaPainter);
                    d.addChild(klasa);///dodoavanje u model
                    MainFrame.getInstance().getClassyTree().addChild(item, klasa);///dodavanje u stablo
                } else if (interclass instanceof Interfejs) {
                    Interfejs interfejs = (Interfejs) interclass;
                    interfejs.addSubscriber(dijagramView);
                    InterfejsPainter interfejsPainter = new InterfejsPainter(interfejs);
                    dijagramView.getElementPainterList().add(interfejsPainter);
                    d.addChild(interfejs);
                    MainFrame.getInstance().getClassyTree().addChild(item, interfejs);
                } else if (interclass instanceof EnumM) {
                    EnumM enumM = (EnumM) interclass;
                    enumM.addSubscriber(dijagramView);
                    EnumPainter enumPainter = new EnumPainter(enumM);
                    dijagramView.getElementPainterList().add(enumPainter);
                    d.addChild(enumM);
                    MainFrame.getInstance().getClassyTree().addChild(item, enumM);
                }
            } else if (dijagramElement instanceof Connection) {
                Connection connection = (Connection) dijagramElement;
                makeConnection(connection, d, item);
            }
        }
        for(Connection c : veze)
            makeConnection(c, d, item);

        veze.clear();
        list.clear();
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

    public void makeConnection(Connection connection, Dijagram d, ClassyTreeItem item){
        if(connection instanceof Agregacija){
            for(ElementPainter e : dijagramView.getElementPainterList()){
                if(e instanceof InterclassPainter) {
                    if (e.elementAt(new Point(x, y))){
                        connection.setTo((Interclass) e.getElement());
                    }
                }
            }
            if(connection.getFrom()!= null && connection.getTo() != null && connection.getFrom() != connection.getTo()){
                AgregacijaPainter ap = new AgregacijaPainter(connection);
                dijagramView.getElementPainterList().add(ap);
                d.addChild(connection);
                MainFrame.getInstance().getClassyTree().addChild(item, connection);
            }
        }
        else if(connection instanceof Kompozicija){
            for(ElementPainter e : dijagramView.getElementPainterList()){
                if(e instanceof InterclassPainter) {
                    if (e.elementAt(new Point(x, y))){
                        connection.setTo((Interclass) e.getElement());
                    }
                }
            }
            if(connection.getFrom()!= null && connection.getTo() != null && connection.getFrom() != connection.getTo()){
                KompozicijaPainter kp = new KompozicijaPainter(connection);
                dijagramView.getElementPainterList().add(kp);
                d.addChild(connection);
                MainFrame.getInstance().getClassyTree().addChild(item, connection);
            }
        }
        else if(connection instanceof Generalizacija){
            for(ElementPainter e : dijagramView.getElementPainterList()){
                if(e instanceof InterclassPainter) {
                    if (e.elementAt(new Point(x, y))){
                        connection.setTo((Interclass) e.getElement());
                    }
                }
            }
            if(connection.getFrom()!= null && connection.getTo() != null && connection.getFrom() != connection.getTo()){
                GeneralizacijaPainter gp = new GeneralizacijaPainter(connection);
                dijagramView.getElementPainterList().add(gp);
                d.addChild(connection);
                MainFrame.getInstance().getClassyTree().addChild(item, connection);
            }
        }
        else if(connection instanceof Zavisnost){
            for(ElementPainter e : dijagramView.getElementPainterList()){
                if(e instanceof InterclassPainter)
                    if(e.elementAt(new Point(x, y)))
                        connection.setTo((Interclass) e.getElement());
            }
            if(connection.getFrom()!= null && connection.getTo() != null && connection.getFrom() != connection.getTo()){
                ZavisnostPainter zp = new ZavisnostPainter(connection);
                dijagramView.getElementPainterList().add(zp);
                d.addChild(connection);
                MainFrame.getInstance().getClassyTree().addChild(item, connection);
            }
        }
        else if(connection instanceof Asocijacija){
            for(ElementPainter e : dijagramView.getElementPainterList()){
                if(e instanceof InterclassPainter) {
                    if (e.elementAt(new Point(x, y))){
                        connection.setTo((Interclass) e.getElement());
                    }
                }
            }
            if(connection.getFrom()!= null && connection.getTo() != null && connection.getFrom() != connection.getTo()){
                AsocijacijaPainter asp = new AsocijacijaPainter(connection);
                dijagramView.getElementPainterList().add(asp);
                d.addChild(connection);
                MainFrame.getInstance().getClassyTree().addChild(item, connection);
            }
        }
        dijagramView.setLine(new Pair<>(new Point(-1, -1), new Point(0, 0)));
        dijagramView.repaint();
    }
}
