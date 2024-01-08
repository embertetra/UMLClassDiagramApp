package raf.dsw.classycraft.app.stateSablon.states;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
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


        InterclassPainter ip = null;
        AbstractCommand command = null;
        for(ElementPainter e : dijagramView.getElementPainterList()){
            if(e instanceof InterclassPainter){
                if(e.elementAt(new Point(x, y))) {
                    ip = (InterclassPainter) e;
                    if (ip instanceof EnumPainter)
                        ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Nije moguce napraviti kopiju enuma!", MessageType.ERROR);
                    else if (ip instanceof KlasaPainter) {
                        command = new DuplicateCommand(ip, dijagramView, item);
                        ((DijagramView)MainFrame.getInstance().getPackageView().getjTabbedPane().getSelectedComponent()).getCommandManager().addCommand(command);

                        break;
                    } else if (ip instanceof InterfejsPainter) {
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
