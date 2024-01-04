package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class ExportDiagram extends AbstractClassyAction{
    public ExportDiagram() {
        putValue(SMALL_ICON, loadIcon("/images/export.png"));
        putValue(NAME, "Export image");
        putValue(SHORT_DESCRIPTION, "Export image");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DijagramView d = (DijagramView) MainFrame.getInstance().getPackageView().getjTabbedPane().getSelectedComponent();
        if(d != null)
            d.exportImage();
    }
}
