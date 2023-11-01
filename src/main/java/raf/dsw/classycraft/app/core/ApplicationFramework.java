package raf.dsw.classycraft.app.core;

import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

public class ApplicationFramework {

    //buduca polja za model celog projekta
    protected ClassyRepository classyRepository;

    public void initialize(ClassyRepository classyRepository){
        MainFrame.getInstance().setVisible(true);
        this.classyRepository = classyRepository;
    }
    private static ApplicationFramework instance;

    private ApplicationFramework(){

    }

    public static ApplicationFramework getInstance(){
        if(instance==null){
            instance = new ApplicationFramework();
        }
        return instance;
    }

    public void setClassyRepository(ClassyRepository classyRepository) {
        this.classyRepository = classyRepository;
    }

    public ClassyRepository getClassyRepository() {
        return classyRepository;
    }
}
