package raf.dsw.classycraft.app.classyCraftRepository.implementation;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.jTabbedElements.NotificationJTabbed;
import raf.dsw.classycraft.app.observer.IPublisher;
import raf.dsw.classycraft.app.observer.ISubscriber;

import java.util.ArrayList;
import java.util.List;

@JsonTypeName("projekat")
@JsonPropertyOrder({"type", "filePath", "name", "autor", "children", "type"})
public class Project extends ClassyNodeComposite implements IPublisher {
    protected String filePath;
    private transient List<ISubscriber> subscribers;
    private String autor;
    protected boolean changed;


    public Project(String name, ClassyNode parent) {
        super(name, parent);
        autor = "unkown";
        changed = true;
    }

    public Project() {
        super("", null);
        subscribers = new ArrayList<>();
        changed = true;
    }

    public void setNewAutor(Object notification) {

        notifySubscribers(notification);

        NotificationJTabbed poruka = (NotificationJTabbed) notification;
        setAutor(poruka.getNewName());

        changed = true;
    }

    public void setNameInJTabb(Object notification) {
        notifySubscribers(notification);
        changed = true;
    }

    @Override
    public void addChild(ClassyNode child) {
        if (child != null && child instanceof Package) {
            Package pck = (Package) child;
            if (!this.getChildren().contains(pck)) {
                this.getChildren().add(pck);
                changed = true;
            }
        }
    }

    @Override
    public void removeChild(ClassyNode child) {
        if (child != null && child instanceof Package) {
            if (getChildren().contains(child)) {
                getChildren().remove(child);
                notifySubscribers(new NotificationJTabbed((ClassyNodeComposite) child, 3));
                changed = true;
            }
        }
    }


    @Override
    public void addSubscriber(ISubscriber subscriber) {
        if (subscriber != null) {
            if (subscribers == null)
                this.subscribers = new ArrayList<>();
            if (!subscribers.contains(subscriber)) {
                this.subscribers.add(subscriber);
                changed = true;
            }
        }
    }

    @Override
    public void removeSubscriber(ISubscriber subscriber) {
        if (subscriber != null && subscribers != null && subscribers.contains(subscriber)) {
            subscribers.remove(subscriber);
            changed = true;
        }
    }

    @Override
    public void notifySubscribers(Object notification) {
        if (subscribers != null && notification != null && !subscribers.isEmpty()) {
            for (ISubscriber i : subscribers) {
                i.update(notification);
            }
            changed = true;
        }
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        changed = true;
        this.autor = autor;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        changed = true;
        this.filePath = filePath;
    }
}
