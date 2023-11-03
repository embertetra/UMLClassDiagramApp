package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.controller.ActionManager;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.Message;
import raf.dsw.classycraft.app.errorHandler.MessageType;
import raf.dsw.classycraft.app.gui.swing.tree.ClassyTree;
import raf.dsw.classycraft.app.gui.swing.tree.ClassyTreeImplementation;
import raf.dsw.classycraft.app.gui.swing.tree.view.ClassyTreeView;
import raf.dsw.classycraft.app.observer.ISubscriber;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements ISubscriber {
    private static MainFrame instance;

    //sva view polja projekta

    private ActionManager actionManager;
    private AboutUsFrame aboutUsFrame;
    private ClassyTree classyTree;
    private MyMenyBar menu;
    private MyToolBar toolBar;


    private MainFrame() {

    }

    private void initialize() {
        actionManager = new ActionManager();
        aboutUsFrame = new AboutUsFrame();
        classyTree = new ClassyTreeImplementation();
        //packageOrDiagram.setVisible(true);

        ApplicationFramework.getInstance().getMessageGenerator().getSubscribers().add(this);


        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth / 2, screenHeight / 2);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("ClassyCrafT");

        menu = new MyMenyBar();
        setJMenuBar(menu);

        toolBar = new MyToolBar();
        add(toolBar, BorderLayout.NORTH);

        JTree projectExplorer = classyTree.generateTree(ApplicationFramework.getInstance().getClassyRepository().getRoot());
        JPanel desktop = new JPanel();

        JScrollPane scrollPane = new JScrollPane(projectExplorer);
        scrollPane.setMinimumSize(new Dimension(200, 150));
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, desktop);
        getContentPane().add(split, BorderLayout.CENTER);
        split.setDividerLocation(250);
        split.setOneTouchExpandable(true);
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

    public ClassyTree getClassyTree() {
        return classyTree;
    }

    public void setClassyTree(ClassyTree classyTree) {
        this.classyTree = classyTree;
    }

    public MyMenyBar getMenu() {
        return menu;
    }

    public MyToolBar getToolBar() {
        return toolBar;
    }
}
