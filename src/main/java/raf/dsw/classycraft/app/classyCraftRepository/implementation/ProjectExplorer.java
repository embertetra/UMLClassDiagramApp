package raf.dsw.classycraft.app.classyCraftRepository.implementation;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNodeComposite;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.MessageType;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.PackageView;
import raf.dsw.classycraft.app.jTabbedElements.NotificationJTabbed;
import raf.dsw.classycraft.app.observer.IPublisher;
import raf.dsw.classycraft.app.observer.ISubscriber;

import java.util.ArrayList;
import java.util.List;

public class ProjectExplorer extends ClassyNodeComposite implements IPublisher {
    // root klasa = postoji samo jedna instanca
    private List<ISubscriber> subscribers;

    public ProjectExplorer(String name) {
        super(name, null);


    }

    @Override
    public void addChild(ClassyNode child) {
        if (child != null && child instanceof Project) {
            Project project = (Project) child;
            if (!this.getChildren().contains(project)) {
                this.getChildren().add(project);
            }
        }
    }

    @Override
    public void removeChild(ClassyNode child) {
        if (child != null && child instanceof Project) {
            if (getChildren().contains(child)) {
                getChildren().remove(child);

                if (MainFrame.getInstance().getPackageView() == null)
                    MainFrame.getInstance().setPackageView(new PackageView());
                addSubscriber(MainFrame.getInstance().getPackageView());
                notifySubscribers(new NotificationJTabbed((ClassyNodeComposite) child, 3));
            }
        }
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
