package raf.dsw.classycraft.app.stateSablon.stateActions;

import raf.dsw.classycraft.app.controller.AbstractClassyAction;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class AddContectAction extends AbstractClassyAction {

    public AddContectAction() {
        //putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/images/content.png"));
        putValue(NAME, "Dodaj sadrzaj");
        putValue(SHORT_DESCRIPTION, "Dodaj sadrzaj");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getPackageView().startAddContentState();
    }
}
