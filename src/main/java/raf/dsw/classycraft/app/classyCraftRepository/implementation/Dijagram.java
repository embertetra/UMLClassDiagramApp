package raf.dsw.classycraft.app.classyCraftRepository.implementation;

import com.fasterxml.jackson.annotation.JsonTypeName;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.DijagramElement;
import raf.dsw.classycraft.app.observer.IPublisher;
import raf.dsw.classycraft.app.observer.ISubscriber;

import java.util.ArrayList;
import java.util.List;
@JsonTypeName("dijagram")
public class Dijagram extends ClassyNodeComposite implements IPublisher {

    private transient List<ISubscriber> subscribers;

    public Dijagram(String name, ClassyNode parent) {
        super(name, parent);
        subscribers = new ArrayList<>();
        projectChanged();
    }
    public Dijagram(){
        super("", null);
        subscribers = new ArrayList<>();
    }

    @Override
    public void addChild(ClassyNode child) {
        if (child != null && child instanceof DijagramElement) {
            getChildren().add(child);
            notifySubscribers("state");
            projectChanged();
        }
    }

    @Override
    public void removeChild(ClassyNode child) {
        if(child != null && getChildren().contains(child)) {
            getChildren().remove(child);
            notifySubscribers("state");
            projectChanged();
        }
    }

    public void setNameInJTabb(Object notification){
        projectChanged();
        notifySubscribers(notification);
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

    public void projectChanged(){
        ClassyNode c = this;
        while(c!=null && !(c instanceof Project)){
            c = c.getParent();
        }
        if(c != null)
            ((Project) c).setChanged(true);
    }

}
