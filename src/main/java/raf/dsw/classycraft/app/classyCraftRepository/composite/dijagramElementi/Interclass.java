package raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi;

import javafx.geometry.Pos;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Vidljivost;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.painters.ConnectionPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.observer.IPublisher;

import javax.swing.text.Position;
import java.awt.*;

public abstract class Interclass extends DijagramElement implements IPublisher {

    private String naziv;

    private Vidljivost vidljivost;

    private int size;

    private Point position;

    public Interclass(String name, ClassyNode parent) {
        super(name, parent);
    }

    public Interclass(String name, ClassyNode parent, Color color, int stroke, String naziv, Vidljivost vidljivost, int size, Point position) {
        super(name, parent, color, stroke);
        this.naziv = naziv;
        this.vidljivost = vidljivost;
        this.size = size;
        this.position = position;
    }
    public Interclass(String name, ClassyNode parent, int stroke, String naziv, Vidljivost vidljivost, Point position) {
        super(name, parent, stroke);
        this.naziv = naziv;
        this.vidljivost = vidljivost;
        this.position = position;
    }

    public boolean poredjenje(Interclass i){
        if(position.x == i.getPosition().x && position.y == i.getPosition().y)
            return true;
        else return false;
    }

    public String getNaziv() {
        return naziv;
    }

    public Vidljivost getVidljivost() {
        return vidljivost;
    }

    public void setVidljivost(Vidljivost vidljivost) {
        this.vidljivost = vidljivost;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position, DijagramView d) {

        if(d != null){
            for(ElementPainter elementPainter : d.getElementPainterList()){
                if(elementPainter instanceof ConnectionPainter){
                    ConnectionPainter painter = (ConnectionPainter) elementPainter;
                    Connection cn = (Connection) painter.getElement();
                    if(cn.getTo().poredjenje(this))
                        cn.getTo().setPosition(position, null);
                    else if(cn.getFrom().poredjenje(this))
                        cn.getFrom().setPosition(position, null);
                }
            }
        }
        this.position = position;
        notifySubscribers("state");
    }
}
