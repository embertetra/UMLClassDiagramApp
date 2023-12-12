package raf.dsw.classycraft.app.gui.swing.view;

import javafx.util.Pair;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.controller.MouseListeners.*;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;
import raf.dsw.classycraft.app.jTabbedElements.NotificationDijagramView;
import raf.dsw.classycraft.app.observer.ISubscriber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class DijagramView extends JPanel implements ISubscriber {

    ClassyNode classyNode;
    private List<ElementPainter> elementPainterList;
    private Pair<Point, Point> line;
    private Rectangle selection;
    private Shape shape;
    private List<Shape> selectionModel;
    private InterclassPainter flag1;
    private List<InterclassPainter> moveSelections;

    /// zoom polja
    private Point startPoint;
    private double xOffset;
    private double yOffset;
    private int xDragOffset;
    private int yDragOffset;
    private double zoomFactor = 1;
    private double prevZoomFactor = 1;
    private boolean zoomer;
    private MouseController mouseController;
    AffineTransform at = new AffineTransform();
    public DijagramView(ClassyNode classyNode) {
        if (classyNode != null) {
            this.classyNode = classyNode;
        }

        mouseController = new MouseController(this);
        this.addMouseListener(mouseController);
        this.addMouseMotionListener(mouseController);
        this.addMouseWheelListener(mouseController);
        elementPainterList = new ArrayList<>();
        selectionModel = new ArrayList<>();
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
        //AffineTransform save = ((Graphics2D) g).getTransform();
        if (zoomer) {
            double xRel = MouseInfo.getPointerInfo().getLocation().x - getLocationOnScreen().x;
            double yRel = MouseInfo.getPointerInfo().getLocation().y - getLocationOnScreen().y;
            double zoomDiv = zoomFactor / prevZoomFactor;
            xOffset = (zoomDiv) * (xOffset) + (1 - zoomDiv) * xRel;
            yOffset = (zoomDiv) * (yOffset) + (1 - zoomDiv) * yRel;
            prevZoomFactor = zoomFactor;
            zoomer = false;
        }

        at = new AffineTransform();
        at.translate(xOffset, yOffset);
        at.scale(zoomFactor, zoomFactor);
        g2.transform(at);

        ///crtanje veze na dragged mouse
        if (line != null && line.getKey() != null && line.getValue() != null && line.getKey().x != -1 && line.getKey().y != -1)
            g2.drawLine(line.getKey().x, line.getKey().y, line.getValue().x, line.getValue().y);

        g2.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
        g2.setPaint(Color.GRAY);
        ///crtanje jedne selekcije
        if (selection != null)
            g2.draw(selection);

        ///crtanje lasso pravugaonika
        if (shape != null)
            g2.draw(shape);

        ///crtanje selekcije svakog selektovanog unutar lasso
        if(selectionModel != null)
            for (Shape s : selectionModel)
                g2.draw(s);

        ///iscrtavanje paintera
        for (ElementPainter x : elementPainterList) {
            x.draw(g2);
        }
        System.out.println("Izvrsen paintComponent");
        //((Graphics2D) g).setTransform(save);
    }
    public List<ElementPainter> getElementPainterList() {
        return elementPainterList;
    }
    public ClassyNode getClassyNode() {
        return classyNode;
    }
    public void setLine(Pair<Point, Point> line) {
        this.line = line;
    }
    public void setSelection(Rectangle selection) {
        this.selection = selection;
    }
    public Rectangle getSelection() {
        return selection;
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
    public Point getStartPoint() {
        return startPoint;
    }
    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }
    public void setFlag1(InterclassPainter flag1) {
        this.flag1 = flag1;
    }
    public InterclassPainter getFlag1() {
        return flag1;
    }
    public void setMoveSelections(List<InterclassPainter> moveSelections) {
        this.moveSelections = moveSelections;
    }
    public List<InterclassPainter> getMoveSelections() {
        return moveSelections;
    }
    public void setSelectionModel(List<Shape> selectionModel) {
        this.selectionModel = selectionModel;
    }
    public Pair<Point, Point> getLine() {
        return line;
    }
    public int getxDragOffset() {
        return xDragOffset;
    }
    public int getyDragOffset() {
        return yDragOffset;
    }
    public double getZoomFactor() {
        return zoomFactor;
    }
    public void setZoomFactor(double zoomFactor) {
        this.zoomFactor = zoomFactor;
    }
    public void setZoomer(boolean zoomer) {
        this.zoomer = zoomer;
    }

    public AffineTransform getAt() {
        return at;
    }
}
