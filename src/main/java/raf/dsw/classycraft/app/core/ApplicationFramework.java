package raf.dsw.classycraft.app.core;

import com.sun.tools.javac.Main;
import raf.dsw.classycraft.app.errorHandler.Message;
import raf.dsw.classycraft.app.errorHandler.MessageGenerator;
import raf.dsw.classycraft.app.errorHandler.MessageType;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import javax.swing.*;

public class ApplicationFramework {

    private static ApplicationFramework instance;

    //sva polja modela projekta

    private ApplicationFramework(){

    }

    public void initialize(){
        MainFrame.getInstance().setVisible(true);
    }

    public static ApplicationFramework getInstance(){
        if(instance==null){
            instance = new ApplicationFramework();
        }
        return instance;
    }
}
