package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.controller.ActionManager;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.Message;
import raf.dsw.classycraft.app.errorHandler.MessageType;
import raf.dsw.classycraft.app.gui.swing.tree.ClassyTree;
import raf.dsw.classycraft.app.gui.swing.tree.ClassyTreeImplementation;
import raf.dsw.classycraft.app.observer.ISubscriber;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame implements ISubscriber {
    private static MainFrame instance;

    //sva view polja projekta
    private ActionManager actionManager;
    private AboutUsFrame aboutUsFrame;
    private ClassyTree classyTree;
    private MyMenyBar menu;
    private MyToolBar toolBar;
    private PackageView packageView;
    private DijagramView dijagramView;
    private ToolBarStates toolBarStates;

    private KlasaProzor klasaProzor;
    private InterfejsProzor interfejsProzor;
    private EnumProzor enumProzor;
    private List<PackageView> listaPackageView;
    private JSplitPane split;
    private JPanel desktop;
    private JScrollPane scrollPane;
    private KompAgregProzor kompAgregProzor;
    private DependencyProzor dependencyProzor;
    private GalleryTemplates galleryTemplates;


    private MainFrame() {
    }

    private void initialize() {
        galleryTemplates = new GalleryTemplates();
        //galleryTemplates.setVisible(true);
        actionManager = new ActionManager();
        aboutUsFrame = new AboutUsFrame();
        classyTree = new ClassyTreeImplementation();
        packageView = new PackageView();
        dijagramView = new DijagramView(null);
        toolBarStates = new ToolBarStates();
        klasaProzor = new KlasaProzor();
        interfejsProzor = new InterfejsProzor();
        enumProzor = new EnumProzor();
        listaPackageView = new ArrayList<>();
        kompAgregProzor = new KompAgregProzor();
        dependencyProzor = new DependencyProzor();

        //kompAgregProzor.setVisible(true);

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
        desktop = new JPanel();
        scrollPane = new JScrollPane(projectExplorer);
        scrollPane.setMinimumSize(new Dimension(200, 150));
        split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, desktop);

        split.add(packageView.getRightSide(), JSplitPane.RIGHT);

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

    public ToolBarStates getToolBarStates() {
        if(toolBarStates != null)
            return toolBarStates;
        else return new ToolBarStates();
    }

    public GalleryTemplates getGalleryTemplates() {
        return galleryTemplates;
    }

    public ActionManager getActionManager() {
        return actionManager;
    }

    public AboutUsFrame getAboutUsFrame() {
        return aboutUsFrame;
    }

    public ClassyTree getClassyTree() {
        return classyTree;
    }

    public PackageView getPackageView() {
        return packageView;
    }

    public KlasaProzor getKlasaProzor() {
        return klasaProzor;
    }

    public InterfejsProzor getInterfejsProzor() {
        return interfejsProzor;
    }

    public EnumProzor getEnumProzor() {
        return enumProzor;
    }

    public List<PackageView> getListaPackageView() {
        return listaPackageView;
    }

    public void setPackageView(PackageView packageView) {
        this.packageView = packageView;
        //split.add(packageView.getRightSide(), JSplitPane.RIGHT);
        split.setRightComponent(packageView.getRightSide());
    }

    public KompAgregProzor getKompAgregProzor() {
        return kompAgregProzor;
    }

    public DependencyProzor getDependencyProzor() {
        return dependencyProzor;
    }

    public DijagramView getDijagramView() {
        return dijagramView;
    }
}
