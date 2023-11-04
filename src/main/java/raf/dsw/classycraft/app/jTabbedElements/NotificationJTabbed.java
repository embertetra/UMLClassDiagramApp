package raf.dsw.classycraft.app.jTabbedElements;

import raf.dsw.classycraft.app.classyCraftRepository.composite.ClassyNodeComposite;

public class NotificationJTabbed {

    ClassyNodeComposite parent;
    int oznaka; /// 0-dodaj dijagram, 1-izbrisi dijagram, 2-brisanje podpaketa, 3-paket/project

    public NotificationJTabbed(ClassyNodeComposite parent, int oznaka) {

        this.parent = parent;
        this.oznaka = oznaka;

    }

    public ClassyNodeComposite getParent() {
        return parent;
    }

    public void setParent(ClassyNodeComposite parent) {
        this.parent = parent;
    }

    public int getOznaka() {
        return oznaka;
    }

    public void setOznaka(int oznaka) {
        this.oznaka = oznaka;
    }
}
