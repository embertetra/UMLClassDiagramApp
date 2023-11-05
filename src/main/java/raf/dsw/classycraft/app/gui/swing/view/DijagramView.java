package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.jTabbedElements.NotificationDijagramView;
import raf.dsw.classycraft.app.observer.ISubscriber;

import javax.swing.*;

public class DijagramView extends JPanel implements ISubscriber {

    ClassyNode classyNode;

    public DijagramView(ClassyNode classyNode) {
        if (classyNode != null) {
            this.classyNode = classyNode;
            Dijagram dijagram = (Dijagram) classyNode;
            dijagram.addSubscriber(this);
        }
    }

    @Override
    public void update(Object notification) {
        NotificationDijagramView poruka = (NotificationDijagramView) notification;
        Dijagram dijagram = (Dijagram) poruka.getChild();
        if (dijagram.getParent().equals(classyNode.getParent())) {
            int numTabs = MainFrame.getInstance().getPackageView().getjTabbedPane().getTabCount();
            for (int i = 0; i < numTabs; i++) {
                if (MainFrame.getInstance().getPackageView().getjTabbedPane().getTitleAt(i).equals(poruka.getOldName())) {
                    MainFrame.getInstance().getPackageView().getjTabbedPane().setTitleAt(i, poruka.getNewName());
                    classyNode.setName(poruka.getNewName());
                }
            }
        }
    }
}
