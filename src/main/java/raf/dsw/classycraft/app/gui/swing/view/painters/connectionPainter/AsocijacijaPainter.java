package raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.DijagramElement;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Asocijacija;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painters.ConnectionPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class AsocijacijaPainter extends ConnectionPainter {
    public AsocijacijaPainter(DijagramElement element) {
        super(element);
    }

    @Override
    public void draw(Graphics2D g) {
        Asocijacija as = (Asocijacija) element;
        g.setStroke(new BasicStroke(element.getStroke()));
        g.setPaint(Color.BLACK);

        InterclassPainter aspFrom = null;
        InterclassPainter aspTo = null;

        DijagramView d = (DijagramView) MainFrame.getInstance().getPackageView().getjTabbedPane().getSelectedComponent();
        for (ElementPainter e : d.getElementPainterList()){
            if(e instanceof InterclassPainter){
                InterclassPainter ip = (InterclassPainter) e;
                if(ip.getElement() == as.getFrom())aspFrom = ip;
                else if(ip.getElement() == as.getTo())aspTo = ip;
            }
        }
        double min = 1e9;
        Point point1 = null;
        Point point2 = null;
        int idx1 = 0;
        for(int i=0;i<=3;i++){
            for(int j=0;j<=3;j++){
                if(aspFrom != null && aspTo != null) {
                    double c = Math.sqrt(Math.pow(aspFrom.getConnectionPoints().get(i).x - aspTo.getConnectionPoints().get(j).x, 2) +
                            Math.pow(aspFrom.getConnectionPoints().get(i).y - aspTo.getConnectionPoints().get(j).y, 2));
                    if (c < min) {
                        min = c;
                        point1 = aspFrom.getConnectionPoints().get(i);
                        point2 = aspTo.getConnectionPoints().get(j);
                        idx1 = j;
                    }
                }
            }
        }
        Shape shape = new GeneralPath();
        if(point1 != null && point2!=null) {
            if (idx1 == 3) {///desno
                ((GeneralPath) shape).moveTo(point2.x + 2, point2.y);
                ((GeneralPath) shape).lineTo(point2.x + 10, point2.y + 10);
                ((GeneralPath) shape).lineTo(point2.x + 2, point2.y);
                ((GeneralPath) shape).lineTo(point2.x+10, point2.y-10);
                ((GeneralPath) shape).lineTo(point2.x + 2, point2.y);
                ((GeneralPath) shape).lineTo(point1.x, point1.y);
                ((GeneralPath) shape).lineTo(point2.x + 2, point2.y);
                ((GeneralPath) shape).closePath();
            } else if (idx1 == 0) {///gore
                ((GeneralPath) shape).moveTo(point2.x, point2.y);
                ((GeneralPath) shape).lineTo(point2.x + 10, point2.y - 10);
                ((GeneralPath) shape).lineTo(point2.x, point2.y);
                ((GeneralPath) shape).lineTo(point2.x - 10, point2.y + 10);
                ((GeneralPath) shape).lineTo(point2.x, point2.y);
                ((GeneralPath) shape).lineTo(point1.x, point1.y);
                ((GeneralPath) shape).lineTo(point2.x, point2.y);
                ((GeneralPath) shape).closePath();
            } else if (idx1 == 1) {///dole
                ((GeneralPath) shape).moveTo(point2.x, point2.y);
                ((GeneralPath) shape).lineTo(point2.x + 10, point2.y + 10);
                ((GeneralPath) shape).lineTo(point2.x, point2.y);
                ((GeneralPath) shape).lineTo(point2.x - 10, point2.y + 10);
                ((GeneralPath) shape).lineTo(point2.x, point2.y);
                ((GeneralPath) shape).lineTo(point1.x, point1.y);
                ((GeneralPath) shape).lineTo(point2.x, point2.y);
                ((GeneralPath) shape).closePath();

            } else if (idx1 == 2) {///levo
                ((GeneralPath) shape).moveTo(point2.x, point2.y);
                ((GeneralPath) shape).lineTo(point2.x - 10, point2.y - 10);
                ((GeneralPath) shape).lineTo(point2.x, point2.y);
                ((GeneralPath) shape).lineTo(point2.x - 10, point2.y + 10);
                ((GeneralPath) shape).lineTo(point2.x, point2.y);
                ((GeneralPath) shape).lineTo(point1.x, point1.y);
                ((GeneralPath) shape).lineTo(point2.x, point2.y);
                ((GeneralPath) shape).closePath();
            }
        }
        g.draw(shape);
    }

    @Override
    public boolean elementAt(Point point) {
        return false;
    }
}
