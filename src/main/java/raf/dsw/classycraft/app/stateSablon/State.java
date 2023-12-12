package raf.dsw.classycraft.app.stateSablon;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;

public interface State{

    void misKliknut(int x, int y, DijagramView dijagramView);
    void misOtpusten(int x, int y, DijagramView dijagramView);
    void misPrivucen(int x, int y, DijagramView dijagramView);
    void misDragged(int x, int y, DijagramView dijagramView);
    void wheelMove(int x, int y, DijagramView dijagramView);

}
