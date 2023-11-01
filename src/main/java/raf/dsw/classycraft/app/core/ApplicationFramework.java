package raf.dsw.classycraft.app.core;

import com.sun.tools.javac.Main;
import raf.dsw.classycraft.app.errorHandler.*;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import javax.swing.*;

public class ApplicationFramework {

    private static ApplicationFramework instance;

    //sva polja modela projekta
    MessageGenerator messageGenerator;
    Logger fileLogger;
    Logger consoleLogger;

    private ApplicationFramework(){

        messageGenerator = new MessageGenerator();

    }

    public void initialize(){

        LoggerFactory loggerFactory = new LoggerFactory();
        fileLogger = loggerFactory.createLogger(LoggerType.FILE);
        consoleLogger = loggerFactory.createLogger(LoggerType.CONSOLE);

        MainFrame.getInstance().setVisible(true);
    }

    public static ApplicationFramework getInstance(){
        if(instance==null){
            instance = new ApplicationFramework();
        }
        return instance;
    }

    public MessageGenerator getMessageGenerator() {
        return messageGenerator;
    }

    public void setMessageGenerator(MessageGenerator messageGenerator) {
        this.messageGenerator = messageGenerator;
    }

    public Logger getFileLogger() {
        return fileLogger;
    }

    public void setFileLogger(Logger fileLogger) {
        this.fileLogger = fileLogger;
    }

    public Logger getConsoleLogger() {
        return consoleLogger;
    }

    public void setConsoleLogger(Logger consoleLogger) {
        this.consoleLogger = consoleLogger;
    }
}
