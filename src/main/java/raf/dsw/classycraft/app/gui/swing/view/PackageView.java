package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Project;
import raf.dsw.classycraft.app.jTabbedElements.NotificationJTabbed;
import raf.dsw.classycraft.app.observer.ISubscriber;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PackageView implements ISubscriber {
    private JTabbedPane jTabbedPane;
    JLabel nazivProjekta;
    JLabel nazivAutora;
    JPanel rightSide;
    ClassyNode parent;
    List<DijagramView> tabovi;

    public PackageView() {
        this.parent = null;

        nazivProjekta = new JLabel("        ");
        nazivAutora = new JLabel("          ");

        jTabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);

        rightSide = new JPanel();
        BoxLayout box = new BoxLayout(rightSide, BoxLayout.Y_AXIS);
        rightSide.setLayout(box);
        rightSide.add(nazivProjekta);
        rightSide.add(nazivAutora);
        rightSide.add(jTabbedPane);
    }

    public void addInTabList(DijagramView dijagramView) {
        if (tabovi == null) {
            tabovi = new ArrayList<>();
            tabovi.add(dijagramView);
        } else tabovi.add(dijagramView);
    }

    public void promeniNazivProjekta(String string) {
        nazivProjekta.setText(string);
    }

    public void promeniNazivAutora(String string){
        nazivAutora.setText(string);
    }

    public JTabbedPane getjTabbedPane() {
        return jTabbedPane;
    }

    public JPanel getRightSide() {
        return rightSide;
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
                        jTabbedPane.addTab(c.getName(), new JPanel());
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
                        //tabovi.remove(i);
                    }
                    brojac = 0;
                    totalTabs = jTabbedPane.getTabCount();
                }
            }
        }
        ///update obrisanih podpaketa
        else if (poruka.getOznaka() == 2) {
            ClassyNode tmp = parent;
            if(parent !=null){
                while(!(tmp instanceof Project)){
                    if(tmp.getName().equals(poruka.getParent().getName())){
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
            }
        }
        ///Update promena autora
        else if(poruka.getOznaka() == 6){

            if(parent != null){
                ClassyNode p = this.parent;
                while(true){
                    if(p instanceof Project)
                        break;
                    else p = p.getParent();
                }
                if (p.getName().equals(poruka.getParent().getName())){
                    nazivAutora.setText(poruka.getNewName());
                }
            }

        }
    }

    public void setParent(ClassyNode parent) {
        this.parent = parent;
    }

}
