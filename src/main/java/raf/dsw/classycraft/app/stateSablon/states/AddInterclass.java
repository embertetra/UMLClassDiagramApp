package raf.dsw.classycraft.app.stateSablon.states;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.EnumM;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Interfejs;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Klasa;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Vidljivost;
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

public class AddInterclass implements State {

    private String interclass;

    @Override
    public void misKliknut(int x, int y, DijagramView dijagramView) {

        dijagramView.getSelectionModel().clear();
        dijagramView.setSelection(null);
        Dijagram d = (Dijagram)dijagramView.getClassyNode();
        d.addSubscriber(dijagramView);

        Rectangle rec = new Rectangle(x-38,y-23, 76, 46);
        for(ElementPainter ep : dijagramView.getElementPainterList()){
            if(ep instanceof InterclassPainter){
                InterclassPainter ip = (InterclassPainter) ep;
                Rectangle rectangle = new Rectangle(((Interclass)ip.getElement()).getPosition().x-ip.getWidth()/2,((Interclass)ip.getElement()).getPosition().y-ip.getHeightUkupno()/2,
                        ip.getWidth(), ip.getHeightUkupno());
                if(rectangle.intersects(rec)) {
                    ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("nije moguce postaviti interklasu na zeljeno mesto", MessageType.ERROR);
                    return;
                }
            }
        }
        if(interclass != null) {
            if (interclass.equals("class")) {
                Klasa klasa = new Klasa("name", dijagramView.getClassyNode(), 2, "naziv", Vidljivost.PUBLIC, new Point(x, y));
                KlasaPainter klasaPainter = new KlasaPainter(klasa);
                dijagramView.getElementPainterList().add(klasaPainter);
                d.addChild(klasa);
            } else if (interclass.equals("interface")) {
                Interfejs interfejs = new Interfejs("name", dijagramView.getClassyNode(), 2, "naziv", Vidljivost.PUBLIC, new Point(x, y));
                InterfejsPainter interfejsPainter = new InterfejsPainter(interfejs);
                dijagramView.getElementPainterList().add(interfejsPainter);
                d.addChild(interfejs);
            } else if (interclass.equals("enum")) {
                EnumM enumM = new EnumM("name", dijagramView.getClassyNode(), 2, "naziv", Vidljivost.PUBLIC, new Point(x, y));
                EnumPainter enumPainter = new EnumPainter(enumM);
                dijagramView.getElementPainterList().add(enumPainter);
                d.addChild(enumM);
            }
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
