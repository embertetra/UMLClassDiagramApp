package raf.dsw.classycraft.app.errorHandler;

import com.sun.tools.javac.Main;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

public class ConsoleLogger implements Logger{



    public ConsoleLogger() {

        ApplicationFramework.getInstance().getMessageGenerator().getSubscribers().add(this);

    }

    @Override
    public void update(Object notification) {
        log(notification);
    }

    @Override
    public void log(Object notification) {
        Message message = (Message) notification;
        System.out.println("[" + message.getMessageType() + "] [" + message.getDtf().format(message.getNow()) + "] [" + message.getPoruka() + "]");
    }
}
