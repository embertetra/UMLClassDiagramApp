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

        dtf = DateTimeFormatter.ofPattern("dd.MM.YYYY. HH:mm");
        now = LocalDateTime.now();

    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public DateTimeFormatter getDtf() {
        return dtf;
    }

    public void setDtf(DateTimeFormatter dtf) {
        this.dtf = dtf;
    }

    public LocalDateTime getNow() {
        return now;
    }

    public void setNow(LocalDateTime now) {
        this.now = now;
    }
}
