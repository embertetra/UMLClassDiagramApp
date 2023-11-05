package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.controller.dvoklikNaPaket.MouseListener;

public class ActionManager {

    //sve akcije projekta
    ExitAction exitAction;
    AboutUsAction aboutUsAction;
    NewChildAction newChildAction;
    MouseListener mouseListener;
    RemoveChild removeChild;
    AutorAction autorAction;

    public ActionManager() {
        exitAction = new ExitAction();
        aboutUsAction = new AboutUsAction();
        newChildAction = new NewChildAction();
        removeChild = new RemoveChild();
        autorAction = new AutorAction();
    }

    public AboutUsAction getAboutUsAction() {
        return aboutUsAction;
    }

    public NewChildAction getNewChildAction() {
        return newChildAction;
    }

    public RemoveChild getRemoveChild() {
        return removeChild;
    }

    public void setMouseListener(MouseListener mouseListener) {
        this.mouseListener = mouseListener;
    }

    public AutorAction getAutorAction() {
        return autorAction;
    }
}
