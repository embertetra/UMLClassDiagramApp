package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.gui.swing.view.AboutUsFrame;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.awt.event.ActionEvent;

public class AboutUsAction extends AbstractClassyAction{

    public AboutUsAction(){

        //putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        putValue(SMALL_ICON, LoadIcon("/images/info.png"));
        putValue(NAME, "About us");
        putValue(SHORT_DESCRIPTION, "About us");

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().getAboutUsFrame().setVisible(true);
    }
}
