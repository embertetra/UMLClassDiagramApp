package raf.dsw.classycraft.app.gui.swing.tree.model.childFactory;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
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
    public ClassyNode generateChild(ClassyNode parent){

        ///pravljenje projekta
        if(parent instanceof ProjectExplorer){
            childFactory = getChildFactory(FactoryType.PROJECT);

            int cnt = 1;
            Project project = new Project("Project" + String.valueOf(cnt), parent);
            while(((ProjectExplorer) parent).getChildren().contains(project)){
                cnt++;
                project = new Project("Project" + String.valueOf(cnt), parent);
            }

            return childFactory.orderChild(project.getName(), parent);
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

            return childFactory.orderChild(pck.getName(), parent);
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

                return  childFactory.orderChild(pck.getName(), parent);
            }
            else if(n == 0){
                childFactory = getChildFactory(FactoryType.DIAGRAM);

                int cnt = 1;
                Dijagram dijagram = new Dijagram("Dijagram" + String.valueOf(cnt), parent);
                while(((Package) parent).getChildren().contains(dijagram)){
                    cnt++;
                    dijagram = new Dijagram("Dijagram" + String.valueOf(cnt), parent);
                }

                return  childFactory.orderChild(dijagram.getName(), parent);
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
