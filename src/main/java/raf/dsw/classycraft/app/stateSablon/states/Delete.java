package raf.dsw.classycraft.app.stateSablon.states;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.commands.AbstractCommand;
import raf.dsw.classycraft.app.commands.implementation.MultipleDeleteCommand;
import raf.dsw.classycraft.app.commands.implementation.SingleDeleteCommand;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.stateSablon.State;

import java.awt.*;

public class Delete implements State {

    @Override
    public void misKliknut(int x, int y, DijagramView dijagramView) {

        ///pronalazenje dijagrama u stablu
        ClassyTreeItem item = null;
        ClassyTreeItem selected = MainFrame.getInstance().getPackageView().getClassyTreeItem();
        for (int i = 0; i < selected.getChildCount(); i++) {
            ClassyTreeItem c = (ClassyTreeItem) selected.getChildAt(i);
            ClassyNode cn = c.getClassyNode();
            if (cn.getName().equals(dijagramView.getClassyNode().getName()))
                item = c;
        }

        int flag = 0;
        for (Shape s : dijagramView.getSelectionModel()) {
            if (s.contains(new Point(x, y))) flag = 1;
        }

        AbstractCommand command;
        if(!dijagramView.getSelectionModel().isEmpty() && flag == 1){
            command = new MultipleDeleteCommand(x, y, dijagramView, item);
        }
        else{
            command = new SingleDeleteCommand(x, y, dijagramView, item);
        }
        ((DijagramView) MainFrame.getInstance().getPackageView().getjTabbedPane().getSelectedComponent()).getCommandManager().addCommand(command);
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
