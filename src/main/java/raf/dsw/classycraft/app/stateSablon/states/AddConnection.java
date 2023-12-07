package raf.dsw.classycraft.app.stateSablon.states;

import javafx.util.Pair;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.*;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter.AgregacijaPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter.AsocijacijaPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter.GeneralizacijaPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter.KompozicijaPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter.ZavisnostPainter;
import raf.dsw.classycraft.app.stateSablon.State;

import java.awt.*;

public class AddConnection implements State {

    private String connection;
    private Agregacija a;
    private Kompozicija k;
    private Generalizacija g;
    private Zavisnost z;
    private Asocijacija as;
    @Override
    public void misKliknut(int x, int y, DijagramView dijagramView) {
        dijagramView.getSelectionModel().clear();
        dijagramView.setSelection(null);
    }

    @Override
    public void misOtpusten(int x, int y, DijagramView dijagramView) {

        dijagramView.removeMML();
        Dijagram d = (Dijagram)dijagramView.getClassyNode();
        d.addSubscriber(dijagramView);

        ClassyTreeItem item = null;
        ClassyTreeItem selected = MainFrame.getInstance().getClassyTree().getSelectedNode();
        for(int i=0; i<selected.getChildCount(); i++){
            ClassyTreeItem c = (ClassyTreeItem)selected.getChildAt(i);
            ClassyNode cn = c.getClassyNode();
            if(cn.getName().equals(dijagramView.getClassyNode().getName()))
                item = c;
        }

        if(connection.equals("agregacija")){
            for(ElementPainter e : dijagramView.getElementPainterList()){
                if(e instanceof InterclassPainter) {
                    if (e.elementAt(new Point(x, y))){
                        a.setTo((Interclass) e.getElement());
                    }
                }
            }
            if(a.getTo() != null){
                AgregacijaPainter ap = new AgregacijaPainter(a);
                dijagramView.getElementPainterList().add(ap);
                d.addChild(a);
                MainFrame.getInstance().getClassyTree().addChild(item, a);
            }
        }
        else if(connection.equals("kompozicija")){
            for(ElementPainter e : dijagramView.getElementPainterList()){
                if(e instanceof InterclassPainter) {
                    if (e.elementAt(new Point(x, y))){
                        k.setTo((Interclass) e.getElement());
                    }
                }
            }
            if(k.getTo() != null){
                KompozicijaPainter kp = new KompozicijaPainter(k);
                dijagramView.getElementPainterList().add(kp);
                d.addChild(k);
                MainFrame.getInstance().getClassyTree().addChild(item, k);
            }
        }
        else if(connection.equals("generalizacija")){
            for(ElementPainter e : dijagramView.getElementPainterList()){
                if(e instanceof InterclassPainter) {
                    if (e.elementAt(new Point(x, y))){
                        g.setTo((Interclass) e.getElement());
                    }
                }
            }
            if(g.getTo() != null){
                GeneralizacijaPainter gp = new GeneralizacijaPainter(g);
                dijagramView.getElementPainterList().add(gp);
                d.addChild(g);
                MainFrame.getInstance().getClassyTree().addChild(item, g);
            }
        }
        else if(connection.equals("zavisnost")){
            for(ElementPainter e : dijagramView.getElementPainterList()){
                if(e instanceof InterclassPainter)
                    if(e.elementAt(new Point(x, y)))
                        z.setTo((Interclass) e.getElement());
            }
            if(z.getTo() != null){
                ZavisnostPainter zp = new ZavisnostPainter(z);
                dijagramView.getElementPainterList().add(zp);
                d.addChild(z);
                MainFrame.getInstance().getClassyTree().addChild(item, z);
            }
        }
        else if(connection.equals("asocijacija")){

            for(ElementPainter e : dijagramView.getElementPainterList()){
                if(e instanceof InterclassPainter) {
                    if (e.elementAt(new Point(x, y))){
                        as.setTo((Interclass) e.getElement());
                    }
                }
            }
            if(as.getTo() != null){
                AsocijacijaPainter asp = new AsocijacijaPainter(as);
                dijagramView.getElementPainterList().add(asp);
                d.addChild(as);
                MainFrame.getInstance().getClassyTree().addChild(item, as);
            }

        }

        dijagramView.setLine(new Pair<>(new Point(-1,-1), new Point(0,0)));
        dijagramView.repaint();
    }

    @Override
    public void misPrivucen(int x, int y, DijagramView dijagramView) {
        dijagramView.getSelectionModel().clear();
        dijagramView.setSelection(null);
        dijagramView.setMML();
        if(connection != null) {
            if (connection.equals("agregacija")) {
                a = new Agregacija("Connection", dijagramView.getClassyNode(), 2, null, null);
                for (ElementPainter e : dijagramView.getElementPainterList()) {
                    if (e instanceof InterclassPainter) {
                        if (e.elementAt(new Point(x, y))) {
                            a.setFrom((Interclass) e.getElement());
                            dijagramView.setLine(new Pair<>(new Point(x, y), null));
                        }
                    }
                }
            } else if (connection.equals("kompozicija")) {

                k = new Kompozicija("Connection", dijagramView.getClassyNode(), 2, null, null);
                for (ElementPainter e : dijagramView.getElementPainterList()) {
                    if (e instanceof InterclassPainter) {
                        if (e.elementAt(new Point(x, y))) {
                            k.setFrom((Interclass) e.getElement());
                            dijagramView.setLine(new Pair<>(new Point(x, y), null));
                        }
                    }
                }

            } else if (connection.equals("generalizacija")) {

                g = new Generalizacija("Connection", dijagramView.getClassyNode(), 2, null, null);
                for (ElementPainter e : dijagramView.getElementPainterList()) {
                    if (e instanceof InterclassPainter) {
                        if (e.elementAt(new Point(x, y))) {
                            g.setFrom((Interclass) e.getElement());
                            dijagramView.setLine(new Pair<>(new Point(x, y), null));
                        }
                    }
                }

            } else if (connection.equals("zavisnost")) {
                  z = new Zavisnost("Connection", dijagramView.getClassyNode(), 2, null, null);
                  for(ElementPainter e : dijagramView.getElementPainterList()){
                    if (e instanceof InterclassPainter)
                        if (e.elementAt(new Point(x, y))){
                            z.setFrom((Interclass) e.getElement());
                            dijagramView.setLine(new Pair<>(new Point(x, y), null));
                        }
                }
            } else if(connection.equals("asocijacija")){
                as = new Asocijacija("Connection", dijagramView.getClassyNode(), 2, null, null);
                for (ElementPainter e : dijagramView.getElementPainterList()) {
                    if (e instanceof InterclassPainter) {
                        if (e.elementAt(new Point(x, y))) {
                            as.setFrom((Interclass) e.getElement());
                            dijagramView.setLine(new Pair<>(new Point(x, y), null));
                        }
                    }
                }

            }
        }

    }

    public void setConnection(String connection) {
        this.connection = connection;
    }
}
