package raf.dsw.classycraft.app.stateSablon.states;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.EnumM;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Interfejs;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Klasa;
import raf.dsw.classycraft.app.controller.*;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;
import raf.dsw.classycraft.app.stateSablon.State;

import java.awt.*;

public class AddContent implements State {

    @Override
    public void misKliknut(int x, int y, DijagramView dijagramView) {

        InterclassPainter interclassPainter = null;

        for(ElementPainter ep : dijagramView.getElementPainterList()){
            if(ep.elementAt(new Point(x,y)) && ep instanceof InterclassPainter)
               interclassPainter = (InterclassPainter) ep;
        }

        if(interclassPainter != null) {
            if (interclassPainter.getElement() instanceof Klasa) {
                Klasa k = (Klasa) interclassPainter.getElement();


                MainFrame.getInstance().getKlasaProzor().setClassContentList(k.getClassContentList());
                MainFrame.getInstance().getKlasaProzor().getLista().updateUI();


                MainFrame.getInstance().getKlasaProzor().getJbIme().setAction(new PromenaNazivaKlaseAction(interclassPainter, dijagramView));
                MainFrame.getInstance().getKlasaProzor().getJbDodaj().setAction(new DodajUKlasuAction(interclassPainter, dijagramView));
                MainFrame.getInstance().getKlasaProzor().setVisible(true);
            } else if (interclassPainter.getElement() instanceof Interfejs) {
                Interfejs i = (Interfejs) interclassPainter.getElement();

                MainFrame.getInstance().getInterfejsProzor().getJbIme().setAction(new PromeniNazivInterfejsaAction(interclassPainter, dijagramView));
                MainFrame.getInstance().getInterfejsProzor().getJbDodaj().setAction(new DodajUInterfejsAction(interclassPainter, dijagramView));
                MainFrame.getInstance().getInterfejsProzor().setVisible(true);
            } else if (interclassPainter.getElement() instanceof EnumM) {
                EnumM e = (EnumM) interclassPainter.getElement();
                MainFrame.getInstance().getEnumProzor().setEnumMList(e.getListEnuma());
                MainFrame.getInstance().getEnumProzor().getJbIme().setAction(new PromenaNazivaEnumaAction(interclassPainter, dijagramView));
                MainFrame.getInstance().getEnumProzor().getJbDodaj().setAction(new DodajUEnumAction(interclassPainter, dijagramView));
                MainFrame.getInstance().getEnumProzor().setVisible(true);
            }
        }
      
        dijagramView.getSelectionModel().clear();
        dijagramView.setSelection(null);
        dijagramView.repaint();

    }

    @Override
    public void misOtpusten(int x, int y, DijagramView dijagramView) {

    }

    @Override
    public void misPrivucen(int x, int y, DijagramView dijagramView) {
        dijagramView.getSelectionModel().clear();
        dijagramView.setSelection(null);
        dijagramView.repaint();
    }
}
