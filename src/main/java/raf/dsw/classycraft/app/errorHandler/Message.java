package raf.dsw.classycraft.app.errorHandler;

import java.awt.*;

public class Message {

    String poruka;
    MessageType messageType;

    public Message(String poruka, MessageType messageType) {

        this.poruka = poruka;
        this.messageType = messageType;




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
}
