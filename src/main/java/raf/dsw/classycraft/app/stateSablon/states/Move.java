package raf.dsw.classycraft.app.stateSablon.states;

import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.stateSablon.State;

import java.awt.*;

public class Move implements State {

    @Override
    public void misKliknut(int x, int y, DijagramView dijagramView) {

    }

    @Override
    public void misOtpusten(int x, int y, DijagramView dijagramView) {
        dijagramView.removeTranslation();
        dijagramView.repaint();
    }

    @Override
    public void misPrivucen(int x, int y, DijagramView dijagramView) {
        dijagramView.setTranslation();
        dijagramView.setOffSet(new Point(x,y));
        dijagramView.repaint();
    }
}
