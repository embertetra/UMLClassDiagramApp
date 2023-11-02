package raf.dsw.classycraft.app.controller;

public class ActionManager {

    //sve akcije projekta
    ExitAction exitAction;
    AboutUsAction aboutUsAction;

    public ActionManager() {
        exitAction = new ExitAction();
        aboutUsAction = new AboutUsAction();
    }

    public ExitAction getExitAction() {
        return exitAction;
    }

    public AboutUsAction getAboutUsAction() {
        return aboutUsAction;
    }
}
