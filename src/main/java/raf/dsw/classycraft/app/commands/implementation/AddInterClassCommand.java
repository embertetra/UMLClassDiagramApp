package raf.dsw.classycraft.app.commands.implementation;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.EnumM;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Interfejs;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Klasa;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.commands.AbstractCommand;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.EnumPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.InterfejsPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.KlasaPainter;

import java.awt.geom.AffineTransform;

public class AddInterClassCommand extends AbstractCommand {

    private Interclass interclass;
    private DijagramView dijagramView;

    public AddInterClassCommand(Interclass interclass, DijagramView dijagramView) {
        this.interclass = interclass;
        this.dijagramView = dijagramView;
    }

    @Override
    public void doCommand() {
        AffineTransform at = dijagramView.getAt();

        ///odredjivanje dijagrama unutar stabla
        ClassyTreeItem item = null;
        //ClassyNode tmp = MainFrame.getInstance().getPackageView().getClassyNode();
        ClassyTreeItem selected = MainFrame.getInstance().getPackageView().getClassyTreeItem();
        for (int i = 0; i < selected.getChildCount(); i++) {
            ClassyTreeItem c = (ClassyTreeItem) selected.getChildAt(i);
            ClassyNode cn = c.getClassyNode();
            if (cn.getName().equals(dijagramView.getClassyNode().getName()))
                item = c;
        }
        Dijagram d = (Dijagram) dijagramView.getClassyNode();

        if (interclass instanceof Klasa) {
            Klasa klasa = (Klasa)interclass;
            klasa.addSubscriber(dijagramView);
            KlasaPainter klasaPainter = new KlasaPainter(klasa);
            dijagramView.getElementPainterList().add(klasaPainter);
            d.addChild(klasa);///dodoavanje u model
            MainFrame.getInstance().getClassyTree().addChild(item, klasa);///dodavanje u stablo
        } else if (interclass instanceof Interfejs) {
            Interfejs interfejs = (Interfejs)interclass;
            interfejs.addSubscriber(dijagramView);
            InterfejsPainter interfejsPainter = new InterfejsPainter(interfejs);
            dijagramView.getElementPainterList().add(interfejsPainter);
            d.addChild(interfejs);
            MainFrame.getInstance().getClassyTree().addChild(item, interfejs);
        } else if (interclass instanceof EnumM) {
            EnumM enumM = (EnumM)interclass;
            enumM.addSubscriber(dijagramView);
            EnumPainter enumPainter = new EnumPainter(enumM);
            dijagramView.getElementPainterList().add(enumPainter);
            d.addChild(enumM);
            MainFrame.getInstance().getClassyTree().addChild(item, enumM);
        }
    }

    @Override
    public void undoCommand() {

    }
}
