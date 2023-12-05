package raf.dsw.classycraft.app.gui.swing.view;

import javafx.util.Pair;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.gui.swing.view.painters.ConnectionPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.controller.MouseController;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;
import raf.dsw.classycraft.app.jTabbedElements.NotificationDijagramView;
import raf.dsw.classycraft.app.observer.ISubscriber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;

public class DijagramView extends JPanel implements ISubscriber {

    ClassyNode classyNode;
    private List<ElementPainter> elementPainterList;
    private Pair<Point, Point> line;
    private Rectangle selection;
    private Shape shape;
    private List<Shape> selectionModel;
    private MouseMotionListener mml;
    private MouseMotionListener selectionListener;
    public DijagramView(ClassyNode classyNode) {
        if (classyNode != null) {
            this.classyNode = classyNode;
        }
        this.addMouseListener(new MouseController(this));
        elementPainterList = new ArrayList<>();
        selectionModel = new ArrayList<>();

        createMML();
        createSelectionListener();
    }
    private void createSelectionListener(){
        selectionListener = new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(selection != null) {
                    shape = new GeneralPath();
                    ((GeneralPath)shape).moveTo(selection.x, selection.y);
                    ((GeneralPath)shape).lineTo(e.getX(), selection.y);
                    ((GeneralPath)shape).lineTo(e.getX(), e.getY());
                    ((GeneralPath)shape).lineTo(selection.x, e.getY());
                    ((GeneralPath)shape).closePath();
                    repaint();
                }
                selectionModel.clear();
                for(ElementPainter el : elementPainterList){
                    if(el instanceof InterclassPainter){
                        InterclassPainter ip = (InterclassPainter) el;
                        Interclass ic = (Interclass)ip.getElement();
                        Rectangle rec = new Rectangle(ic.getPosition().x - ip.getWidth()/2 - 5, ic.getPosition().y-ip.getHeightUkupno()/2,
                                ip.getWidth()+10, ip.getHeightUkupno()+10);
                        if(shape != null && shape.intersects(rec))
                            selectionModel.add(new Rectangle(rec.x - 5, rec.y - 5, rec.width + 10, rec.height + 10));
                    }
                    else if(el instanceof ConnectionPainter){
                        ConnectionPainter cp = (ConnectionPainter) el;
                        if(shape != null && cp.getPoint1()!= null && cp.getPoint2() != null && shape.getBounds2D().intersectsLine(cp.getPoint1().x, cp.getPoint1().y, cp.getPoint2().x, cp.getPoint2().y)){
                            Shape shape2 = new GeneralPath();
                            ((GeneralPath)shape2).moveTo(cp.getPoint1().x, cp.getPoint1().y);
                            ((GeneralPath)shape2).lineTo(cp.getPoint1().x, cp.getPoint2().y);
                            ((GeneralPath)shape2).lineTo(cp.getPoint2().x, cp.getPoint2().y);
                            ((GeneralPath)shape2).lineTo(cp.getPoint2().x, cp.getPoint1().y);
                            ((GeneralPath)shape2).lineTo(cp.getPoint1().x, cp.getPoint1().y);
                            selectionModel.add(shape2);
                        }
                    }
                }
            }
            @Override
            public void mouseMoved(MouseEvent e) {
            }
        };
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

        g2.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
        g2.setPaint(Color.GRAY);
        if(selection != null)
            g2.draw(selection);

        if(shape != null)
            g2.draw(shape);

        for(Shape s : selectionModel)
            g2.draw(s);

        for (ElementPainter x : elementPainterList) {
            x.draw(g2);
        }
        System.out.println("Izvrsen paintComponent");
    }
    public void setMML(){
        this.addMouseMotionListener(mml);
    }
    public void removeMML(){
        this.removeMouseMotionListener(mml);
    }
    public void setSelectionListener(){
        this.addMouseMotionListener(selectionListener);
    }
    public void removeSelectionListener(){
        this.addMouseMotionListener(selectionListener);
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

    public Rectangle getSelection() {
        return selection;
    }

    public void setSelection(Rectangle selection) {
        this.selection = selection;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public List<Shape> getSelectionModel() {
        return selectionModel;
    }

    public void setSelectionModel(List<Shape> selectionModel) {
        this.selectionModel = selectionModel;
    }
}
