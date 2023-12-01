package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.DijagramElement;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Klasa;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.KlasaPainter;
import raf.dsw.classycraft.app.controller.MouseController;
import raf.dsw.classycraft.app.jTabbedElements.NotificationDijagramView;
import raf.dsw.classycraft.app.observer.ISubscriber;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.List;

public class DijagramView extends JPanel implements ISubscriber {

    ClassyNode classyNode;

    private List<ElementPainter> elementPainterList;

    public DijagramView(ClassyNode classyNode) {
        if (classyNode != null) {
            this.classyNode = classyNode;
        }
        this.addMouseListener(new MouseController(this));
    }

    @Override
    public void update(Object notification) {
        NotificationDijagramView poruka = (NotificationDijagramView) notification;
        Dijagram dijagram = (Dijagram) poruka.getChild();
        if (classyNode != null) {
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
////////////////////////////////////////////
    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);

        Graphics2D g2 = (Graphics2D) g;


        for(ElementPainter x : elementPainterList)
            x.draw(g2);

        System.out.println("Izvrsen paintComponent");
    }

    public List<ElementPainter> getElementPainterList() {
        return elementPainterList;
    }

    public void setElementPainterList(List<ElementPainter> elementPainterList) {
        this.elementPainterList = elementPainterList;
    }
}
