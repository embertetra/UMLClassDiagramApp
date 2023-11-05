package raf.dsw.classycraft.app.errorHandler;

import raf.dsw.classycraft.app.observer.IPublisher;
import raf.dsw.classycraft.app.observer.ISubscriber;

import java.util.ArrayList;
import java.util.List;

public class MessageGenerator implements IPublisher {

    private List<ISubscriber> subscribers;

    public MessageGenerator() {
        subscribers = new ArrayList<>();
    }


    public void GenerateMessage(String poruka, MessageType messageType){
        Message message = new Message(poruka, messageType);
        notifySubscribers(message);
    }


    @Override
    public void addSubscriber(ISubscriber subscriber) {
        if(subscriber != null){
            if(subscribers == null)
                this.subscribers = new ArrayList<>();
            if(!subscribers.contains(subscriber))
                this.subscribers.add(subscriber);
        }
    }

    @Override
    public void removeSubscriber(ISubscriber subscriber) {
        if(subscriber != null && subscribers != null && subscribers.contains(subscriber))
            subscribers.remove(subscriber);
    }

    @Override
    public void notifySubscribers(Object notification) {
        if(subscribers != null && notification != null && !subscribers.isEmpty()){
            for (ISubscriber i: subscribers) {
                i.update(notification);
            }
        }
    }

    public List<ISubscriber> getSubscribers() {
        return subscribers;
    }

}
