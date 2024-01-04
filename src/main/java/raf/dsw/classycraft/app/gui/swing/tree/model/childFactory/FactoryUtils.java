package raf.dsw.classycraft.app.gui.swing.tree.model.childFactory;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Connection;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.DijagramElement;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Package;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Project;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.ProjectExplorer;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import javax.swing.*;

public class FactoryUtils {

    ChildFactory childFactory;

    public FactoryUtils() {
    }
    public ClassyNode generateChild(ClassyNode parent, DijagramElement dijagramElement){

        ///pravljenje projekta
        if(parent instanceof ProjectExplorer){
            childFactory = getChildFactory(FactoryType.PROJECT);

            int cnt = 1;
            Project project = new Project("Project" + String.valueOf(cnt), parent);
            while(((ProjectExplorer) parent).getChildren().contains(project)){
                cnt++;
                project = new Project("Project" + String.valueOf(cnt), parent);
            }

            return childFactory.orderChild(project.getName(), parent, null);
        }
        ///pravljenje paketa
        else if(parent instanceof Project){
            childFactory = getChildFactory(FactoryType.PACKAGE);

            int cnt = 1;
            Package pck = new Package("Package" + String.valueOf(cnt), parent);
            while(((Project) parent).getChildren().contains(pck)){
                cnt++;
                pck = new Package("Package" + String.valueOf(cnt), parent);
            }

            return childFactory.orderChild(pck.getName(), parent, null);
        }
        ///pravljenje dijagrama ili podpaketa
        else if(parent instanceof Package){

            Object[] options = {"Dijagram", "Podpaket"};

            int n = JOptionPane.showOptionDialog(MainFrame.getInstance(),
                    "Da li zelite da kreirate dijagram ili podpaket?", "Kreiranje", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null,     //default ikonica
                    options,  //opcije
                    options[0]); //selektovan po defaultu

            if(n == 1){
                childFactory = getChildFactory(FactoryType.PACKAGE);

                int cnt = 1;
                Package pck = new Package("SubPackage" + String.valueOf(cnt), parent);
                while(((Package) parent).getChildren().contains(pck)){
                    cnt++;
                    pck = new Package("SubPackage" + String.valueOf(cnt), parent);
                }

                return  childFactory.orderChild(pck.getName(), parent, null);
            }
            else if(n == 0){
                childFactory = getChildFactory(FactoryType.DIAGRAM);

                int cnt = 1;
                Dijagram dijagram = new Dijagram("Dijagram" + String.valueOf(cnt), parent);
                while(((Package) parent).getChildren().contains(dijagram)){
                    cnt++;
                    dijagram = new Dijagram("Dijagram" + String.valueOf(cnt), parent);
                }
                Dijagram d = (Dijagram) childFactory.orderChild(dijagram.getName(), parent, null);
                MainFrame.getInstance().getGalleryTemplates().setProsledjen(d);
                MainFrame.getInstance().getGalleryTemplates().setVisible(true);
                return null;
                //return  childFactory.orderChild(dijagram.getName(), parent, null);
            }
        }
        else if(parent instanceof Dijagram){
            Dijagram d = (Dijagram)parent;
            if(d.getChildren().size()-1 >=0);
                ClassyNode c = d.getChildren().get(d.getChildren().size()-1);
            if(c instanceof Interclass) {
                childFactory = new InterclassFactory();
                return childFactory.makeChild("interClass", parent, dijagramElement);
            }
            else if(c instanceof Connection){
                childFactory = new InterclassFactory();
                return childFactory.makeChild("connection", parent, dijagramElement);
            }
        }
        return null;
    }


    public ChildFactory getChildFactory(FactoryType factoryType) {

        if (factoryType == FactoryType.PROJECT)
            return new ProjectFactory();
        else if (factoryType == FactoryType.PACKAGE)
            return new PackageFactory();
        else if (factoryType == FactoryType.DIAGRAM)
            return new DiagramFactory();
        return null;

    }

}
