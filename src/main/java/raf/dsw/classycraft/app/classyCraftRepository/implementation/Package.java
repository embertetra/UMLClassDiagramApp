package raf.dsw.classycraft.app.classyCraftRepository.implementation;

import com.fasterxml.jackson.annotation.JsonTypeName;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.jTabbedElements.NotificationJTabbed;
import raf.dsw.classycraft.app.observer.IPublisher;
import raf.dsw.classycraft.app.observer.ISubscriber;

import java.util.ArrayList;
import java.util.List;

@JsonTypeName("paket")
public class Package extends ClassyNodeComposite implements IPublisher {
    private transient List<ISubscriber> subscribers;
    public Package(){
        super("", null);
        subscribers = new ArrayList<>();
    }
    public Package(String name, ClassyNode parent) {
        super(name, parent);
        projectChanged();
    }


    @Override
    public void addChild(ClassyNode child) {
        if (child != null && child instanceof Dijagram) {
            Dijagram dijagram = (Dijagram) child;
            if (!this.getChildren().contains(dijagram)) {
                this.getChildren().add(dijagram);
                projectChanged();
                notifySubscribers(new NotificationJTabbed(this, 0));
            }
        } else if (child != null && child instanceof Package) {
            Package p = (Package) child;
            if (!this.getChildren().contains(p)) {
                projectChanged();
                this.getChildren().add(p);
            }
        }
    }

    @Override
    public void removeChild(ClassyNode child) {
        if (child != null && child instanceof Package) {
            if (getChildren().contains(child)) {
                getChildren().remove(child);
                notifySubscribers(new NotificationJTabbed((ClassyNodeComposite) child, 2));
                projectChanged();
            }
        } else if (child != null && child instanceof Dijagram) {
            if (getChildren().contains(child)) {
                getChildren().remove(child);
                notifySubscribers(new NotificationJTabbed(this, 1));
                projectChanged();
            }
        }
    }

    @Override
    public void addSubscriber(ISubscriber subscriber) {
        if (subscriber != null) {
            projectChanged();
            if (subscribers == null)
                this.subscribers = new ArrayList<>();
            if (!subscribers.contains(subscriber))
                this.subscribers.add(subscriber);
        }
    }

    @Override
    public void removeSubscriber(ISubscriber subscriber) {
        if (subscriber != null && subscribers != null && subscribers.contains(subscriber)) {
            subscribers.remove(subscriber);
            projectChanged();
        }
    }

    @Override
    public void notifySubscribers(Object notification) {
        if (subscribers != null && notification != null && !subscribers.isEmpty()) {
            for (ISubscriber i : subscribers) {
                i.update(notification);
            }
            projectChanged();
        }
    }

    private void projectChanged(){
        ClassyNode c = this;
        while(c!=null && !(c instanceof Project)){
            c = c.getParent();
        }
        if(c != null)
            ((Project) c).setChanged(true);
    }

}
