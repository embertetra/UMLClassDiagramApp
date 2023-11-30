package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Project;
import raf.dsw.classycraft.app.jTabbedElements.NotificationJTabbed;
import raf.dsw.classycraft.app.observer.ISubscriber;
import raf.dsw.classycraft.app.stateSablon.StateManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PackageView implements ISubscriber {
    private JTabbedPane jTabbedPane;
    private JLabel nazivProjekta;
    private JLabel nazivAutora;
    private JPanel rightSide;
    private ClassyNode parent;
    private List<DijagramView> tabovi;
    private JPanel toolMenu;
    private JPanel downSide;
    private StateManager stateManager;

    public PackageView() {
        this.parent = null;
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
        /*toolMenu.add(MainFrame.getInstance().getActionManager().getAddInterclassAction());
        toolMenu.add(new JLabel("stanje2"));
        toolMenu.add(new JLabel("stanje3"));
        toolMenu.add(new JLabel("stanje4"));*/
        toolMenu.add(MainFrame.getInstance().getToolBarStates());
        toolMenu.setAlignmentY(Component.TOP_ALIGNMENT);

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
    public void test(){
        stateManager.getCurrentState().test();
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
            if (this.parent != null && parent2.getName().equals(this.parent.getName())) {
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
            if (this.parent != null && parent2.getName().equals(this.parent.getName())) {
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
            ClassyNode tmp = parent;
            if (parent != null) {
                while (!(tmp instanceof Project)) {
                    if (tmp.getName().equals(poruka.getParent().getName())) {
                        jTabbedPane.removeAll();
                        parent = null;
                        return;
                    }
                    tmp = tmp.getParent();
                }
            }
        }
        ///update brisanje paketa
        else if (poruka.getOznaka() == 3) {
            ClassyNode pck = poruka.getParent();
            if (parent != null) {
                ClassyNode tmp = parent;
                while (true) {
                    ClassyNode tmp2 = tmp.getParent();
                    if (tmp2 instanceof Project)
                        break;
                    else tmp = tmp.getParent();
                }

                if (tmp.getName().equals(pck.getName())) {
                    jTabbedPane.removeAll();
                    parent = null;
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
            if (parent != null) {
                ClassyNode p = this.parent;
                while (true) {
                    if (p instanceof Project)
                        break;
                    else p = p.getParent();
                }
                if (p.getName().equals(poruka.getParent().getName())) {
                    jTabbedPane.removeAll();
                    parent = null;
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
            if (parent != null) {
                ClassyNode p = this.parent;
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


    ///

    public void startAddInterclassState() {
        stateManager.setAddInterclass();
    }

    public void startAddConnectionState() {
        stateManager.setAddConnection();
    }

    public void startAddContentState() {
        stateManager.setAddContent();
    }

    public void startDeleteState() {
        stateManager.setDelete();
    }

    public void startSelectionState() {
        stateManager.setSelection();
    }

    public StateManager getStateManager() {
        return stateManager;
    }

    public void setParent(ClassyNode parent) {
        this.parent = parent;
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
}
