package raf.dsw.classycraft.app.commands.implementation;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.commands.AbstractCommand;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;

import java.awt.*;

public class SingleMoveCommand extends AbstractCommand {

    private DijagramView dijagramView;
    private Point oldPoint;
    private Point newPoint;
    private Interclass mojInterclass;
    private Rectangle mojRect;

    public SingleMoveCommand(DijagramView dijagramView, int x, int y, Point oldPoint){
        this.dijagramView = dijagramView;
        this.oldPoint = oldPoint;
        this.newPoint = new Point(x, y);
    }

    @Override
    public void doCommand() {
        int brojac = 0;
        InterclassPainter mojPainter = null;
        for (ElementPainter ep : dijagramView.getElementPainterList()) {
            if (ep instanceof InterclassPainter) {
                InterclassPainter ip = (InterclassPainter) ep;
                if (ip.elementAt(oldPoint)) {
                    mojPainter = ip;
                    //nove koordinate
                    mojInterclass = (Interclass) mojPainter.getElement();
                    mojInterclass.setPosition(newPoint);
                }
            }
        }
        if (mojPainter != null) {
            //provera da li se nove koordinate preklapaju sa drugim elementima na dijagramu
            mojRect = new Rectangle(mojInterclass.getPosition().x - mojPainter.getWidth() / 2 - 10, mojInterclass.getPosition().y - mojPainter.getHeightUkupno() / 2 - 5, mojPainter.getWidth() + 12, mojPainter.getHeightUkupno() + 12);
            for (ElementPainter ep : dijagramView.getElementPainterList()) {
                if (ep instanceof InterclassPainter) {
                    InterclassPainter ip = (InterclassPainter) ep;
                    Interclass ic = (Interclass) ip.getElement();
                    Rectangle rect = new Rectangle(ic.getPosition().x - ip.getWidth() / 2 - 10, ic.getPosition().y - 5 - ip.getHeightUkupno() / 2, ip.getWidth() + 12, ip.getHeightUkupno() + 12);
                    if (rect.intersects(mojRect)) brojac++;
                }
            }
            if (brojac > 1) {//vracanje starih koordinata
                mojInterclass.setPosition(oldPoint);
                newPoint  = oldPoint;
            }
        }
        dijagramView.repaint();
    }

    @Override
    public void undoCommand() {
        InterclassPainter mojPainter;
        for (ElementPainter ep : dijagramView.getElementPainterList()) {
            if (ep instanceof InterclassPainter) {
                InterclassPainter ip = (InterclassPainter) ep;
                if (ip.elementAt(newPoint)) {
                    mojPainter = ip;
                    mojInterclass = (Interclass) mojPainter.getElement();
                    mojInterclass.setPosition(oldPoint);
                    break;
                }
            }
        }
        dijagramView.repaint();
    }
}
