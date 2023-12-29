package raf.dsw.classycraft.app.commands.implementation;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Interfejs;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Klasa;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.commands.AbstractCommand;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.InterfejsPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.KlasaPainter;

import java.awt.*;

public class DuplicateCommand extends AbstractCommand {

    private InterclassPainter interclassPainter;
    private Klasa k = null;
    private Klasa duplikatK;
    private Interfejs i = null;
    private Interfejs duplikatI;
    private DijagramView dijagramView;
    private ClassyTreeItem item = null;

    public DuplicateCommand(InterclassPainter interclassPainter, DijagramView dijagramView, ClassyTreeItem item){
       this.interclassPainter = interclassPainter;
       this.dijagramView = dijagramView;
       this.item = item;
    }

    @Override
    public void doCommand() {
        if(interclassPainter instanceof KlasaPainter) {
            k = (Klasa) interclassPainter.getElement();
            duplikatK = new Klasa("Interclass", dijagramView.getClassyNode(), 2, k.getNaziv(), k.getVidljivost(), new Point(k.getPosition().x + interclassPainter.getWidth() / 2 + 5, k.getPosition().y + interclassPainter.getHeightUkupno() / 2 + 10));
            duplikatK.setNaziv(k.getNaziv());
            duplikatK.setClassContentList(k.getClassContentList());
            duplikatK.addSubscriber(dijagramView);
            dijagramView.getElementPainterList().add(new KlasaPainter(duplikatK));
            Dijagram d = (Dijagram) dijagramView.getClassyNode();
            if (item != null)
                MainFrame.getInstance().getClassyTree().addChild(item, duplikatK);
            d.addChild(duplikatK);
        }
        else if(interclassPainter instanceof InterfejsPainter) {
            i = (Interfejs) interclassPainter.getElement();
            duplikatI = new Interfejs("Interclass", dijagramView.getClassyNode(), 2, i.getNaziv(), i.getVidljivost(), new Point(i.getPosition().x + interclassPainter.getWidth()/2 + 5, i.getPosition().y + 10 + interclassPainter.getHeightUkupno()/2));
            duplikatI.setNaziv(i.getNaziv()); duplikatI.setMetodeList(i.getMetodeList());
            duplikatI.addSubscriber(dijagramView);
            dijagramView.getElementPainterList().add(new InterfejsPainter(duplikatI));
            Dijagram d = (Dijagram) dijagramView.getClassyNode();
            MainFrame.getInstance().getClassyTree().addChild(item, duplikatI);
            d.addChild(duplikatI);
        }
    }

    @Override
    public void undoCommand() {

    }
}
