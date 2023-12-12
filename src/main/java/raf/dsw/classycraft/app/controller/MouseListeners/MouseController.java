package raf.dsw.classycraft.app.controller.MouseListeners;

import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

public class MouseController implements MouseMotionListener,MouseListener, MouseWheelListener {
    private DijagramView dijagramView;

    public MouseController(DijagramView dijagramView) {
        this.dijagramView = dijagramView;
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        Point2D point = e.getPoint();
        try {
            point = dijagramView.getAt().inverseTransform(e.getPoint(), null);
        } catch (NoninvertibleTransformException ex) {
            throw new RuntimeException(ex);
        }
        MainFrame.getInstance().getPackageView().misDragged((int) point.getX(), (int) point.getY(), dijagramView);
    }
    @Override
    public void mouseClicked(MouseEvent e) {

        Point2D point = e.getPoint();
        try {
            point = dijagramView.getAt().inverseTransform(e.getPoint(), null);
        } catch (NoninvertibleTransformException ex) {
            throw new RuntimeException(ex);
        }

        MainFrame.getInstance().getPackageView().misKliknut((int) point.getX(), (int)point.getY(), dijagramView);
        //MainFrame.getInstance().getPackageView().misKliknut(e.getX(), e.getY(), dijagramView);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point2D point = e.getPoint();
        try {
            point = dijagramView.getAt().inverseTransform(e.getPoint(), null);
        } catch (NoninvertibleTransformException ex) {
            throw new RuntimeException(ex);
        }

        MainFrame.getInstance().getPackageView().misPrivucen((int) point.getX(), (int) point.getY(), dijagramView);
        //MainFrame.getInstance().getPackageView().misPrivucen(e.getX(), e.getY(), dijagramView);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Point2D point = e.getPoint();
        try {
            point = dijagramView.getAt().inverseTransform(e.getPoint(), null);
        } catch (NoninvertibleTransformException ex) {
            throw new RuntimeException(ex);
        }
        MainFrame.getInstance().getPackageView().misOtpusten((int) point.getX(), (int) point.getY(), dijagramView);
        //MainFrame.getInstance().getPackageView().misOtpusten(e.getX(),e.getY(), dijagramView);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }



    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        dijagramView.setZoomer(true);
        if(e.getWheelRotation() > 0) {
            dijagramView.setZoomFactor(Math.max(dijagramView.getZoomFactor() / 1.1, 0.3));
        }
        else if(e.getWheelRotation() < 0) {
            dijagramView.setZoomFactor(Math.min(dijagramView.getZoomFactor() * 1.1, 2.0));
        }

        dijagramView.repaint();
    }
}
