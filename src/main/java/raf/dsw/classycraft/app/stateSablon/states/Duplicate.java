package raf.dsw.classycraft.app.stateSablon.states;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.Metode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Interfejs;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Klasa;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.commands.AbstractCommand;
import raf.dsw.classycraft.app.commands.implementation.DuplicateCommand;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.MessageType;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.EnumPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.InterfejsPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.KlasaPainter;
import raf.dsw.classycraft.app.stateSablon.State;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Duplicate implements State {
    @Override
    public void misKliknut(int x, int y, DijagramView dijagramView) {

        if(dijagramView.getElementPainterList() == null) return;

        ClassyTreeItem item = null;
        //ClassyTreeItem selected = MainFrame.getInstance().getClassyTree().getSelectedNode();
        ClassyTreeItem selected = MainFrame.getInstance().getPackageView().getClassyTreeItem();
        for(int i=0; i<selected.getChildCount(); i++){
            ClassyTreeItem c = (ClassyTreeItem)selected.getChildAt(i);
            ClassyNode cn = c.getClassyNode();
            if(cn.getName().equals(dijagramView.getClassyNode().getName()))
                item = c;
        }

        /*
        Klasa duplikatK = null;
        Interfejs duplikatI = null;
         */

        InterclassPainter ip = null;
        AbstractCommand command = null;
        for(ElementPainter e : dijagramView.getElementPainterList()){
            if(e instanceof InterclassPainter){
                if(e.elementAt(new Point(x, y))) {
                    ip = (InterclassPainter) e;
                    if (ip instanceof EnumPainter)
                        ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Nije moguce napraviti kopiju enuma!", MessageType.ERROR);
                    else if (ip instanceof KlasaPainter) {
                        /*
                        //Klasa k = (Klasa) ip.getElement();
                        duplikatK = new Klasa("Interclass", dijagramView.getClassyNode(), 2, k.getNaziv(), k.getVidljivost(), new Point(k.getPosition().x + ip.getWidth()/2 + 5, k.getPosition().y + ip.getHeightUkupno()/2 + 10));
                        duplikatK.setNaziv(k.getNaziv()); duplikatK.setClassContentList(k.getClassContentList());
                        duplikatK.addSubscriber(dijagramView);
                        dijagramView.getElementPainterList().add(new KlasaPainter(duplikatK));
                        Dijagram d = (Dijagram) dijagramView.getClassyNode();
                        if(item != null)
                            MainFrame.getInstance().getClassyTree().addChild(item, duplikatK);
                        d.addChild(duplikatK);
                        */
                        command = new DuplicateCommand(ip, dijagramView, item);
                        ((DijagramView)MainFrame.getInstance().getPackageView().getjTabbedPane().getSelectedComponent()).getCommandManager().addCommand(command);

                        break;
                    } else if (ip instanceof InterfejsPainter) {
                        /*
                        Interfejs i = (Interfejs) ip.getElement();
                        duplikatI = new Interfejs("Interclass", dijagramView.getClassyNode(), 2, i.getNaziv(), i.getVidljivost(), new Point(i.getPosition().x + ip.getWidth()/2 + 5, i.getPosition().y + 10 + ip.getHeightUkupno()/2));
                        duplikatI.setNaziv(i.getNaziv()); duplikatI.setMetodeList(i.getMetodeList());
                        duplikatI.addSubscriber(dijagramView);
                        dijagramView.getElementPainterList().add(new InterfejsPainter(duplikatI));
                        Dijagram d = (Dijagram) dijagramView.getClassyNode();
                        MainFrame.getInstance().getClassyTree().addChild(item, duplikatI);
                        d.addChild(duplikatI);
                        */
                        command = new DuplicateCommand(ip, dijagramView, item);
                        ((DijagramView)MainFrame.getInstance().getPackageView().getjTabbedPane().getSelectedComponent()).getCommandManager().addCommand(command);

                        break;
                    }
                }
            }
        }
        dijagramView.repaint();
        System.out.println("Napravljena kopija");
    }

    @Override
    public void misOtpusten(int x, int y, DijagramView dijagramView) {

    }

    @Override
    public void misPrivucen(int x, int y, DijagramView dijagramView) {

    }

    @Override
    public void misDragged(int x, int y, DijagramView dijagramView) {

    }

    @Override
    public void wheelMove(int x, int y, DijagramView dijagramView) {

    }
}
