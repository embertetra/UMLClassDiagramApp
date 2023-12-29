package raf.dsw.classycraft.app.commands.implementation;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Connection;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.*;
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

import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;

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
    }

    @Override
    public void undoCommand() {

    }
}
