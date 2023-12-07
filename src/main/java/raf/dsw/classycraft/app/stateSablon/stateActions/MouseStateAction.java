package raf.dsw.classycraft.app.stateSablon.stateActions;

import raf.dsw.classycraft.app.controller.AbstractClassyAction;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class MouseStateAction extends AbstractClassyAction {
    public MouseStateAction() {
        putValue(SMALL_ICON, loadIcon("/images/click.png"));
        putValue(NAME, "Odselektuj");
        putValue(SHORT_DESCRIPTION, "Odselektuj");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getPackageView().startMouseState();
    }
}
