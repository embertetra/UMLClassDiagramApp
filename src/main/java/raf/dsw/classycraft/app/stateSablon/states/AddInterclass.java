package raf.dsw.classycraft.app.stateSablon.states;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.EnumM;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Interfejs;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Klasa;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Vidljivost;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.MessageType;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.EnumPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.InterfejsPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.KlasaPainter;
import raf.dsw.classycraft.app.stateSablon.State;

import java.awt.*;

public class AddInterclass implements State {

    private String interclass;

    @Override
    public void misKliknut(int x, int y, DijagramView dijagramView) {

        Dijagram d = (Dijagram)dijagramView.getClassyNode();
        d.addSubscriber(dijagramView);

        for(ElementPainter ep : dijagramView.getElementPainterList()){
            if(ep.elementAt(new Point(x,y))){
                ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("nije moguce postaviti interklasu na zeljeno mesto", MessageType.ERROR);
                return;
            }
        }

        if(interclass.equals("class")){
            Klasa klasa = new Klasa("name", dijagramView.getClassyNode(), 2, "naziv", Vidljivost.PUBLIC, new Point(x,y));
            KlasaPainter klasaPainter = new KlasaPainter(klasa);
            dijagramView.getElementPainterList().add(klasaPainter);
            d.addChild(klasa);
        }
        else if(interclass.equals("interface")){
            Interfejs interfejs = new Interfejs("name", dijagramView.getClassyNode(), 2, "naziv", Vidljivost.PUBLIC, new Point(x,y));
            InterfejsPainter interfejsPainter = new InterfejsPainter(interfejs);
            dijagramView.getElementPainterList().add(interfejsPainter);
            d.addChild(interfejs);
        }
        else if(interclass.equals("enum")){
            EnumM enumM = new EnumM("name", dijagramView.getClassyNode(), 2, "naziv", Vidljivost.PUBLIC, new Point(x,y));
            EnumPainter enumPainter = new EnumPainter(enumM);
            dijagramView.getElementPainterList().add(enumPainter);
            d.addChild(enumM);
        }

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
