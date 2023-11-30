package raf.dsw.classycraft.app.stateSablon.states;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.stateSablon.State;

public class AddInterclass implements State {

    private String interclass;

    @Override
    public void misKliknut(int x, int y, DijagramView dijagramView) {
        System.out.println("kreirana nova klasica :) " + interclass);
    }

    @Override
    public void misOtpusten(int x, int y, DijagramView dijagramView) {

    }

    @Override
    public void misPrivucen(int x, int y, DijagramView dijagramView) {

    }

    public void setInterclass(String interclass) {
        this.interclass = interclass;
    }
}
