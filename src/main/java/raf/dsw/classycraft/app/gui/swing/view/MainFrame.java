package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.controller.ActionManager;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private static MainFrame instance;

    //buduca polja za sve komponente view-a na glavnom prozoru

    ActionManager actionManager;
    AboutUsFrame aboutUsFrame;

    private MainFrame(){

    }

    private void initialize(){
        actionManager = new ActionManager();
        aboutUsFrame = new AboutUsFrame();

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth / 2, screenHeight / 2);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("ClassyCrafT");

        MyMenyBar menu = new MyMenyBar();
        setJMenuBar(menu);

        MyToolBar toolBar = new MyToolBar();
        add(toolBar, BorderLayout.NORTH);



    }

    public static MainFrame getInstance()
    {
        if(instance == null)
        {
            instance = new MainFrame();
            instance.initialize();
        }
        return instance;
    }

    public ActionManager getActionManager() {
        return actionManager;
    }

    public AboutUsFrame getAboutUsFrame() {
        return aboutUsFrame;
    }
}
