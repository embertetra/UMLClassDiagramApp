package raf.dsw.classycraft.app.controller;

public class ActionManager {

    //sve akcije projekta
    ExitAction exitAction;
    AboutUsAction aboutUsAction;
    NewChildAction newChildAction;

    RemoveChild removeChild;

    public ActionManager() {
        exitAction = new ExitAction();
        aboutUsAction = new AboutUsAction();
        newChildAction = new NewChildAction();
        removeChild = new RemoveChild();
    }

    public ExitAction getExitAction() {
        return exitAction;
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
}
