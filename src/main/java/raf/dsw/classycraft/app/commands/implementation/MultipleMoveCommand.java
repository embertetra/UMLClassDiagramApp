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
    private List<Point> oldPointsUndo;
    private List<Point> oldPointsRedo;
    private List<Shape> selectionUndo;
    private List<Shape> selectionRedo;
    private List<InterclassPainter> moveSelectionUndo;
    private List<InterclassPainter> moveSeletionRedo;
    private int x;
    private int y;

    public MultipleMoveCommand(DijagramView dijagramView, List<Point> oldPoints, int x, int y){
        this.dijagramView = dijagramView;
        this.oldPointsRedo = oldPoints;
        this.x = x;
        this.y = y;
        selectionRedo = new ArrayList<>(dijagramView.getSelectionModel());
        moveSeletionRedo = new ArrayList<>(dijagramView.getMoveSelections());
    }

    @Override
    public void doCommand() {
        dijagramView.getSelectionModel().clear();
        selectionUndo = new ArrayList<>(selectionRedo);
        moveSelectionUndo = new ArrayList<>(moveSeletionRedo);
        oldPointsUndo = new ArrayList<>(oldPointsRedo);

        int diffX = x - dijagramView.getStartPoint().x;
        int diffY = y - dijagramView.getStartPoint().y;

        //nove koordinate
        for (InterclassPainter ip : moveSeletionRedo) {
            Interclass ic = (Interclass) ip.getElement();
            ic.setPosition(new Point(dijagramView.getStartPoint().x + diffX - ip.getxDragOffset(), dijagramView.getStartPoint().y + diffY - ip.getyDragOffset()), dijagramView);
        }
        //provera da li se nove kordinate preklapaju sa drugim elementima na dijagramu
        for(InterclassPainter ip : moveSeletionRedo) {
            Interclass ic = (Interclass) ip.getElement();
            Rectangle myRect = new Rectangle(ic.getPosition().x - ip.getWidth() / 2 - 10, ic.getPosition().y - ip.getHeightUkupno() / 2 - 5,
                    ip.getWidth() + 12, ip.getHeightUkupno() + 12);
            int brojac = 0;
            for (ElementPainter ep : dijagramView.getElementPainterList()) {
                if(ep instanceof InterclassPainter) {
                    InterclassPainter classP = (InterclassPainter) ep;
                    Interclass klasa = (Interclass) classP.getElement();
                    Rectangle rect = new Rectangle(klasa.getPosition().x - classP.getWidth() / 2 - 10, klasa.getPosition().y - classP.getHeightUkupno() / 2 - 5,
                            classP.getWidth() + 12, classP.getHeightUkupno() + 12);
                    if (rect.intersects(myRect)) brojac++;
                }
            }
            if (brojac > 1) {//vracanje starih koordinata
                int ind = 0;
                for(InterclassPainter ip2 : moveSeletionRedo){
                    Interclass i = (Interclass) ip2.getElement();
                    i.setPosition(oldPointsRedo.get(ind++), dijagramView);
                }
            }
        }
        selectionRedo.clear();
        moveSeletionRedo.clear();
        dijagramView.repaint();
    }

    @Override
    public void undoCommand() {
        selectionRedo = selectionUndo;
        moveSeletionRedo = moveSelectionUndo;
        oldPointsRedo = oldPointsUndo;
        int index = 0;
        for (InterclassPainter ip : moveSeletionRedo) {
            Interclass i = (Interclass) ip.getElement();
            i.setPosition(oldPointsRedo.get(index++), dijagramView);
        }
        dijagramView.repaint();
    }
}
