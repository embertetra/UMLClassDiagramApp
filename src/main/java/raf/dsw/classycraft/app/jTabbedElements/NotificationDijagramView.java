package raf.dsw.classycraft.app.jTabbedElements;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNode;

public class NotificationDijagramView {
    ClassyNode child;
    String newName;
    String oldName;

    public NotificationDijagramView(ClassyNode child, String newName, String oldName) {

        this.newName = newName;
        this.child = child;
        this.oldName = oldName;

    }

    public String getNewName() {
        return newName;
    }

    public ClassyNode getChild() {
        return child;
    }

    public void setChild(ClassyNode child) {
        this.child = child;
    }

    public String getOldName() {
        return oldName;
    }
}
