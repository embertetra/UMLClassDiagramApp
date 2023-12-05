package raf.dsw.classycraft.app.stateSablon.states;

import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.Atributi;
import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.Metode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.EnumM;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Interfejs;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Klasa;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.controller.DodajUEnumAction;
import raf.dsw.classycraft.app.controller.DodajUInterfejsAction;
import raf.dsw.classycraft.app.controller.DodajUKlasuAction;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;
import raf.dsw.classycraft.app.stateSablon.State;

import java.awt.*;

public class AddContent implements State {

    private Atributi a;

    private Metode m;

    @Override
    public void misKliknut(int x, int y, DijagramView dijagramView) {

        Dijagram d = (Dijagram)dijagramView.getClassyNode();
        InterclassPainter interclassPainter = null;

        for(ElementPainter ep : dijagramView.getElementPainterList()){
            if(ep.elementAt(new Point(x,y)) && ep instanceof InterclassPainter)
               interclassPainter = (InterclassPainter) ep;
        }

        if(interclassPainter != null) {
            if (interclassPainter.getElement() instanceof Klasa) {
                MainFrame.getInstance().getKlasaProzor().getJbDodaj().setAction(new DodajUKlasuAction(interclassPainter, dijagramView));
                MainFrame.getInstance().getKlasaProzor().setVisible(true);
            } else if (interclassPainter.getElement() instanceof Interfejs) {
                MainFrame.getInstance().getInterfejsProzor().getJbDodaj().setAction(new DodajUInterfejsAction(interclassPainter, dijagramView));
                MainFrame.getInstance().getInterfejsProzor().setVisible(true);
            } else if (interclassPainter.getElement() instanceof EnumM) {
                MainFrame.getInstance().getEnumProzor().getJbDodaj().setAction(new DodajUEnumAction(interclassPainter, dijagramView));
                MainFrame.getInstance().getEnumProzor().setVisible(true);
            }
        }
    }

    @Override
    public void misOtpusten(int x, int y, DijagramView dijagramView) {

    }

    @Override
    public void misPrivucen(int x, int y, DijagramView dijagramView) {

    }
}
