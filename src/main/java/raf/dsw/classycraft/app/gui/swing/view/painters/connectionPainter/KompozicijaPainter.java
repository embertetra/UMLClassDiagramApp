package raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.DijagramElement;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Kompozicija;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painters.ConnectionPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class KompozicijaPainter extends ConnectionPainter {
    public KompozicijaPainter(DijagramElement element) {
        super(element);
    }

    @Override
    public void draw(Graphics2D g) {
        Kompozicija k = (Kompozicija) element;
        g.setStroke(new BasicStroke(element.getStroke()));
        g.setPaint(Color.BLACK);

        InterclassPainter kpFrom = null;
        InterclassPainter kpTo = null;

        DijagramView d = (DijagramView) MainFrame.getInstance().getPackageView().getjTabbedPane().getSelectedComponent();
        for (ElementPainter e : d.getElementPainterList()){
            if(e instanceof InterclassPainter){
                InterclassPainter ip = (InterclassPainter) e;
                if(ip.getElement() == k.getFrom())kpFrom = ip;
                else if(ip.getElement() == k.getTo())kpTo = ip;
            }
        }
        double min = 1e9;
        Point point1 = null;
        Point point2 = null;
        int idx1 = 0;
        for(int i=0;i<=3;i++){
            for(int j=0;j<=3;j++){
                assert kpFrom != null;
                assert kpTo != null;
                double c = Math.sqrt(Math.pow(kpFrom.getConnectionPoints().get(i).x - kpTo.getConnectionPoints().get(j).x,2)+
                        Math.pow(kpFrom.getConnectionPoints().get(i).y - kpTo.getConnectionPoints().get(j).y,2));
                if(c<min){
                    min=c;
                    point1 = kpFrom.getConnectionPoints().get(i);
                    point2 = kpTo.getConnectionPoints().get(j);
                    idx1 = i;
                }
            }
        }
        Shape shape = new GeneralPath();
        if(idx1 == 3){///desno
            ((GeneralPath)shape).moveTo(point1.x, point1.y);
            ((GeneralPath)shape).lineTo(point1.x + 10, point1.y-10);
            ((GeneralPath)shape).lineTo(point1.x + 2*10, point1.y);
            ((GeneralPath)shape).lineTo(point2.x, point2.y);
            ((GeneralPath)shape).lineTo(point1.x + 2*10, point1.y);
            ((GeneralPath)shape).lineTo(point1.x + 10, point1.y+10);
            ((GeneralPath)shape).lineTo(point1.x, point1.y);
            ((GeneralPath)shape).closePath();
        }
        else if(idx1 == 0){///gore
            ((GeneralPath)shape).moveTo(point1.x, point1.y);
            ((GeneralPath)shape).lineTo(point1.x - 10, point1.y-10);
            ((GeneralPath)shape).lineTo(point1.x, point1.y-2*10);
            ((GeneralPath)shape).lineTo(point2.x, point2.y);
            ((GeneralPath)shape).lineTo(point1.x, point1.y-2*10);
            ((GeneralPath)shape).lineTo(point1.x + 10, point1.y-10);
            ((GeneralPath)shape).lineTo(point1.x, point1.y);
            ((GeneralPath)shape).closePath();
        }
        else if(idx1 == 1){///dole
            ((GeneralPath)shape).moveTo(point1.x, point1.y);
            ((GeneralPath)shape).lineTo(point1.x+10, point1.y+10);
            ((GeneralPath)shape).lineTo(point1.x, point1.y+2*10);
            ((GeneralPath)shape).lineTo(point2.x, point2.y);
            ((GeneralPath)shape).lineTo(point1.x, point1.y+2*10);
            ((GeneralPath)shape).lineTo(point1.x-10, point1.y+10);
            ((GeneralPath)shape).lineTo(point1.x, point1.y);
            ((GeneralPath)shape).closePath();

        }
        else if(idx1 == 2){///levo
            ((GeneralPath)shape).moveTo(point1.x, point1.y);
            ((GeneralPath)shape).lineTo(point1.x-10, point1.y+10);
            ((GeneralPath)shape).lineTo(point1.x-20, point1.y);
            ((GeneralPath)shape).lineTo(point2.x, point2.y);
            ((GeneralPath)shape).lineTo(point1.x-20, point1.y);
            ((GeneralPath)shape).lineTo(point1.x-10, point1.y-10);
            ((GeneralPath)shape).lineTo(point1.x, point1.y);
            ((GeneralPath)shape).closePath();
        }
        g.fill(shape);
        g.draw(shape);
    }

    @Override
    public boolean elementAt(Point point) {
        return false;
    }
}
