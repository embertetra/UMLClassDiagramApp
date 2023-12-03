package raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.DijagramElement;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Agregacija;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painters.ConnectionPainter;

import java.awt.*;

public class AgregacijaPainter extends ConnectionPainter {
    public AgregacijaPainter(DijagramElement element) {
        super(element);
    }

    @Override
    public void draw(Graphics2D g) {
        Agregacija a = (Agregacija)element;

        g.setPaint(Color.BLACK);
        g.setStroke(new BasicStroke(3));
        DijagramView d = (DijagramView) MainFrame.getInstance().getPackageView().getjTabbedPane().getSelectedComponent();
        if(d.getLine().getValue() != null)
            g.drawLine(d.getLine().getKey().x, d.getLine().getKey().y, d.getLine().getValue().x, d.getLine().getValue().y);


    }

    @Override
    public boolean elementAt(DijagramElement dijagramElement, Point point) {
        return false;
    }
}
