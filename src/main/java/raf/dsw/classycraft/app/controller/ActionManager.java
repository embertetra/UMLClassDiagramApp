package raf.dsw.classycraft.app.controller;

public class ActionManager {

    //sve akcije projekta
    ExitAction exitAction;
    AboutUsAction aboutUsAction;
    NewChildAction newChildAction;

    public ActionManager() {
        exitAction = new ExitAction();
        aboutUsAction = new AboutUsAction();
        newChildAction = new NewChildAction();
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
}
