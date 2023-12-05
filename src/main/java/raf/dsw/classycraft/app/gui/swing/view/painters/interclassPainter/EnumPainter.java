package raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.DijagramElement;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.EnumM;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Vidljivost;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;

import java.awt.*;

public class EnumPainter extends InterclassPainter {
    protected Shape shape;

    public EnumPainter(DijagramElement element) {
        super(element);

        /*
        ///test primeri
        EnumM e = (EnumM) element;
        e.getListEnuma().add("test1");
        e.getListEnuma().add("test3");
        e.getListEnuma().add("test77");
        e.getListEnuma().add("tesfuiedghcgweodc");
        */

    }

    @Override
    public void draw(Graphics2D g) {
        g.setPaint(Color.BLACK);
        g.setStroke(new BasicStroke(this.element.getStroke()));
        EnumM e = (EnumM) this.element;

        //odredjivanje sirine i visine
        width = maxDuzina(g);
        int rowHight = g.getFontMetrics().getHeight();
        heightUkupno = rowHight * e.getListEnuma().size() + rowHight;

        shape = new Rectangle(e.getPosition().x - width/2 - 5, e.getPosition().y - heightUkupno/2, width+10, heightUkupno+10);
        g.draw(shape);
        g.drawString("E  " + e.getNaziv(), e.getPosition().x - width/2, e.getPosition().y - heightUkupno/2 + rowHight);

        //cratnje enuma
        int brojac = 2;
        for(String s : e.getListEnuma()){
            g.drawString(s.toUpperCase(), e.getPosition().x - width/2, e.getPosition().y - heightUkupno/2 + brojac * rowHight);
            brojac++;
        }
        setConnectionPoints();
    }

    public int maxDuzina(Graphics2D g){
        int max = 0;
        int width = 0;

        EnumM e = (EnumM) this.element;
        for(String s : e.getListEnuma()){
            width = g.getFontMetrics().stringWidth(s.toUpperCase());
            if (width > max) max = width;
        }
        String string = "E  " + e.getNaziv();
        width = g.getFontMetrics().stringWidth(string);
        if (width > max) max = width;

        return max;
    }

    public void setConnectionPoints(){
        Interclass i = (Interclass)element;
        int width2 = width + 10;
        int height2 = heightUkupno + 5;
        connectionPoints.add(new Point(i.getPosition().x, i.getPosition().y - height2/2));
        connectionPoints.add(new Point(i.getPosition().x, i.getPosition().y + height2/2 + 8));
        connectionPoints.add(new Point(i.getPosition().x-width2/2, i.getPosition().y));
        connectionPoints.add(new Point(i.getPosition().x+width2/2, i.getPosition().y));
    }

    @Override
    public boolean elementAt(Point point) {
        return this.getShape().contains(point);
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }
}
