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
    List<DijaframView> tabovi;

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

    public void addInTabList(DijaframView dijaframView){
        if(tabovi == null){
            tabovi = new ArrayList<>();
            tabovi.add(dijaframView);
        }
        else tabovi.add(dijaframView);
    }

    public void promeniNazivProjekta(String string){
        nazivProjekta.setText(string);
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
        NotificationJTabbed poruka = (NotificationJTabbed) notification;
        ClassyNodeComposite parent2 = poruka.getParent();
        int totalTabs;
        int brojac;

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
        else if(poruka.getOznaka() == 1){
            brojac = 0;
            if (this.parent != null && parent2.getName().equals(this.parent.getName())){
                totalTabs = jTabbedPane.getTabCount();
                for(int i=0; i<totalTabs; i++){
                    for(ClassyNode c : parent2.getChildren()){
                        if(jTabbedPane.getTitleAt(i).equals(c.getName()))brojac = 1;
                    }
                    if(brojac == 0)
                        jTabbedPane.removeTabAt(i);
                    brojac = 0;
                    totalTabs = jTabbedPane.getTabCount();
                }
            }
        }
        else if(poruka.getOznaka() == 2){
            System.out.println(parent.getName() + " " + parent2.getName());
            if (this.parent != null && parent2.getName().equals(this.parent.getName())){
                jTabbedPane.removeAll();
                promeniNazivProjekta("      ");
            }
        }
        else if(poruka.getOznaka() == 3){
            while(true){
                if(parent instanceof Project)
                    break;
                else parent = parent.getParent();
            }
            if(parent.getName().equals(parent2.getName())) {
                jTabbedPane.removeAll();
                promeniNazivProjekta("      ");
            }
        }
    }

    public void setParent(ClassyNode parent) {
        this.parent = parent;
    }

    public List<DijaframView> getTabovi() {
        return tabovi;
    }
}
