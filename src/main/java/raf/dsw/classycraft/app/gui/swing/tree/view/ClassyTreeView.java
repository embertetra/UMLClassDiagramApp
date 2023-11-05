package raf.dsw.classycraft.app.gui.swing.tree.view;

import raf.dsw.classycraft.app.gui.swing.tree.controller.ClassyTreeSelectionListerer;
import raf.dsw.classycraft.app.gui.swing.tree.controller.ClassyTreeSellEditor;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;

public class ClassyTreeView extends JTree{


    public ClassyTreeView(DefaultTreeModel defaultTreeModel){
        setModel(defaultTreeModel);
        ClassyTreeCellRenderer classyTreeCellRenderer = new ClassyTreeCellRenderer();
        addTreeSelectionListener(new ClassyTreeSelectionListerer());
        setCellEditor(new ClassyTreeSellEditor(this, classyTreeCellRenderer));
        setCellRenderer(classyTreeCellRenderer);
        setEditable(true);
    }
}
