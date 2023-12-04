package raf.dsw.classycraft.app.stateSablon;

import raf.dsw.classycraft.app.stateSablon.states.*;

public class StateManager {

    private AddConnection addConnection;
    private AddContent addContent;
    private AddInterclass addInterclass;
    private Delete delete;
    private Selection selection;
    private State currentState;

    public StateManager() {
        addConnection = new AddConnection();
        addContent = new AddContent();
        addInterclass = new AddInterclass();
        delete = new Delete();
        selection = new Selection();
        currentState = null;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setAddConnection(){
        currentState = addConnection;
    }

    public void setAddContent(){
        currentState = addContent;
    }

    public void setAddInterclass(){
        currentState = addInterclass;
    }

    public void setDelete(){
        currentState = delete;
    }

    public void setSelection(){
        currentState = selection;
    }
    public AddInterclass getAddInterclass() {
        return addInterclass;
    }

    public AddConnection getAddConnection() {
        return addConnection;
    }
}
