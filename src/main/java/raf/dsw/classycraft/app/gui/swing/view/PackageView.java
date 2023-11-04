package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.observer.ISubscriber;

import javax.swing.*;

public class PackageView implements ISubscriber {
    private JTabbedPane jTabbedPane;
    JLabel nazivProjekta;
    JLabel nazivAutora;
    JPanel rightSide;
    ClassyNode parent;

    public PackageView() {
        this.parent = null;

        nazivProjekta = new JLabel("Projekat");
        nazivAutora = new JLabel("Autor");

        jTabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);

        rightSide = new JPanel();
        BoxLayout box = new BoxLayout(rightSide, BoxLayout.Y_AXIS);
        rightSide.setLayout(box);
        rightSide.add(nazivProjekta);
        rightSide.add(nazivAutora);
        rightSide.add(jTabbedPane);
    }

    public JTabbedPane getjTabbedPane() {
        return jTabbedPane;
    }

    public JLabel getNazivProjekta() {
        return nazivProjekta;
    }

    public JLabel getNazivAutora() {
        return nazivAutora;
    }

    public JPanel getRightSide() {
        return rightSide;
    }

    @Override
    public void update(Object notification) {
        ClassyNodeComposite parent2 = (ClassyNodeComposite) notification;
        int brojac = 0;
        if (this.parent != null && parent2.getName().equals(this.parent.getName())) {
            for (ClassyNode c : parent2.getChildren()) {
                int totalTabs = jTabbedPane.getTabCount();
                for (int i = 0; i < totalTabs; i++) {
                    if (c.getName().equals(jTabbedPane.getTitleAt(i)) && c instanceof Dijagram) brojac = 1;
                }
                if (brojac == 0 && c instanceof Dijagram) {
                    jTabbedPane.addTab(c.getName(), new JPanel());
                }
                brojac = 0;
            }
        }
    }
    public void setParent(ClassyNode parent) {
        this.parent = parent;
    }
}
