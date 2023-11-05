package raf.dsw.classycraft.app.errorHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {

    String poruka;
    MessageType messageType;
    DateTimeFormatter dtf;
    LocalDateTime now;

    public Message(String poruka, MessageType messageType) {

        this.poruka = poruka;
        this.messageType = messageType;

        dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm");
        now = LocalDateTime.now();

    }

    public String getPoruka() {
        return poruka;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public DateTimeFormatter getDtf() {
        return dtf;
    }

    public LocalDateTime getNow() {
        return now;
    }

}
