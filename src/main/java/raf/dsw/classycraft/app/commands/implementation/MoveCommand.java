package raf.dsw.classycraft.app.commands.implementation;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.commands.AbstractCommand;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;

import java.awt.*;
import java.util.List;

public class MoveCommand extends AbstractCommand {

    private int tmp;
    private List<Shape> novaListaSelekcije;
    private List<Point> oldPoints;
    private Point oldPoint;
    private List<InterclassPainter> shapes;
    private DijagramView dijagramView;
    private int x;
    private int y;

    public MoveCommand(int tmp, List<Shape> novaListaSelekcije, List<Point> oldPoints, Point oldPoint, List<InterclassPainter> shapes, DijagramView dijagramView, int x, int y){
        this.tmp = tmp;
        this.novaListaSelekcije = novaListaSelekcije;
        this.oldPoints = oldPoints;
        this.oldPoint = oldPoint;
        this.shapes = shapes;
        this.dijagramView = dijagramView;
        this.x = x;
        this.y = y;
    }

    @Override
    public void doCommand() {
        int diffX = (int) (x - dijagramView.getStartPoint().x);
        int diffY = (int) (y - dijagramView.getStartPoint().y);

        //presek multiselekcija
        if(tmp == 1) {
            //dodavanje multiselekcije na novu poziciju
            if (dijagramView.getMoveSelections() != null && !dijagramView.getMoveSelections().isEmpty()) {
                for (InterclassPainter ip : dijagramView.getMoveSelections()) {
                    Interclass ic = (Interclass) ip.getElement();
                    ic.setPosition(new Point(dijagramView.getStartPoint().x + diffX - ip.getxDragOffset(),
                            dijagramView.getStartPoint().y + diffY - ip.getyDragOffset()));
                }
            }
            //ako postoji presek - vratiti stare koordinate
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
            ///setovanje novih selekcija
            dijagramView.getSelectionModel().clear();
            for (InterclassPainter ip : shapes) {
                Interclass ic = (Interclass) ip.getElement();
                Shape s = new Rectangle(ic.getPosition().x - ip.getWidth() / 2 - 10, ic.getPosition().y - ip.getHeightUkupno() / 2 - 5,
                        ip.getWidth() + 20, ip.getHeightUkupno() + 20);
                novaListaSelekcije.add(s);

            }
            dijagramView.setSelectionModel(novaListaSelekcije);
        }



        //odredjivanje preseka i vracanje na stare koordinate ako se sece - 1 interklasa
        else {
            /*
            for (ElementPainter ep : dijagramView.getElementPainterList()) {
                if (ep instanceof InterclassPainter) {
                    InterclassPainter ip = (InterclassPainter) ep;
                    Interclass ic = (Interclass) ip.getElement();
                    if (ip == dijagramView.getFlag1()) {
                        ic.setPosition(new Point(dijagramView.getStartPoint().x + diffX - dijagramView.getxDragOffset(), dijagramView.getStartPoint().y + diffY - dijagramView.getyDragOffset()));
                        return;
                    }
                }
            }*/

            int brojac = 0;
            InterclassPainter mojPainter = null;
            Interclass ic = null;
            for (ElementPainter ep : dijagramView.getElementPainterList()) {
                if (ep instanceof InterclassPainter) {
                    InterclassPainter ip = (InterclassPainter) ep;
                    ic = (Interclass) ip.getElement();
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
                        ic = (Interclass) ip.getElement();
                        Rectangle rect = new Rectangle(ic.getPosition().x - ip.getWidth() / 2 - 10, ic.getPosition().y - 5 - ip.getHeightUkupno() / 2, ip.getWidth() + 12, ip.getHeightUkupno() + 12);
                        if (rect.intersects(mojRect)) brojac++;
                    }
                }
            }
            if (brojac > 1) {
                ((Interclass) dijagramView.getFlag1().getElement()).setPosition(oldPoint);
            }
            else {
                assert ic != null;
                ic.setPosition(new Point(dijagramView.getStartPoint().x + diffX - dijagramView.getxDragOffset(), dijagramView.getStartPoint().y + diffY - dijagramView.getyDragOffset()));
            }
        }
    }

    @Override
    public void undoCommand() {

        int index = 0;
        if(tmp == 1){
            for(InterclassPainter ip : shapes){
                Interclass i = (Interclass) ip.getElement();
                i.setPosition(oldPoints.get(index));
                index++;
            }
            dijagramView.getSelectionModel().clear();
            for (InterclassPainter ip : shapes) {
                Interclass ic = (Interclass) ip.getElement();
                Shape s = new Rectangle(ic.getPosition().x - ip.getWidth() / 2 - 10, ic.getPosition().y - ip.getHeightUkupno() / 2 - 5,
                        ip.getWidth() + 20, ip.getHeightUkupno() + 20);
                novaListaSelekcije.add(s);

            }
            dijagramView.setSelectionModel(novaListaSelekcije);
        }
        else {
            ((Interclass) dijagramView.getFlag1().getElement()).setPosition(oldPoint);
        }
    }
}
