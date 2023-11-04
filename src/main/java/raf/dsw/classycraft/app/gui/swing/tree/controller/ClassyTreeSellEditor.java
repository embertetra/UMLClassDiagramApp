package raf.dsw.classycraft.app.gui.swing.tree.controller;

import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Project;
import raf.dsw.classycraft.app.gui.swing.tree.ClassyTreeImplementation;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

public class ClassyTreeSellEditor extends DefaultTreeCellEditor implements ActionListener {

    private Object clickedOn = null;
    private JTextField edit = null;

    public ClassyTreeSellEditor(JTree arg0, DefaultTreeCellRenderer arg1) {
        super(arg0, arg1);
    }

    //isCellEditable - sta treba da se desi da bi se pokrenuo editabilni mod
    public boolean isCellEditable(EventObject arg0){
        if(arg0 instanceof MouseEvent)
            if(((MouseEvent)arg0).getClickCount() == 3)
                return true;
        return false;
    }

    //getTreeCellEditorComponent - metoda menja textBox na novi tekst (arg0 - gde se desilo, arg1 - na sta je kliknuto)
    public Component getTreeCellEditorComponent(JTree arg0, Object arg1, boolean arg2, boolean arg3, boolean agr4, int arg5){
        super.getTreeCellEditorComponent(arg0, arg1, arg2, arg3, agr4, arg5);
        clickedOn = arg1;
        edit = new JTextField(arg1.toString());
        edit.addActionListener(this);
        return edit;
    }

    //actionPerformed - klikom na ENTER izvrsava se promena (setuje se ime, vraca se ono sto smo ukucali)
    public void actionPerformed(ActionEvent e){
        if(!(clickedOn instanceof ClassyTreeItem))
            return;

        ClassyTreeItem clicked = (ClassyTreeItem) clickedOn;
        String old = clicked.getClassyNode().getName();
        clicked.setName(e.getActionCommand());

        ///promena imena za project u JTabbu
        ClassyTreeItem selected = MainFrame.getInstance().getClassyTree().getSelectedNode();
        if(selected.getClassyNode() instanceof Project)
            MainFrame.getInstance().getPackageView().promeniNazivProjekta(e.getActionCommand());

        ///promena imena za dijagram na JTabbu
        if(selected.getClassyNode() instanceof Dijagram){
            int totalTabs = MainFrame.getInstance().getPackageView().getjTabbedPane().getTabCount();
            for (int i=0;i<totalTabs;i++)
                if(MainFrame.getInstance().getPackageView().getjTabbedPane().getTitleAt(i).equals(old))
                    MainFrame.getInstance().getPackageView().getjTabbedPane().setTitleAt(i, e.getActionCommand());
        }


        //obraditi slucaj ako je uneto vec postojece ime



        ClassyTreeImplementation cti = (ClassyTreeImplementation) MainFrame.getInstance().getClassyTree();
        SwingUtilities.updateComponentTreeUI(cti.getTreeView());
    }
}
