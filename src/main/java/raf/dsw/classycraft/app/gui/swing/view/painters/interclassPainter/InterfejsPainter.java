package raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.DijagramElement;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Interfejs;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;

import java.awt.*;

public class InterfejsPainter extends InterclassPainter {
    protected Shape shape;

    public InterfejsPainter(DijagramElement element) {
        super(element);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setPaint(Color.GREEN);
        g.setStroke(new BasicStroke(this.element.getStroke()));
        Interfejs i = (Interfejs) this.element;
        g.drawRect(i.getPosition().x, i.getPosition().y, 100,100);
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
