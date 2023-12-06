package raf.dsw.classycraft.app.gui.swing.view;

import javafx.util.Pair;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Connection;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.gui.swing.view.painters.ConnectionPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.ElementPainter;
import raf.dsw.classycraft.app.controller.MouseListeners.MouseController;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;
import raf.dsw.classycraft.app.jTabbedElements.NotificationDijagramView;
import raf.dsw.classycraft.app.observer.ISubscriber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
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
    private MouseMotionListener translation;
    private InterclassPainter flag1;
    private List<InterclassPainter> moveSelections;


    ///

    private Point startPoint;
    private double xOffset;
    private double yOffset;
    private int xDragOffset;
    private int yDragOffset;
    private double zoomFactor = 1;
    private double prevZoomFactor = 1;
    private boolean zoomer;
    private MouseWheelListener mouseWheelListener;


    ///
    public DijagramView(ClassyNode classyNode) {
        if (classyNode != null) {
            this.classyNode = classyNode;
        }
        this.addMouseListener(new MouseController(this));
        elementPainterList = new ArrayList<>();
        selectionModel = new ArrayList<>();

        createMML();
        createSelectionListener();
        createTranslation();
        createMouseWheelListener();
        this.addMouseWheelListener(mouseWheelListener);
    }

    public void createMouseWheelListener() {
        mouseWheelListener = new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {

                zoomer = true;
                if (e.getWheelRotation() < 0) {
                    zoomFactor = Math.max(zoomFactor / 1.1, 0.3);
                } else if (e.getWheelRotation() > 0) {
                    zoomFactor = Math.min(zoomFactor * 1.1, 2.0);
                }
                repaint();
            }
        };
    }

    public void setMouseWheelListener() {
        this.addMouseWheelListener(mouseWheelListener);
    }

    public void removeMouseWheelListener() {
        this.removeMouseWheelListener(mouseWheelListener);
    }

    public void createTranslation() {
        translation = new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

                if (startPoint != null) {
                    int diffX = e.getX() - startPoint.x;
                    int diffY = e.getY() - startPoint.y;

                    if (flag1 != null) {
                        for (ElementPainter ep : elementPainterList) {
                            if (ep instanceof InterclassPainter) {
                                InterclassPainter ip = (InterclassPainter) ep;
                                Interclass ic = (Interclass) ip.getElement();
                                if (ip == flag1) {
                                    ic.setPosition(new Point(startPoint.x + diffX - xDragOffset, startPoint.y + diffY - yDragOffset));
                                    repaint();//
                                    return;
                                }
                            }
                        }
                    }
                    else if(moveSelections != null && moveSelections.size()>0){
                        for(InterclassPainter ip : moveSelections){
                            Interclass ic = (Interclass) ip.getElement();
                            ic.setPosition(new Point(startPoint.x + diffX - ip.getxDragOffset(),
                                    startPoint.y + diffY - ip.getyDragOffset()));
                        }
                        repaint();
                        return;
                    }
                    else {

                        for (ElementPainter ep : getElementPainterList()) {
                            if (ep instanceof InterclassPainter) {
                                InterclassPainter ip = ((InterclassPainter) ep);
                                ((Interclass) ip.getElement()).setPosition(new Point(startPoint.x + diffX - ip.getxDragOffset(),
                                        startPoint.y + diffY - ip.getyDragOffset()));
                            }
                        }
                        for (ElementPainter ep : getElementPainterList()) {
                            if (ep instanceof ConnectionPainter) {
                                ConnectionPainter cp = (ConnectionPainter) ep;
                                Interclass from = ((Connection) cp.getElement()).getFrom();
                                Interclass to = ((Connection) cp.getElement()).getTo();
                                from.setPosition(new Point(startPoint.x + diffX - cp.getDragOffset1().x,
                                        startPoint.y + diffY - cp.getDragOffset1().y));
                                to.setPosition(new Point(startPoint.x + diffX - cp.getDragOffset2().x,
                                        startPoint.y + diffY - cp.getDragOffset2().y));
                            }
                        }
                    }
                }
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        };
    }

    public void setTranslation() {
        addMouseMotionListener(translation);
    }

    public void removeTranslation() {
        removeMouseMotionListener(translation);
    }

    private void createSelectionListener() {
        selectionListener = new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (selection != null) {
                    shape = new GeneralPath();
                    ((GeneralPath) shape).moveTo(selection.x, selection.y);
                    ((GeneralPath) shape).lineTo(e.getX(), selection.y);
                    ((GeneralPath) shape).lineTo(e.getX(), e.getY());
                    ((GeneralPath) shape).lineTo(selection.x, e.getY());
                    ((GeneralPath) shape).closePath();
                    repaint();
                }
                selectionModel.clear();
                for (ElementPainter el : elementPainterList) {
                    if (el instanceof InterclassPainter) {
                        InterclassPainter ip = (InterclassPainter) el;
                        Interclass ic = (Interclass) ip.getElement();
                        Rectangle rec = new Rectangle(ic.getPosition().x - ip.getWidth() / 2 - 5, ic.getPosition().y - ip.getHeightUkupno() / 2,
                                ip.getWidth() + 10, ip.getHeightUkupno() + 10);
                        if (shape != null && shape.intersects(rec))
                            selectionModel.add(new Rectangle(rec.x - 5, rec.y - 5, rec.width + 10, rec.height + 10));
                    } else if (el instanceof ConnectionPainter) {
                        ConnectionPainter cp = (ConnectionPainter) el;
                        if (shape != null && cp.getPoint1() != null && cp.getPoint2() != null && shape.getBounds2D().intersectsLine(cp.getPoint1().x, cp.getPoint1().y, cp.getPoint2().x, cp.getPoint2().y)) {
                            Shape shape2 = new GeneralPath();
                            ((GeneralPath) shape2).moveTo(cp.getPoint1().x, cp.getPoint1().y);
                            ((GeneralPath) shape2).lineTo(cp.getPoint1().x, cp.getPoint2().y);
                            ((GeneralPath) shape2).lineTo(cp.getPoint2().x, cp.getPoint2().y);
                            ((GeneralPath) shape2).lineTo(cp.getPoint2().x, cp.getPoint1().y);
                            ((GeneralPath) shape2).lineTo(cp.getPoint1().x, cp.getPoint1().y);
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

    private void createMML() {
        mml = new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (line != null && line.getKey() != null) {
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


        ///

        if (zoomer) {
            double xRel = MouseInfo.getPointerInfo().getLocation().x - getLocationOnScreen().x;
            double yRel = MouseInfo.getPointerInfo().getLocation().y - getLocationOnScreen().y;
            double zoomDiv = zoomFactor / prevZoomFactor;
            xOffset = (zoomDiv) * (xOffset) + (1 - zoomDiv) * xRel;
            yOffset = (zoomDiv) * (yOffset) + (1 - zoomDiv) * yRel;
            prevZoomFactor = zoomFactor;
            zoomer = false;
        }
        AffineTransform at = new AffineTransform();
        at.translate(xOffset, yOffset);
        at.scale(zoomFactor, zoomFactor);
        g2.transform(at);

        ///


        if (line != null && line.getKey() != null && line.getValue() != null && line.getKey().x != -1 && line.getKey().y != -1)
            g2.drawLine(line.getKey().x, line.getKey().y, line.getValue().x, line.getValue().y);

        g2.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
        g2.setPaint(Color.GRAY);
        if (selection != null)
            g2.draw(selection);

        if (shape != null)
            g2.draw(shape);

        if(selectionModel != null)
            for (Shape s : selectionModel)
                g2.draw(s);

        for (ElementPainter x : elementPainterList) {
            x.draw(g2);
        }
        System.out.println("Izvrsen paintComponent");
    }

    public void setMML() {
        this.addMouseMotionListener(mml);
    }

    public void removeMML() {
        this.removeMouseMotionListener(mml);
    }

    public void setSelectionListener() {
        this.addMouseMotionListener(selectionListener);
    }

    public void removeSelectionListener() {
        this.removeMouseMotionListener(selectionListener);
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


    ///

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
}
