package raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.DijagramElement;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Klasa;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;

import java.awt.*;

public class KlasaPainter extends InterclassPainter {
    protected Shape shape;
    public KlasaPainter(DijagramElement element) {
        super(element);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setPaint(Color.BLUE);
        g.draw(shape);
        Klasa k = (Klasa) this.element;
        g.drawString(k.getNaziv(), (int) (k.getPosition().getX()+10), (int) (k.getPosition().getY()+10));


        /*
        g.setStroke(dijagramElement.getStroke());
        g.drawRect(); // (x, y) - gore levo, with, hight
         */
    }

    @Override
    public boolean elementAt(DijagramElement dijagramElement, Point point) {
        return this.getShape().contains(point);
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }
}
