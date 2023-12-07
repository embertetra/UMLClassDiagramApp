package raf.dsw.classycraft.app.controller.MouseListeners;

import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelListener;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

public class MouseController implements MouseListener {
    private DijagramView dijagramView;

    public MouseController(DijagramView dijagramView) {
        this.dijagramView = dijagramView;
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
}
