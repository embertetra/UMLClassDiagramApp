package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.controller.dvoklikNaPaket.MouseListener;
import raf.dsw.classycraft.app.stateSablon.stateActions.*;

public class ActionManager {

    //sve akcije projekta
    private ExitAction exitAction;
    private AboutUsAction aboutUsAction;
    private NewChildAction newChildAction;
    private MouseListener mouseListener;
    private RemoveChild removeChild;
    private AutorAction autorAction;
    private AddConnectionAction addConnectionAction;
    private AddContectAction addContectAction;
    private AddInterclassAction addInterclassAction;
    private DeleteAction deleteAction;
    private SelectionAction selectionAction;
    private DuplicateAction duplicateAction;
    private MouseStateAction mouseStateAction;


    public ActionManager() {
        exitAction = new ExitAction();
        aboutUsAction = new AboutUsAction();
        newChildAction = new NewChildAction();
        removeChild = new RemoveChild();
        autorAction = new AutorAction();
        addConnectionAction = new AddConnectionAction();
        addContectAction = new AddContectAction();
        addInterclassAction = new AddInterclassAction();
        deleteAction = new DeleteAction();
        selectionAction = new SelectionAction();
        duplicateAction = new DuplicateAction();
        mouseStateAction = new MouseStateAction();
    }

    public DuplicateAction getDuplicateAction() {
        return duplicateAction;
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

    public AddConnectionAction getAddConnectionAction() {
        return addConnectionAction;
    }

    public AddContectAction getAddContectAction() {
        return addContectAction;
    }

    public AddInterclassAction getAddInterclassAction() {
        return addInterclassAction;
    }

    public DeleteAction getDeleteAction() {
        return deleteAction;
    }

    public SelectionAction getSelectionAction() {
        return selectionAction;
    }

    public MouseStateAction getMouseStateAction() {
        return mouseStateAction;
    }
}
