package raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter;

import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.ClassContent;
import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.Metode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.DijagramElement;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Interfejs;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Vidljivost;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;

import java.awt.*;

public class InterfejsPainter extends InterclassPainter {
    protected Shape shape;

    public InterfejsPainter(DijagramElement element) {
        super(element);

        /*
        ///test primeri
        Interfejs i = (Interfejs) element;
        i.getMetodeList().add(new Metode(Vidljivost.PROTECTED, "void", "test1"));
        i.getMetodeList().add(new Metode(Vidljivost.PROTECTED, "void", "test2"));
        i.getMetodeList().add(new Metode(Vidljivost.PROTECTED, "void", "test3"));
        i.getMetodeList().add(new Metode(Vidljivost.PROTECTED, "void", "test4testetsttest"));
        */

    }

    @Override
    public void draw(Graphics2D g) {
        g.setPaint(Color.BLACK);
        g.setStroke(new BasicStroke(this.element.getStroke()));
        Interfejs i = (Interfejs) this.element;

        //odredjivanje sirine i visine reda, kao i ukupne visine
        width = maxDuzina(g);
        int heightRow = g.getFontMetrics().getHeight();
        heightUkupno = heightRow * i.getMetodeList().size() + heightRow;

        shape = new Rectangle(i.getPosition().x - width/2 - 5, i.getPosition().y - heightUkupno/2, width+10, heightUkupno+10);
        g.draw(shape);
        g.drawString("I", i.getPosition().x - width/2, i.getPosition().y - heightUkupno/2 + heightRow);


        //crtanje metoda
        int brojac = 2;
        for(ClassContent c : ((Interfejs)element).getMetodeList()){
            String string;
            string = c.getVidljivost() + c.getNaziv() + "(): " + c.getTip();
            g.drawString(string, i.getPosition().x - width / 2, i.getPosition().y - heightUkupno / 2 + brojac * heightRow);
            brojac++;
        }
        setConnectionPoints();
    }

    public int maxDuzina(Graphics2D g){
        int max = 0;
        int width = 0;
        String string;

        for(ClassContent c : ((Interfejs)element).getMetodeList()){
            string = c.getVidljivost() + c.getNaziv() + "(): " + c.getTip();
            width = g.getFontMetrics().stringWidth(string);
            if(max < width) max = width;
        }
        string = "I" + ((Interfejs)element).getNaziv();
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
