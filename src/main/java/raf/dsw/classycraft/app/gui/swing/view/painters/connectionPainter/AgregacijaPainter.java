package raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.DijagramElement;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Agregacija;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Klasa;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painters.ConnectionPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.KlasaPainter;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class AgregacijaPainter extends ConnectionPainter {
    public AgregacijaPainter(DijagramElement element) {
        super(element);
    }

    @Override
    public void draw(Graphics2D g) {
        Agregacija a = (Agregacija)element;
        g.setStroke(new BasicStroke(element.getStroke()));
        g.setPaint(Color.BLACK);

        KlasaPainter kpFrom = null;
        KlasaPainter kpTo = null;

        DijagramView d = (DijagramView) MainFrame.getInstance().getPackageView().getjTabbedPane().getSelectedComponent();
        for (ElementPainter e : d.getElementPainterList()){
            if(e instanceof KlasaPainter){
                KlasaPainter kp = (KlasaPainter) e;
                if(kp.getElement() == a.getFrom())kpFrom = kp;
                else if(kp.getElement() == a.getTo())kpTo = kp;
            }
        }
        double min = 1e9;
        Point point1 = null;
        Point point2 = null;
        for(int i=0;i<=3;i++){
            for(int j=0;j<=3;j++){
                double c = Math.sqrt(Math.pow(kpFrom.getConnectionPoints().get(i).x - kpTo.getConnectionPoints().get(j).x,2)+
                        Math.pow(kpFrom.getConnectionPoints().get(i).y - kpTo.getConnectionPoints().get(j).y,2));
                if(c<min){
                    min=c;
                    point1 = kpFrom.getConnectionPoints().get(i);
                    point2 = kpTo.getConnectionPoints().get(j);
                }
            }
        }
        g.drawLine(point1.x, point1.y, point2.x, point2.y);
        Shape shape = new GeneralPath();



        //g.drawLine(a.getFrom().getPosition().x, a.getFrom().getPosition().y, a.getTo().getPosition().x, a.getTo().getPosition().y);

    }


    @Override
    public boolean elementAt(Point point) {
        return false;
    }
}
