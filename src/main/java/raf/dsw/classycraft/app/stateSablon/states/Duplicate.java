package raf.dsw.classycraft.app.stateSablon.states;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Interfejs;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Klasa;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.MessageType;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.EnumPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.InterfejsPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.KlasaPainter;
import raf.dsw.classycraft.app.stateSablon.State;

import java.awt.*;

public class Duplicate implements State {
    @Override
    public void misKliknut(int x, int y, DijagramView dijagramView) {

        if(dijagramView.getElementPainterList() == null) return;

        InterclassPainter ip = null;
        Klasa duplikatK = null;
        Interfejs duplikatI = null;
        for(ElementPainter e : dijagramView.getElementPainterList()){
            if(e instanceof InterclassPainter){
                if(e.elementAt(new Point(x, y))) {
                    ip = (InterclassPainter) e;
                    if (ip instanceof EnumPainter)
                        ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Nije moguce napraviti kopiju enuma!", MessageType.ERROR);
                    else if (ip instanceof KlasaPainter) {
                        Klasa k = (Klasa) ip.getElement();
                        duplikatK = new Klasa("name", dijagramView.getClassyNode(), 2, k.getNaziv(), k.getVidljivost(), new Point(x, y + 15));
                        dijagramView.getElementPainterList().add(new KlasaPainter(duplikatK));
                        Dijagram d = (Dijagram) dijagramView.getClassyNode();
                        d.addChild(duplikatK);
                        break;
                    } else if (ip instanceof InterfejsPainter) {
                        Interfejs i = (Interfejs) ip.getElement();
                        duplikatI = new Interfejs("name", dijagramView.getClassyNode(), 2, i.getNaziv(), i.getVidljivost(), new Point(x, y + 15));
                        dijagramView.getElementPainterList().add(new InterfejsPainter(duplikatI));
                        Dijagram d = (Dijagram) dijagramView.getClassyNode();
                        d.addChild(duplikatI);
                        break;
                    }
                }
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
