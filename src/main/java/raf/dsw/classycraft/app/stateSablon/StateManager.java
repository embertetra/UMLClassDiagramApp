package raf.dsw.classycraft.app.stateSablon;

import raf.dsw.classycraft.app.stateSablon.states.*;

public class StateManager {

    private AddConnection addConnection;
    private AddContent addContent;
    private AddInterclass addInterclass;
    private Delete delete;
    private Selection selection;
    private Mouse mouse;
    private Move move;
    private State currentState;

    public StateManager() {
        addConnection = new AddConnection();
        addContent = new AddContent();
        addInterclass = new AddInterclass();
        delete = new Delete();
        selection = new Selection();
        mouse = new Mouse();
        move = new Move();
        currentState = null;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setAddConnection(){
        currentState = addConnection;
    }
    public void setMove(){
        currentState = move;
    }
    public void setMouse(){
        currentState = mouse;
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
