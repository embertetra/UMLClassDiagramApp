package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.controller.ActionManager;
import raf.dsw.classycraft.app.errorHandler.Message;
import raf.dsw.classycraft.app.errorHandler.MessageGenerator;
import raf.dsw.classycraft.app.errorHandler.MessageType;
import raf.dsw.classycraft.app.observer.ISubscriber;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements ISubscriber {
    private static MainFrame instance;

    //sva view polja projekta

    ActionManager actionManager;
    AboutUsFrame aboutUsFrame;
    MessageGenerator messageGenerator;

    private MainFrame() {

    }

    private void initialize() {
        actionManager = new ActionManager();
        aboutUsFrame = new AboutUsFrame();
        messageGenerator = new MessageGenerator();
        messageGenerator.getSubscribers().add(this);

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

    @Override
    public void update(Object notification) {
        Message message = (Message) notification;
        if (message.getMessageType() == MessageType.INFO) {
            JOptionPane.showMessageDialog(MainFrame.getInstance(), message.getPoruka(), "INFO", JOptionPane.INFORMATION_MESSAGE);
        } else if (message.getMessageType() == MessageType.ERROR) {
            JOptionPane.showMessageDialog(MainFrame.getInstance(), message.getPoruka(), "ERROR", JOptionPane.ERROR_MESSAGE);
        } else if (message.getMessageType() == MessageType.WARNING) {
            JOptionPane.showMessageDialog(MainFrame.getInstance(), message.getPoruka(), "WARNING", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static MainFrame getInstance() {
        if (instance == null) {
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

    public void setActionManager(ActionManager actionManager) {
        this.actionManager = actionManager;
    }

    public MessageGenerator getMessageGenerator() {
        return messageGenerator;
    }

    public void setMessageGenerator(MessageGenerator messageGenerator) {
        this.messageGenerator = messageGenerator;
    }

}
