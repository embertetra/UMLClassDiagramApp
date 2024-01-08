package raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter;

import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.Atributi;
import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.ClassContent;
import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.Metode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.DijagramElement;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Klasa;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class KlasaPainter extends InterclassPainter {
    protected Shape shape;

    public KlasaPainter(DijagramElement element) {
        super(element);
        /*
        //test primeri
        Klasa k = (Klasa) this.element;
        k.getClassContentList().add(new Atributi(Vidljivost.PRIVATE, "string", "test1"));
        k.getClassContentList().add(new Metode(Vidljivost.PROTECTED, "void", "test2"));
        k.getClassContentList().add(new Atributi(Vidljivost.PUBLIC, "boolean", "test3"));
        k.getClassContentList().add(new Metode(Vidljivost.PROTECTED, "void", "test4testetsttest"));
        */
    }

    @Override
    public void draw(Graphics2D g) {
        g.setPaint(Color.BLACK);
        g.setStroke(new BasicStroke(this.element.getStroke()));
        Klasa k = (Klasa) this.element;
        AffineTransform at = g.getTransform();
        //odredjivanje sirine, visine reda i ukupne visine
        width = maxDuzina(g);
        int heightRed = g.getFontMetrics().getHeight();
        heightUkupno = heightRed * k.getClassContentList().size() + heightRed;

        shape = new Rectangle(k.getPosition().x - width / 2 - 5, k.getPosition().y - heightUkupno / 2, width + 10, heightUkupno + 10);

        g.draw(shape);
        g.drawString("C  " + k.getNaziv(), k.getPosition().x - width / 2, k.getPosition().y - heightUkupno / 2 + heightRed);

        //crtanje atributa
        int brojac = 2;
        for (ClassContent c : k.getClassContentList()) {
            String string;
            if (c instanceof Atributi) {
                Atributi a = (Atributi) c;
                string = a.getVidljivost() + a.getNaziv() + ": " + a.getTip();
                g.drawString(string, k.getPosition().x - width / 2, k.getPosition().y - heightUkupno / 2 + brojac * heightRed);
                brojac++;
            }
        }
        //crtanje metoda
        for (ClassContent c : k.getClassContentList()) {
            String string;
            if (c instanceof Metode) {
                Metode m = (Metode) c;
                string = m.getVidljivost() + m.getNaziv() + "(): " + m.getTip();
                g.drawString(string, k.getPosition().x - width / 2, k.getPosition().y - heightUkupno / 2 + brojac * heightRed);
                brojac++;
            }
        }
        setConnectionPoints(k.getPosition().x, k.getPosition().y);
    }

    public int maxDuzina(Graphics2D g) {
        int max = 0;
        int width = 0;
        String string;

        for (ClassContent c : ((Klasa) element).getClassContentList()) {
            string = c.getVidljivost() + c.getNaziv() + ": " + c.getTip();
            width = g.getFontMetrics().stringWidth(string);
            if (c instanceof Metode) {
                width += g.getFontMetrics().stringWidth("()");
            }
            if (width > max) max = width;
        }
        string = "C  " + ((Klasa) element).getNaziv();
        width = g.getFontMetrics().stringWidth(string);
        if (width > max) max = width;

        return max;
    }

    public void setConnectionPoints(int x, int y){
        connectionPoints.clear();
        int width2 = width + 10;
        int height2 = heightUkupno + 5;
        connectionPoints.add(new Point(x, y - height2/2));//gore 0
        connectionPoints.add(new Point(x, y + height2/2 + 8));//dole 1
        connectionPoints.add(new Point(x-width2/2, y));//levo 2
        connectionPoints.add(new Point(x+width2/2, y));//desno 3
    }

    @Override
    public boolean elementAt(Point point) {
        if(getShape() == null)
            return false;

        return this.getShape().contains(point);
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public int getWidth() {
        return width;
    }

    public int getHeightUkupno() {
        return heightUkupno;
    }
}
