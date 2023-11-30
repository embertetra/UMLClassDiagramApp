package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseController implements MouseListener {
    private DijagramView dijagramView;

    public MouseController(DijagramView dijagramView) {
        this.dijagramView = dijagramView;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        MainFrame.getInstance().getPackageView().misKliknut(e.getX(), e.getY(), dijagramView);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        MainFrame.getInstance().getPackageView().misPrivucen(e.getX(), e.getY(), dijagramView);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        MainFrame.getInstance().getPackageView().misOtpusten(e.getX(),e.getY(), dijagramView);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
