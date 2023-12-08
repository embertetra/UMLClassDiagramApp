package raf.dsw.classycraft.app.gui.swing.tree.view;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.DijagramElement;
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
        Image newImg = null;

        if(((ClassyTreeItem)value).getClassyNode() instanceof ProjectExplorer)
            image = getClass().getResource("/images/projectExplorer.png");

        else if(((ClassyTreeItem)value).getClassyNode() instanceof Project)
            image = getClass().getResource("/images/project.png");

        else if(((ClassyTreeItem)value).getClassyNode() instanceof Package)
            image = getClass().getResource("/images/package.png");

        else if(((ClassyTreeItem)value).getClassyNode() instanceof Dijagram)
            image = getClass().getResource("/images/dijagram.png");
        else if(((ClassyTreeItem)value).getClassyNode() instanceof DijagramElement)
            image = getClass().getResource("/images/dijagram.png");

        Image img = new ImageIcon(image).getImage();
        newImg = img.getScaledInstance(17, 17, Image.SCALE_DEFAULT);

        Icon icon = null;
        if(image != null)
            icon = new ImageIcon(newImg);
        setIcon(icon);

        return this;
    }

}
