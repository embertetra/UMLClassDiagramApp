package raf.dsw.classycraft.app.stateSablon.states;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.stateSablon.State;

public class AddConnection implements State {

    @Override
    public void misKliknut(int x, int y, DijagramView dijagramView) {

    }

    @Override
    public void misOtpusten(int x, int y, DijagramView dijagramView) {
        System.out.println("poceo sam ovde (x,y) " + x + "," + y);
    }

    @Override
    public void misPrivucen(int x, int y, DijagramView dijagramView) {
        System.out.println("zavrsio sam ovde (x,y) " + x + "," + y);
    }
}
