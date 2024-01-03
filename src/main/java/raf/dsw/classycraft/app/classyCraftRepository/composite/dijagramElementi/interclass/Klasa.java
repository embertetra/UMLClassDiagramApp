package raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass;

import com.fasterxml.jackson.annotation.JsonTypeName;
import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.ClassContent;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.classyCraftRepository.implementation.Project;
import raf.dsw.classycraft.app.observer.ISubscriber;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
@JsonTypeName("klasa")
public class Klasa extends Interclass{

    private String naziv;

    private List<ClassContent> classContentList;
    private transient List<ISubscriber> subscribers;
    public Klasa(){
        super("", null);
    }

    public Klasa(String name, ClassyNode parent) {
        super(name, parent);
        projectChanged();
    }

    public Klasa(String name, ClassyNode parent, int stroke, String naziv, Vidljivost vidljivost, Point position) {
        super(name, parent, stroke, naziv, vidljivost, position);
        this.naziv = "    ";
        classContentList = new ArrayList<>();
        subscribers = new ArrayList<>();
        projectChanged();
    }

    public void setClassContentList(List<ClassContent> classContentList) {
        this.classContentList = classContentList;
        projectChanged();
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
                projectChanged();
            }
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
    public List<ClassContent> getClassContentList() {
        return classContentList;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
        projectChanged();
    }

}
