package raf.dsw.classycraft.app.commands.implementation;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.commands.AbstractCommand;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class MultipleMoveCommand extends AbstractCommand {

    private DijagramView dijagramView;
    private List<InterclassPainter> shapes;
    private List<Point> oldPoints;
    private List<Shape> novaListaSelekcije;
    private int x;
    private int y;

    public MultipleMoveCommand(DijagramView dijagramView, List<InterclassPainter> shapes, List<Point> oldPoints, int x, int y){
        this.dijagramView = dijagramView;
        this.shapes = shapes;
        this.oldPoints = oldPoints;
        this.x = x;
        this.y = y;
        novaListaSelekcije = new ArrayList<>();
    }

    @Override
    public void doCommand() {
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
        setujNoveSelekcije();
        for(InterclassPainter ip : shapes){
            Interclass ic = (Interclass) ip.getElement();
            ip.setxDragOffset(x-ic.getPosition().x);
            ip.setyDragOffset(y-ic.getPosition().y);
        }
        dijagramView.repaint();
    }

    @Override
    public void undoCommand() {
        int index = 0;
        for(InterclassPainter ip2 : shapes){
            Interclass i = (Interclass) ip2.getElement();
            i.setPosition(oldPoints.get(index));
            index++;
        }
        setujNoveSelekcije();
    }

    public void setujNoveSelekcije(){
        novaListaSelekcije.clear();
        for (InterclassPainter ip : shapes) {
            Interclass ic = (Interclass) ip.getElement();
            Shape s = new Rectangle(ic.getPosition().x - ip.getWidth() / 2 - 10, ic.getPosition().y - ip.getHeightUkupno() / 2 - 5,
                    ip.getWidth() + 20, ip.getHeightUkupno() + 20);
            novaListaSelekcije.add(s);
            dijagramView.setSelectionModel(novaListaSelekcije);
        }
    }
}
