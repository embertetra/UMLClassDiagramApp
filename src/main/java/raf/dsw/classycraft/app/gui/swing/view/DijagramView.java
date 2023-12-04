package raf.dsw.classycraft.app.gui.swing.view;

import javafx.util.Pair;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.controller.MouseController;
import raf.dsw.classycraft.app.jTabbedElements.NotificationDijagramView;
import raf.dsw.classycraft.app.observer.ISubscriber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class DijagramView extends JPanel implements ISubscriber {

    ClassyNode classyNode;
    private List<ElementPainter> elementPainterList;
    private Pair<Point, Point> line;
    private MouseMotionListener mml;
    public DijagramView(ClassyNode classyNode) {
        if (classyNode != null) {
            this.classyNode = classyNode;
        }
        this.addMouseListener(new MouseController(this));
        elementPainterList = new ArrayList<>();

        createMML();
    }
    private void createMML(){
        mml = new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(line!=null && line.getKey() != null) {
                    line = new Pair<>(line.getKey(), new Point(e.getX(), e.getY()));
                    repaint();
                }
            }
            @Override
            public void mouseMoved(MouseEvent e) {
            }
        };
    }
    public void setMML(){
        this.addMouseMotionListener(mml);
    }
    public void removeMML(){
        this.removeMouseMotionListener(mml);
    }
    @Override
    public void update(Object notification) {

        if (notification.equals("state")) {
            repaint();
            return;
        }

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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if(line != null && line.getKey()!= null && line.getValue() != null && line.getKey().x != -1 && line.getKey().y != -1)
            g2.drawLine(line.getKey().x, line.getKey().y, line.getValue().x, line.getValue().y);
        for (ElementPainter x : elementPainterList) {
            x.draw(g2);
        }
        System.out.println("Izvrsen paintComponent");


    }



    public List<ElementPainter> getElementPainterList() {
        return elementPainterList;
    }

    public void setElementPainterList(List<ElementPainter> elementPainterList) {
        this.elementPainterList = elementPainterList;
    }

    public ClassyNode getClassyNode() {
        return classyNode;
    }

    public Pair<Point, Point> getLine() {
        return line;
    }

    public void setLine(Pair<Point, Point> line) {
        this.line = line;
    }
}
