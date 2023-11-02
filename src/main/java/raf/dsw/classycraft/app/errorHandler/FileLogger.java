package raf.dsw.classycraft.app.errorHandler;

import raf.dsw.classycraft.app.core.ApplicationFramework;

import java.io.FileWriter;
import java.io.IOException;

public class FileLogger implements Logger {

    public FileLogger() {

        ApplicationFramework.getInstance().getMessageGenerator().getSubscribers().add(this);

    }

    @Override
    public void log(Object notification) {

        Message message = (Message)notification;

        try {
            FileWriter fw = new FileWriter("/Users/dicifor/Desktop/dizajn-softvera-projekat-tim_dimitrijemitic_martaljiljak/src/main/resources/log.txt", true);

            fw.append("[" + message.getMessageType() + "] [" + message.getDtf().format(message.getNow()) + "] [" + message.getPoruka() + "]" + "\n");
            fw.flush();

        } catch (IOException e) {
            System.out.println("Nema fajla");
            //throw new RuntimeException(e);
        }

    }

    @Override
    public void update(Object notification) {
        log(notification);
    }
}
