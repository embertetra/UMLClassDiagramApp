package raf.dsw.classycraft.app.stateSablon.stateActions;

import raf.dsw.classycraft.app.controller.AbstractClassyAction;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AddConnectionAction extends AbstractClassyAction {

    public AddConnectionAction() {
        //putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        putValue(SMALL_ICON, loadIcon("/images/connection.png"));
        putValue(NAME, "");
        putValue(SHORT_DESCRIPTION, "");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(MainFrame.getInstance().getPackageView().getParent() != null && MainFrame.getInstance().getPackageView().getjTabbedPane().getTabCount() > 0) {

            Object[] options = {"Agregacija", "Generalizacija", "Kompozicija", "Zavisnost", "Asocijacija"};
            int n = JOptionPane.showOptionDialog(MainFrame.getInstance(), "Odaberite vezu:", "", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (n == 0)
                MainFrame.getInstance().getPackageView().getStateManager().getAddConnection().setConnection("agregacija");
            else if (n == 1)
                MainFrame.getInstance().getPackageView().getStateManager().getAddConnection().setConnection("generalizacija");
            else if (n == 2)
                MainFrame.getInstance().getPackageView().getStateManager().getAddConnection().setConnection("kompozicija");
            else if (n == 3)
                MainFrame.getInstance().getPackageView().getStateManager().getAddConnection().setConnection("zavisnost");
            else if (n == 4)
                MainFrame.getInstance().getPackageView().getStateManager().getAddConnection().setConnection("asocijacija");


            MainFrame.getInstance().getPackageView().startAddConnectionState();
        }
    }
}
