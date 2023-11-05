package raf.dsw.classycraft.app.classyCraftRepository.implementation;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.observer.IPublisher;
import raf.dsw.classycraft.app.observer.ISubscriber;

import java.util.ArrayList;
import java.util.List;

public class Dijagram extends ClassyNode implements IPublisher {

    private List<ISubscriber> subscribers;
    public Dijagram(String name, ClassyNode parent) {
        super(name, parent);
    }

    @Override
    public void addSubscriber(ISubscriber subscriber) {
        if (subscriber != null) {
            if (subscribers == null)
                this.subscribers = new ArrayList<>();
            if (!subscribers.contains(subscriber))
                this.subscribers.add(subscriber);
        }
    }

    @Override
    public void removeSubscriber(ISubscriber subscriber) {
        if (subscriber != null && subscribers != null && subscribers.contains(subscriber))
            subscribers.remove(subscriber);
    }

    @Override
    public void notifySubscribers(Object notification) {
        if (subscribers != null && notification != null && !subscribers.isEmpty()) {
            for (ISubscriber i : subscribers) {
                i.update(notification);
            }
        }
    }
}
