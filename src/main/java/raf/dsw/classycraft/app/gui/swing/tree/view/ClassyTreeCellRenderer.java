package raf.dsw.classycraft.app.gui.swing.tree.view;

import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Package;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Project;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.ProjectExplorer;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.net.URL;

public class ClassyTreeCellRenderer extends DefaultTreeCellRenderer {

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expand, boolean lead, int row, boolean hasFocus){
        super.getTreeCellRendererComponent(tree, value, sel, expand, lead, row, hasFocus);
        URL image = null;

        if(((ClassyTreeItem)value).getClassyNode() instanceof ProjectExplorer){
            //image = getClass().getResource("/images/");
        }
        else if(((ClassyTreeItem)value).getClassyNode() instanceof Project){

        }
        else if(((ClassyTreeItem)value).getClassyNode() instanceof Package){

        }
        else if(((ClassyTreeItem)value).getClassyNode() instanceof Dijagram){

        }

        Icon icon = null;
        if(image != null)
            icon = new ImageIcon(image);
        setIcon(icon);

        return this;
    }

}
