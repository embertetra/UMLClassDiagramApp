package raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.DijagramElement;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Zavisnost;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painters.ConnectionPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;

import java.awt.*;
import java.awt.geom.GeneralPath;

public class ZavisnostPainter extends ConnectionPainter {
    public ZavisnostPainter(DijagramElement element) {
        super(element);
    }

    @Override
    public void draw(Graphics2D g) {
        Zavisnost z = (Zavisnost) element;
        g.setStroke(new BasicStroke(element.getStroke()));

        InterclassPainter zpFrom = null;
        InterclassPainter zpTo = null;

        DijagramView d = (DijagramView) MainFrame.getInstance().getPackageView().getjTabbedPane().getSelectedComponent();
        for(ElementPainter e : d.getElementPainterList()){
            if(e instanceof InterclassPainter){
                InterclassPainter ip = (InterclassPainter) e;
                if(ip.getElement() == z.getFrom()) zpFrom = ip;
                else if(ip.getElement() == z.getTo()) zpTo = ip;
            }
        }
        double min = 1e9;
        point1 = null;
        point2 = null;
        int idx1 = 0;
        for(int i=0; i<=3; i++){
            for(int j=0; j<=3; j++){
                if(zpFrom != null && zpTo != null) {
                    double c = Math.sqrt(Math.pow(zpFrom.getConnectionPoints().get(i).x - zpTo.getConnectionPoints().get(j).x, 2) +
                            Math.pow(zpFrom.getConnectionPoints().get(i).y - zpTo.getConnectionPoints().get(j).y, 2));
                    if (c < min) {
                        min = c;
                        point1 = zpFrom.getConnectionPoints().get(i);
                        point2 = zpTo.getConnectionPoints().get(j);
                        idx1 = i;
                    }
                }
            }
        }

        Shape shape = new GeneralPath();
        if(point1 != null && point2 != null){

            if(idx1 == 0){ //gore
                ((GeneralPath)shape).moveTo(point2.x, point2.y);
                ((GeneralPath)shape).lineTo(point2.x - 10, point2.y + 10);
                ((GeneralPath)shape).lineTo(point2.x, point2.y);
                ((GeneralPath)shape).lineTo(point2.x + 10, point2.y + 10);
                ((GeneralPath)shape).lineTo(point2.x, point2.y);
                g.setStroke(new BasicStroke(element.getStroke(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
                ((GeneralPath)shape).lineTo(point1.x, point1.y);
                ((GeneralPath)shape).closePath();
            }
            else if(idx1 == 1){ //dole
                ((GeneralPath)shape).moveTo(point2.x, point2.y);
                ((GeneralPath)shape).lineTo(point2.x + 10, point2.y - 10);
                ((GeneralPath)shape).lineTo(point2.x, point2.y);
                ((GeneralPath)shape).lineTo(point2.x - 10, point2.y - 10);
                ((GeneralPath)shape).lineTo(point2.x, point2.y);
                g.setStroke(new BasicStroke(element.getStroke(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
                ((GeneralPath)shape).lineTo(point1.x, point1.y);
                ((GeneralPath)shape).closePath();
            }
            else if(idx1 == 2){ //levo
                ((GeneralPath)shape).moveTo(point2.x, point2.y);
                ((GeneralPath)shape).lineTo(point2.x + 10, point2.y-10);
                ((GeneralPath)shape).lineTo(point2.x, point2.y);
                ((GeneralPath)shape).lineTo(point2.x + 10, point2.y+10);
                ((GeneralPath)shape).lineTo(point2.x, point2.y);
                g.setStroke(new BasicStroke(element.getStroke(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
                ((GeneralPath)shape).lineTo(point1.x, point1.y);
                ((GeneralPath)shape).closePath();
            }
            else if(idx1 == 3){ //desno
                ((GeneralPath)shape).moveTo(point2.x, point2.y);
                ((GeneralPath)shape).lineTo(point2.x - 10, point2.y-10);
                ((GeneralPath)shape).lineTo(point2.x, point2.y);
                ((GeneralPath)shape).lineTo(point2.x - 10, point2.y+10);
                ((GeneralPath)shape).lineTo(point2.x, point2.y);
                g.setStroke(new BasicStroke(element.getStroke(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
                ((GeneralPath)shape).lineTo(point1.x, point1.y);
                ((GeneralPath)shape).closePath();
            }
        }

        g.draw(shape);
    }

    @Override
    public boolean elementAt(Point point) {
        return false;
    }
}
