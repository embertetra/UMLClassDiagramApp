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
        g.setStroke(new BasicStroke(10));
        Klasa k = (Klasa) this.element;
        g.drawRect(k.getPosition().x, k.getPosition().y, 100,100);

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
