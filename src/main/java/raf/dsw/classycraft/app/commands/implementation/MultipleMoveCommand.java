package raf.dsw.classycraft.app.commands.implementation;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.commands.AbstractCommand;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
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

        List<Shape> novaListaSelekcija = new ArrayList<>();

        for (InterclassPainter ip : moveSeletionRedo) {
            Interclass ic = (Interclass) ip.getElement();
            Shape s = new Rectangle(ic.getPosition().x - ip.getWidth() / 2 - 10, ic.getPosition().y - ip.getHeightUkupno() / 2 - 5,
                    ip.getWidth() + 20, ip.getHeightUkupno() + 20);
            novaListaSelekcija.add(s);

        }

        dijagramView.setSelectionModel(novaListaSelekcija);

        selectionRedo.clear();
        moveSeletionRedo.clear();
        dijagramView.repaint();
    }

    @Override
    public void undoCommand() {
        dijagramView.getSelectionModel().clear();
        selectionRedo = selectionUndo;
        moveSeletionRedo = moveSelectionUndo;
        oldPointsRedo = oldPointsUndo;
        int index = 0;
        for (InterclassPainter ip : moveSeletionRedo) {
            Interclass i = (Interclass) ip.getElement();
            i.setPosition(oldPointsRedo.get(index++), dijagramView);
        }
        List<Shape> novaListaSelekcija = new ArrayList<>();
        for (InterclassPainter ip : moveSeletionRedo) {
            Interclass ic = (Interclass) ip.getElement();
            Shape s = new Rectangle(ic.getPosition().x - ip.getWidth() / 2 - 10, ic.getPosition().y - ip.getHeightUkupno() / 2 - 5,
                    ip.getWidth() + 20, ip.getHeightUkupno() + 20);
            novaListaSelekcija.add(s);

        }
        dijagramView.setSelectionModel(novaListaSelekcija);

        dijagramView.repaint();

    }
}
