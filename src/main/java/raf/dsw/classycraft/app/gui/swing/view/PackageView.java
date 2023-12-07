package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Project;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;
import raf.dsw.classycraft.app.jTabbedElements.NotificationJTabbed;
import raf.dsw.classycraft.app.observer.ISubscriber;
import raf.dsw.classycraft.app.stateSablon.StateManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.tools.Tool;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PackageView implements ISubscriber {
    private JTabbedPane jTabbedPane;
    private JLabel nazivProjekta;
    private JLabel nazivAutora;
    private JPanel rightSide;
    private ClassyNode classyNode;
    private List<DijagramView> tabovi;
    private JPanel toolMenu;
    private JPanel downSide;
    private StateManager stateManager;
    private ClassyTreeItem classyTreeItem;

    public PackageView() {
        this.classyNode = null;
        stateManager = new StateManager();

        nazivProjekta = new JLabel("    ");
        nazivAutora = new JLabel("  ");
        nazivProjekta.setBorder(new EmptyBorder(new Insets(0, 0, 0, 50)));
        nazivAutora.setBorder(new EmptyBorder(new Insets(0, 0, 0, 50)));

        nazivProjekta.setAlignmentX(Component.CENTER_ALIGNMENT);
        nazivAutora.setAlignmentX(Component.CENTER_ALIGNMENT);

        jTabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);

        toolMenu = new JPanel();
        toolMenu.setLayout(new BoxLayout(toolMenu, BoxLayout.Y_AXIS));
        //toolMenu.add(MainFrame.getInstance().getToolBarStates());
        toolMenu.add(new ToolBarStates());
        toolMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
        toolMenu.setAlignmentY(Component.CENTER_ALIGNMENT);

        downSide = new JPanel();
        downSide.setLayout(new BoxLayout(downSide, BoxLayout.X_AXIS));
        downSide.add(jTabbedPane);
        downSide.add(toolMenu);

        rightSide = new JPanel();
        BoxLayout box = new BoxLayout(rightSide, BoxLayout.Y_AXIS);
        rightSide.setLayout(box);
        rightSide.add(nazivProjekta);
        rightSide.add(nazivAutora);
        rightSide.add(downSide);

    }

    public void addInTabList(DijagramView dijagramView) {
        if (tabovi == null) {
            tabovi = new ArrayList<>();
            tabovi.add(dijagramView);
        } else tabovi.add(dijagramView);
    }

    @Override
    public void update(Object notification) {
        NotificationJTabbed poruka = (NotificationJTabbed) notification;
        ClassyNodeComposite parent2 = poruka.getParent();
        int totalTabs;
        int brojac;

        ///Update dodatih dijagrama u stablo, prikaz u JTabbu
        if (poruka.getOznaka() == 0) {
            brojac = 0;
            if (this.classyNode != null && parent2.getName().equals(this.classyNode.getName())) {
                for (ClassyNode c : parent2.getChildren()) {
                    totalTabs = jTabbedPane.getTabCount();
                    for (int i = 0; i < totalTabs; i++) {
                        if (c.getName().equals(jTabbedPane.getTitleAt(i)) && c instanceof Dijagram) brojac = 1;
                    }
                    if (brojac == 0 && c instanceof Dijagram) {
                        DijagramView dijagramView = new DijagramView(c);
                        ((Dijagram) c).addSubscriber(dijagramView);
                        jTabbedPane.addTab(c.getName(), dijagramView);
                    }
                    brojac = 0;
                }
            }
        }
        ///Update obrisanih dijagrama u stablo, prikaz u JTabbu
        else if (poruka.getOznaka() == 1) {
            brojac = 0;
            if (this.classyNode != null && parent2.getName().equals(this.classyNode.getName())) {
                totalTabs = jTabbedPane.getTabCount();
                for (int i = 0; i < totalTabs; i++) {
                    for (ClassyNode c : parent2.getChildren()) {
                        if (jTabbedPane.getTitleAt(i).equals(c.getName())) brojac = 1;
                    }
                    if (brojac == 0) {
                        jTabbedPane.removeTabAt(i);
                    }
                    brojac = 0;
                    totalTabs = jTabbedPane.getTabCount();
                }
            }
        }
        ///update obrisanih podpaketa
        else if (poruka.getOznaka() == 2) {
            ClassyNode tmp = classyNode;
            if (classyNode != null) {
                while (!(tmp instanceof Project)) {
                    if (tmp.getName().equals(poruka.getParent().getName())) {
                        jTabbedPane.removeAll();
                        classyNode = null;
                        return;
                    }
                    tmp = tmp.getParent();
                }
            }
        }
        ///update brisanje paketa
        else if (poruka.getOznaka() == 3) {
            ClassyNode pck = poruka.getParent();
            if (classyNode != null) {
                ClassyNode tmp = classyNode;
                while (true) {
                    ClassyNode tmp2 = tmp.getParent();
                    if (tmp2 instanceof Project)
                        break;
                    else tmp = tmp.getParent();
                }

                if (tmp.getName().equals(pck.getName())) {
                    jTabbedPane.removeAll();
                    classyNode = null;
                }
            }
        }
        ///Update promena naziva projekta
        else if (poruka.getOznaka() == 4) {
            if (poruka.getParent().getName().equals(nazivProjekta.getText())) {
                nazivProjekta.setText(poruka.getNewName());
            }
        }
        ///Update brisanje projekta
        else if (poruka.getOznaka() == 5) {
            if (classyNode != null) {
                ClassyNode p = this.classyNode;
                while (true) {
                    if (p instanceof Project)
                        break;
                    else p = p.getParent();
                }
                if (p.getName().equals(poruka.getParent().getName())) {
                    jTabbedPane.removeAll();
                    classyNode = null;
                    nazivProjekta.setText("    ");
                    nazivAutora.setText("    ");
                }

            } else if (poruka.getParent().getName().equals(this.getNazivProjekta().getText())) {
                promeniNazivProjekta("    ");
                promeniNazivAutora("     ");
            }
        }
        ///Update promena autora
        else if (poruka.getOznaka() == 6) {
            if (classyNode != null) {
                ClassyNode p = this.classyNode;
                while (true) {
                    if (p instanceof Project)
                        break;
                    else p = p.getParent();
                }
                if (p.getName().equals(poruka.getParent().getName())) {
                    nazivAutora.setText(poruka.getNewName());
                }
            }
        }
    }

    //metode koje su unutar State intefrejsa

    public void misKliknut(int x, int y, DijagramView dijagramView){
        if(stateManager.getCurrentState() != null)
            stateManager.getCurrentState().misKliknut(x,y,dijagramView);
    }

    public void misOtpusten(int x, int y, DijagramView dijagramView){
        if(stateManager.getCurrentState() != null)
            stateManager.getCurrentState().misOtpusten(x,y,dijagramView);
    }

    public void misPrivucen(int x, int y, DijagramView dijagramView){
        if(stateManager.getCurrentState() != null)
            stateManager.getCurrentState().misPrivucen(x, y, dijagramView);
    }
    public void startMouseState(){
        if(stateManager.getCurrentState() != null) {
            stateManager.setMouse();
            if(jTabbedPane.getTabCount() > 0 &&((DijagramView) jTabbedPane.getSelectedComponent()).getSelectionModel() != null) {
                ((DijagramView) jTabbedPane.getSelectedComponent()).getSelectionModel().clear();
                ((DijagramView) jTabbedPane.getSelectedComponent()).setSelection(null);
                ((DijagramView) jTabbedPane.getSelectedComponent()).repaint();
            }
        }
    }

    public void startAddInterclassState() {
        stateManager.setAddInterclass();
    }

    public void startAddConnectionState() {
        stateManager.setAddConnection();
    }

    public void startAddContentState() {
        stateManager.setAddContent();
    }
    public void startMoveState(){
        stateManager.setMove();
    }

    public void startDeleteState() {
        stateManager.setDelete();
        if(jTabbedPane.getSelectedComponent()!=null) {
            ((DijagramView) jTabbedPane.getSelectedComponent()).setSelection(null);
            ((DijagramView) jTabbedPane.getSelectedComponent()).repaint();
        }
    }

    public void startSelectionState() {
        stateManager.setSelection();
    }

    public void startDuplicateState(){stateManager.setDuplicate();}

    public StateManager getStateManager() {
        return stateManager;
    }

    public void setClassyNode(ClassyNode classyNode) {
        this.classyNode = classyNode;
    }

    public void promeniNazivProjekta(String string) {
        nazivProjekta.setText(string);
    }

    public void promeniNazivAutora(String string) {
        nazivAutora.setText(string);
    }

    public JTabbedPane getjTabbedPane() {
        return jTabbedPane;
    }

    public JPanel getRightSide() {
        return rightSide;
    }

    public JLabel getNazivProjekta() {
        return nazivProjekta;
    }

    public ClassyNode getClassyNode() {
        return classyNode;
    }

    public JPanel getDownSide() {
        return downSide;
    }

    public ClassyTreeItem getClassyTreeItem() {
        return classyTreeItem;
    }

    public void setClassyTreeItem(ClassyTreeItem classyTreeItem) {
        this.classyTreeItem = classyTreeItem;
    }
}
