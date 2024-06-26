package raf.dsw.classycraft.app.gui.swing.tree.controller;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.ProjectExplorer;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.MessageType;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Project;
import raf.dsw.classycraft.app.gui.swing.tree.ClassyTreeImplementation;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.jTabbedElements.NotificationDijagramView;
import raf.dsw.classycraft.app.jTabbedElements.NotificationJTabbed;

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

    //getTreeCellEditorComponent - metoda menja textBox na novi tekst (arg0 - gde se desilo, arg1 - obj na koji je kliknuto)
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
        ClassyNodeComposite cnc = (ClassyNodeComposite) clicked.getClassyNode().getParent();

        ///pokusaj promene imena PE
        if(clicked.getClassyNode() instanceof ProjectExplorer){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Ne mozete menjati ime ProjectExplorer-a", MessageType.ERROR);
            ClassyTreeImplementation cti = (ClassyTreeImplementation) MainFrame.getInstance().getClassyTree();
            SwingUtilities.updateComponentTreeUI(cti.getTreeView());
            return;
        }
        ///ako nam je ime isto kao sto je i bilo do sada
        else if(clicked.getClassyNode().getName().equals(e.getActionCommand())) {
            clicked.setName(e.getActionCommand());
            ClassyTreeImplementation cti = (ClassyTreeImplementation) MainFrame.getInstance().getClassyTree();
            SwingUtilities.updateComponentTreeUI(cti.getTreeView());
            return;
        }
        ///ako je unet prazan string
        else if(e.getActionCommand().equals("")){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Ne mozete uneti prazno ime", MessageType.INFO);
            return;
        }
        ///ako uneto ime vec postoji
        for(ClassyNode c: cnc.getChildren()){
            if(c.getName().equals(e.getActionCommand())){
                ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Uneto ime je vec u upotrebi!", MessageType.ERROR);
                return;
            }
        }
        ///promena imena za project u JTabbu
        ClassyTreeItem selected = MainFrame.getInstance().getClassyTree().getSelectedNode();
        if(selected.getClassyNode() instanceof Project){
            Project project = (Project) selected.getClassyNode();
            project.setNameInJTabb(new NotificationJTabbed(project, e.getActionCommand(), 4));
        }
        ///promena imena za dijagram na JTabbu
        else if(selected.getClassyNode() instanceof Dijagram){
            Dijagram dijagram = (Dijagram) selected.getClassyNode();
            dijagram.setNameInJTabb(new NotificationDijagramView(dijagram, e.getActionCommand(), old));
        }
        clicked.setName(e.getActionCommand());

        ClassyTreeImplementation cti = (ClassyTreeImplementation) MainFrame.getInstance().getClassyTree();
        SwingUtilities.updateComponentTreeUI(cti.getTreeView());
    }
}
