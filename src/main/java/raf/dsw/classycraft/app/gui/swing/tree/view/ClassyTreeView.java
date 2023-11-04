package raf.dsw.classycraft.app.gui.swing.tree.view;

import raf.dsw.classycraft.app.gui.swing.tree.controller.ClassyTreeSelectionListerer;
import raf.dsw.classycraft.app.gui.swing.tree.controller.ClassyTreeSellEditor;
import raf.dsw.classycraft.app.observer.IPublisher;
import raf.dsw.classycraft.app.observer.ISubscriber;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;
import java.util.List;

public class ClassyTreeView extends JTree{

    private List<ISubscriber> subscribers;

    public ClassyTreeView(DefaultTreeModel defaultTreeModel){
        setModel(defaultTreeModel);
        ClassyTreeCellRenderer classyTreeCellRenderer = new ClassyTreeCellRenderer();
        addTreeSelectionListener(new ClassyTreeSelectionListerer());
        setCellEditor(new ClassyTreeSellEditor(this, classyTreeCellRenderer));
        setCellRenderer(classyTreeCellRenderer);
        setEditable(true);
    }
}
