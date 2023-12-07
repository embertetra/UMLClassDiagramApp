package raf.dsw.classycraft.app.stateSablon.states;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Connection;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.painters.ConnectionPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;
import raf.dsw.classycraft.app.stateSablon.State;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Move implements State {
    private Point oldPoint;
    private List<InterclassPainter> shapes;
    private List<Shape> novaListaSelekcije;
    private List<Point> oldPoints;
    private int tmp;
    public Move() {
        shapes = new ArrayList<>();
        novaListaSelekcije = new ArrayList<>();
        oldPoints = new ArrayList<>();
        tmp = 0;
    }
    @Override
    public void misKliknut(int x, int y, DijagramView dijagramView) {
        dijagramView.setSelection(null);
        dijagramView.getSelectionModel().clear();
        dijagramView.repaint();
    }

    @Override
    public void misOtpusten(int x, int y, DijagramView dijagramView) {


        dijagramView.removeTranslation();
        dijagramView.getSelectionModel().clear();
        novaListaSelekcije.clear();
        ///presek multiselekcija
        if(tmp == 1) {
            for(InterclassPainter ip : shapes) {
                Interclass ic = (Interclass) ip.getElement();
                Rectangle myRect = new Rectangle(ic.getPosition().x - ip.getWidth() / 2 - 10, ic.getPosition().y - ip.getHeightUkupno() / 2 - 5,
                        ip.getWidth() + 12, ip.getHeightUkupno() + 12);
                int brojac = 0;
                int index = 0;
                for (ElementPainter ep : dijagramView.getElementPainterList()) {
                    if(ep instanceof InterclassPainter) {
                        InterclassPainter classP = (InterclassPainter) ep;
                        Interclass klasa = (Interclass) classP.getElement();
                        Rectangle rect = new Rectangle(klasa.getPosition().x - classP.getWidth() / 2 - 10, klasa.getPosition().y - classP.getHeightUkupno() / 2 - 5,
                                classP.getWidth() + 12, classP.getHeightUkupno() + 12);
                        if (rect.intersects(myRect)) brojac++;
                    }
                }
                if (brojac > 1) {

                    for(InterclassPainter ip2 : shapes){
                        Interclass i = (Interclass) ip2.getElement();
                        i.setPosition(oldPoints.get(index));
                        index++;
                    }
                    break;
                }
            }
            ///settovanje novih selekcija
            for (InterclassPainter ip : shapes) {
                Interclass ic = (Interclass) ip.getElement();
                Shape s = new Rectangle(ic.getPosition().x - ip.getWidth() / 2 - 10, ic.getPosition().y - ip.getHeightUkupno() / 2 - 5,
                        ip.getWidth() + 20, ip.getHeightUkupno() + 20);
                novaListaSelekcije.add(s);

            }
            dijagramView.setSelectionModel(novaListaSelekcije);
        }

        ///odredjivanje preseka u vracanje na stare koordinate ako se sece - 1 interklasa
        if(tmp != 1) {
            int brojac = 0;
            InterclassPainter mojPainter = null;
            for (ElementPainter ep : dijagramView.getElementPainterList()) {
                if (ep instanceof InterclassPainter) {
                    InterclassPainter ip = (InterclassPainter) ep;
                    if (ip.elementAt(new Point(x, y)))
                        mojPainter = ip;
                }
            }
            if (mojPainter != null) {
                Interclass mojaKlasa = (Interclass) mojPainter.getElement();
                Rectangle mojRect = new Rectangle(mojaKlasa.getPosition().x - mojPainter.getWidth() / 2 - 10, mojaKlasa.getPosition().y - mojPainter.getHeightUkupno() / 2 - 5, mojPainter.getWidth() + 12, mojPainter.getHeightUkupno() + 12);
                for (ElementPainter ep : dijagramView.getElementPainterList()) {
                    if (ep instanceof InterclassPainter) {
                        InterclassPainter ip = (InterclassPainter) ep;
                        Interclass ic = (Interclass) ip.getElement();
                        Rectangle rect = new Rectangle(ic.getPosition().x - ip.getWidth() / 2 - 10, ic.getPosition().y - 5 - ip.getHeightUkupno() / 2, ip.getWidth() + 12, ip.getHeightUkupno() + 12);
                        if (rect.intersects(mojRect)) brojac++;
                    }
                }
            }
            if (brojac > 1) {
                ((Interclass) dijagramView.getFlag1().getElement()).setPosition(oldPoint);
            }
        }
        dijagramView.setFlag1(null);
        tmp = 0;
        dijagramView.repaint();
    }

    @Override
    public void misPrivucen(int x, int y, DijagramView dijagramView) {
        dijagramView.setSelection(null);
        dijagramView.setTranslation();
        dijagramView.setStartPoint(new Point(x, y));
        int flag = 0;

        ///move multiselekcije
        shapes.clear();
        oldPoints.clear();
        if(dijagramView.getSelectionModel() != null && dijagramView.getSelectionModel().size() > 0){
            for(ElementPainter ep : dijagramView.getElementPainterList()){
                if(ep instanceof InterclassPainter){
                    InterclassPainter ip = (InterclassPainter) ep;
                    Interclass ic = (Interclass) ip.getElement();
                    for(Shape s : dijagramView.getSelectionModel()){
                        if(s.intersects(new Rectangle(ic.getPosition().x - ip.getWidth()/2, ic.getPosition().y-ip.getHeightUkupno()/2, ip.getWidth(),ip.getHeightUkupno()))) {
                            shapes.add(ip);
                            oldPoints.add(new Point(ic.getPosition().x, ic.getPosition().y));
                        }
                    }
                }
            }
            if(shapes.size() > 0){
                dijagramView.setMoveSelections(shapes);
                flag = 2;
                dijagramView.setSelectionModel(new ArrayList<>());
                tmp = 1;
            }
        }
        //provera da li se nalazi na nekoj interklasi mis
        if(flag == 0) {
            for (ElementPainter ep : dijagramView.getElementPainterList()) {
                if (ep instanceof InterclassPainter) {
                    if (ep.elementAt(new Point(x, y))) {
                        flag = 1;
                        dijagramView.setFlag1((InterclassPainter) ep);
                    }
                }
            }
        }
        ///pomera se samo element na koji je kliknut
        if (flag == 1) {
            for (ElementPainter ep : dijagramView.getElementPainterList()) {
                if (ep instanceof InterclassPainter) {
                    InterclassPainter ip = (InterclassPainter) ep;
                    Interclass ic = (Interclass) ip.getElement();
                    if (ip.elementAt(new Point(x, y))) {
                        ip.setxDragOffset(x - ic.getPosition().x);
                        ip.setyDragOffset(y - ic.getPosition().y);
                        oldPoint = new Point(ic.getPosition().x, ic.getPosition().y);
                        dijagramView.repaint();//
                        return;
                    }
                }
            }
        }
        ///multiselekcija move
        else if(flag == 2){
            for(InterclassPainter ip : shapes){
                Interclass ic = (Interclass) ip.getElement();
                ip.setxDragOffset(x-ic.getPosition().x);
                ip.setyDragOffset(y-ic.getPosition().y);
            }
            dijagramView.repaint();
        }
        ///pomeranje radne povrsine
        else {
            for (ElementPainter ep : dijagramView.getElementPainterList()) {
                if (ep instanceof InterclassPainter) {
                    InterclassPainter ip = (InterclassPainter) ep;
                    Interclass ic = (Interclass) ip.getElement();
                    ip.setxDragOffset(dijagramView.getStartPoint().x - ic.getPosition().x);
                    ip.setyDragOffset(dijagramView.getStartPoint().y - ic.getPosition().y);
                }
            }
            for (ElementPainter ep : dijagramView.getElementPainterList()) {
                if (ep instanceof ConnectionPainter) {
                    ConnectionPainter cp = (ConnectionPainter) ep;
                    Interclass from = ((Connection) ep.getElement()).getFrom();
                    Interclass to = ((Connection) ep.getElement()).getTo();
                    if(dijagramView.getStartPoint() != null && from != null && to != null) {
                        cp.setDragOffset1(new Point(dijagramView.getStartPoint().x - from.getPosition().x,
                                dijagramView.getStartPoint().y - from.getPosition().y));
                        cp.setDragOffset2(new Point(dijagramView.getStartPoint().x - to.getPosition().x,
                                dijagramView.getStartPoint().y - to.getPosition().y));
                    }
                }
            }
            dijagramView.repaint();
        }
    }
}
