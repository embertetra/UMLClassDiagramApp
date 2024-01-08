package raf.dsw.classycraft.app.stateSablon.states;

import javafx.util.Pair;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.*;
import raf.dsw.classycraft.app.commands.AbstractCommand;
import raf.dsw.classycraft.app.commands.implementation.AddConnectionCommand;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;
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

        if(connection != null){
            AbstractCommand command = null;
            if(connection.equals("agregacija")){
            command = new AddConnectionCommand(a, dijagramView, x, y);
            }
            else if(connection.equals("kompozicija")){
            command = new AddConnectionCommand(k, dijagramView, x, y);
            }
            else if(connection.equals("generalizacija")){
            command = new AddConnectionCommand(g, dijagramView, x, y);
            }
            else if(connection.equals("zavisnost")){
            command = new AddConnectionCommand(z, dijagramView, x, y);
            }
            else if(connection.equals("asocijacija")){
            command = new AddConnectionCommand(as, dijagramView, x, y);
            }
            ((DijagramView)MainFrame.getInstance().getPackageView().getjTabbedPane().getSelectedComponent()).getCommandManager().addCommand(command);
        }
    }

    @Override
    public void misPrivucen(int x, int y, DijagramView dijagramView) {
        dijagramView.getSelectionModel().clear();
        dijagramView.setSelection(null);
        //dijagramView.setMML();
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

    @Override
    public void misDragged(int x, int y, DijagramView d) {
        if (d.getLine() != null && d.getLine().getKey() != null) {
            d.setLine(new Pair<>(d.getLine().getKey(), new Point(x, y)));
            d.repaint();
        }
    }

    @Override
    public void wheelMove(int x, int y, DijagramView dijagramView) {

    }

    public void setConnection(String connection) {
        this.connection = connection;
    }
}
