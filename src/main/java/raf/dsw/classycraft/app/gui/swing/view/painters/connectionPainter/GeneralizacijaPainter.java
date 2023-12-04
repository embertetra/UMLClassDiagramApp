package raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.DijagramElement;
import raf.dsw.classycraft.app.gui.swing.view.painters.ConnectionPainter;

import java.awt.*;

public class GeneralizacijaPainter extends ConnectionPainter {
    public GeneralizacijaPainter(DijagramElement element) {
        super(element);
    }

    @Override
    public void draw(Graphics2D g) {

    }

    @Override
    public boolean elementAt(Point point) {
        return false;
    }
}
