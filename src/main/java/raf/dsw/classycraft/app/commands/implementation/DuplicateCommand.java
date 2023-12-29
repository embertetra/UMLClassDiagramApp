package raf.dsw.classycraft.app.commands.implementation;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Agregacija;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Generalizacija;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Kompozicija;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.connection.Zavisnost;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.EnumM;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Interfejs;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Klasa;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.commands.AbstractCommand;
import raf.dsw.classycraft.app.gui.swing.tree.ClassyTreeImplementation;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter.AgregacijaPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter.GeneralizacijaPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter.KompozicijaPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter.ZavisnostPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.EnumPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.InterfejsPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.KlasaPainter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
        ///odredjivanje dijagrama unutar stabla
        ClassyTreeItem item = null;
        ClassyTreeItem selected = MainFrame.getInstance().getPackageView().getClassyTreeItem();
        for (int i = 0; i < selected.getChildCount(); i++) {
            ClassyTreeItem c = (ClassyTreeItem) selected.getChildAt(i);
            ClassyNode cn = c.getClassyNode();
            if (cn.getName().equals(dijagramView.getClassyNode().getName()))
                item = c;
        }

        for(int j=dijagramView.getElementPainterList().size()-1; j>=0; j--){
            ElementPainter elementPainter = dijagramView.getElementPainterList().get(j);
            ///brisanje pojedinacne interklase
            if (elementPainter instanceof InterclassPainter) {
                Interclass klasaBrisanje = (Interclass) elementPainter.getElement();
                if (item != null) {
                    for (int i = 0; i < item.getChildCount(); i++) {
                        if (((ClassyTreeItem) item.getChildAt(i)).getClassyNode() instanceof Interclass) {
                            Interclass inter = (Interclass) ((ClassyTreeItem) item.getChildAt(i)).getClassyNode();
                            if (inter.poredjenje(klasaBrisanje)) {
                                Dijagram dijagram = (Dijagram) item.getClassyNode();
                                Interclass interclass = (Interclass) ((ClassyTreeItem) item.getChildAt(i)).getClassyNode();
                                dijagram.removeChild(interclass);
                                item.remove(i);
                                break;
                            }
                        }
                    }
                }
                ClassyTreeImplementation classyTreeImplementation = (ClassyTreeImplementation) MainFrame.getInstance().getClassyTree();
                SwingUtilities.updateComponentTreeUI(classyTreeImplementation.getTreeView());
                break;
            }
        }

        ///sredjivanje modela i paintera
        java.util.List<ElementPainter> novaPainterLista = new ArrayList<>();
        List<ClassyNode> novaLista = new ArrayList<>();
        for(int i=0;i< item.getChildCount();i++){
            ClassyTreeItem cti = (ClassyTreeItem) item.getChildAt(i);
            novaLista.add(cti.getClassyNode());
            if(cti.getClassyNode() instanceof Klasa)
                novaPainterLista.add(new KlasaPainter((Klasa) cti.getClassyNode()));
            else if(cti.getClassyNode() instanceof Interfejs)
                novaPainterLista.add(new InterfejsPainter((Interfejs) cti.getClassyNode()));
            else if(cti.getClassyNode() instanceof EnumM)
                novaPainterLista.add(new EnumPainter((EnumM) cti.getClassyNode()));
            else if(cti.getClassyNode() instanceof Agregacija)
                novaPainterLista.add(new AgregacijaPainter((Agregacija) cti.getClassyNode()));
            else if(cti.getClassyNode() instanceof Kompozicija)
                novaPainterLista.add(new KompozicijaPainter((Kompozicija) cti.getClassyNode()));
            else if(cti.getClassyNode() instanceof Generalizacija)
                novaPainterLista.add(new GeneralizacijaPainter((Generalizacija) cti.getClassyNode()));
            else if(cti.getClassyNode() instanceof Zavisnost)
                novaPainterLista.add(new ZavisnostPainter((Zavisnost) cti.getClassyNode()));
        }
        Dijagram d = (Dijagram) item.getClassyNode();
        d.setChildren(novaLista);
        dijagramView.getElementPainterList().clear();
        dijagramView.setElementPainterList(novaPainterLista);
        dijagramView.repaint();
    }
}
