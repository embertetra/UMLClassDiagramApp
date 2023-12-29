package raf.dsw.classycraft.app.commands;

import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    private List<AbstractCommand> commands = new ArrayList<>();

    private int currentCommand = 0;

    public void doCommand(){
        if(currentCommand < commands.size()) {
            commands.get(currentCommand++).doCommand();
            MainFrame.getInstance().getActionManager().getUndoAction().setEnabled(true);
        }
        if(currentCommand == commands.size()) {
            MainFrame.getInstance().getActionManager().getRedoAction().setEnabled(false);
        }
    }

    public void undoCommand(){
        if(currentCommand > 0){
            MainFrame.getInstance().getActionManager().getRedoAction().setEnabled(true);
            commands.get(--currentCommand).undoCommand();
        }
        if(currentCommand == 0) {
            MainFrame.getInstance().getActionManager().getUndoAction().setEnabled(false);
        }
    }
    public void addCommand(AbstractCommand command){
        while(currentCommand < commands.size())
            commands.remove(currentCommand);
        commands.add(command);
        doCommand();
    }

    public List<AbstractCommand> getCommands() {
        return commands;
    }

    public void setCommands(List<AbstractCommand> commands) {
        this.commands = commands;
    }

    public int getCurrentCommand() {
        return currentCommand;
    }

    public void setCurrentCommand(int currentCommand) {
        this.currentCommand = currentCommand;
    }
}
