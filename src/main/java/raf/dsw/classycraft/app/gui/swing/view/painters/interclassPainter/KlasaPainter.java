package raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter;

import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.Atributi;
import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.ClassContent;
import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.Metode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.DijagramElement;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Klasa;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Vidljivost;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;

import java.awt.*;

public class KlasaPainter extends InterclassPainter {
    protected Shape shape;

    public KlasaPainter(DijagramElement element) {
        super(element);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setPaint(Color.BLACK);
        g.setStroke(new BasicStroke(this.element.getStroke()));
        Klasa k = (Klasa) this.element;
        /*
        ///test primeri
        k.getClassContentList().add(new Atributi(Vidljivost.PRIVATE, "String", "test1"));
        k.getClassContentList().add(new Metode(Vidljivost.PROTECTED, "void", "test2"));
        k.getClassContentList().add(new Atributi(Vidljivost.PUBLIC, "boolean", "test3"));
        k.getClassContentList().add(new Metode(Vidljivost.PROTECTED, "void", "test4"));
        */
        //odredjivanje sirine, visine reda i ukupne visine
        int width = maxDuzina(g);
        int heightRed = g.getFontMetrics().getHeight();
        int heightUkupno = g.getFontMetrics().getHeight() * k.getClassContentList().size() + heightRed;

        g.drawRect(k.getPosition().x-width/2-5, k.getPosition().y-heightUkupno/2, width+10,heightUkupno+10);
        g.drawString("C", k.getPosition().x-width/2, k.getPosition().y-heightUkupno/2 + heightRed);
        //crtanje atributa
        int brojac = 2;
        for(ClassContent c : k.getClassContentList()){
            String string;
            if(c instanceof Atributi){
                Atributi a = (Atributi) c;
                string = a.getVidljivost() + a.getNaziv() + " : " + a.getTip();
                g.drawString(string, k.getPosition().x - width/2, k.getPosition().y - heightUkupno/2 + brojac*heightRed);
                brojac++;
            }
        }
        //crtanje metoda
        for(ClassContent c : k.getClassContentList()){
            String string;
            if(c instanceof Metode){
                Metode m = (Metode) c;
                string = m.getVidljivost() + m.getNaziv() + "() : " + m.getTip();
                g.drawString(string, k.getPosition().x - width/2, k.getPosition().y - heightUkupno/2 + brojac*heightRed);
                brojac++;
            }
        }
    }

    private int maxDuzina(Graphics2D g){
        int max = 0;
        int width = 0;
        String string;

        for(ClassContent c : ((Klasa)element).getClassContentList()){
            string = c.getVidljivost() + c.getNaziv() + " : " + c.getTip();
            width = g.getFontMetrics().stringWidth(string);
            if(c instanceof Metode) {
                width += g.getFontMetrics().stringWidth("()");
            }
            if(width > max)max = width;
        }
        string = "C " + ((Klasa) element).getNaziv();
        width = g.getFontMetrics().stringWidth(string);
        if(width > max)max = width;
        return max;
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
