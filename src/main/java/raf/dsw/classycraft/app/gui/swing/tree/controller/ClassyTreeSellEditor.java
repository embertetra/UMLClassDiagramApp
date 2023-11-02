package raf.dsw.classycraft.app.gui.swing.tree.controller;

import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;

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
        clicked.setName(e.getActionCommand());
    }
}
