package raf.dsw.classycraft.app.commands.implementation;

import javafx.util.Pair;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Connection;
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
import java.util.Iterator;
import java.util.List;

public class AddConnectionCommand extends AbstractCommand {

    private Connection connection;
    private DijagramView dijagramView;
    private int x;
    private int y;

    public AddConnectionCommand(Connection connection, DijagramView dijagramView, int x, int y){
        this.connection = connection;
        this.dijagramView = dijagramView;
        this.x = x;
        this.y = y;
    }

    @Override
    public void doCommand() {

        Dijagram d = (Dijagram)dijagramView.getClassyNode();
        d.addSubscriber(dijagramView);

        ///odredjivanje dijagrama u stablu
        ClassyTreeItem item = null;
        ClassyTreeItem selected = MainFrame.getInstance().getPackageView().getClassyTreeItem();
        for(int i=0; i<selected.getChildCount(); i++){
            ClassyTreeItem c = (ClassyTreeItem)selected.getChildAt(i);
            ClassyNode cn = c.getClassyNode();
            if(cn.getName().equals(dijagramView.getClassyNode().getName()))
                item = c;
        }

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
        dijagramView.setLine(new Pair<>(new Point(-1,-1), new Point(0,0)));
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

        for (int j=dijagramView.getElementPainterList().size()-1; j>=0; j--){
            ElementPainter elementPainter = dijagramView.getElementPainterList().get(j);
            ///brisanje pojedinacne veze
            if (elementPainter instanceof ConnectionPainter) {
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
                                    break;
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }

        ///sredjivanje modela i paintera
        List<ElementPainter> novaPainterLista = new ArrayList<>();
        List<ClassyNode> novaLista = new ArrayList<>();
        for (int i = 0; i < item.getChildCount(); i++) {
            ClassyTreeItem cti = (ClassyTreeItem) item.getChildAt(i);
            novaLista.add(cti.getClassyNode());
            if (cti.getClassyNode() instanceof Klasa)
                novaPainterLista.add(new KlasaPainter((Klasa) cti.getClassyNode()));
            else if (cti.getClassyNode() instanceof Interfejs)
                novaPainterLista.add(new InterfejsPainter((Interfejs) cti.getClassyNode()));
            else if (cti.getClassyNode() instanceof EnumM)
                novaPainterLista.add(new EnumPainter((EnumM) cti.getClassyNode()));
            else if (cti.getClassyNode() instanceof Agregacija)
                novaPainterLista.add(new AgregacijaPainter((Agregacija) cti.getClassyNode()));
            else if (cti.getClassyNode() instanceof Kompozicija)
                novaPainterLista.add(new KompozicijaPainter((Kompozicija) cti.getClassyNode()));
            else if (cti.getClassyNode() instanceof Generalizacija)
                novaPainterLista.add(new GeneralizacijaPainter((Generalizacija) cti.getClassyNode()));
            else if (cti.getClassyNode() instanceof Zavisnost)
                novaPainterLista.add(new ZavisnostPainter((Zavisnost) cti.getClassyNode()));
        }
        Dijagram d = (Dijagram) item.getClassyNode();
        d.setChildren(novaLista);
        dijagramView.getElementPainterList().clear();
        dijagramView.setElementPainterList(novaPainterLista);
        dijagramView.repaint();
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
