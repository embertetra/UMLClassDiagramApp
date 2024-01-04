package raf.dsw.classycraft.app.commands.implementation;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.EnumM;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Interfejs;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Klasa;
import raf.dsw.classycraft.app.commands.AbstractCommand;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;

public class AddNameCommand extends AbstractCommand {

    private String nazivRedo;
    private String nazivUndo;
    private Interclass interclass;
    private DijagramView dijagramView;

    public AddNameCommand(String noviNaziv,  String stariNaziv, Interclass interclass, DijagramView dijagramView){
        this.nazivRedo = noviNaziv;
        this.nazivUndo = stariNaziv;
        this.interclass = interclass;
        this.dijagramView = dijagramView;
    }
    @Override
    public void doCommand() {
        if(interclass instanceof Klasa){
            Klasa k = (Klasa) interclass;
            k.setNaziv(nazivRedo);
        }
        else if(interclass instanceof Interfejs){
            Interfejs i = (Interfejs) interclass;
            i.setNaziv(nazivRedo);
        }
        else if(interclass instanceof EnumM){
            EnumM e = (EnumM) interclass;
            e.setNaziv(nazivRedo);
        }
        dijagramView.repaint();
    }

    @Override
    public void undoCommand() {
        if(interclass instanceof Klasa){
            Klasa k = (Klasa) interclass;
            k.setNaziv(nazivUndo);
        }
        else if(interclass instanceof Interfejs){
            Interfejs i = (Interfejs) interclass;
            i.setNaziv(nazivUndo);
        }
        else if(interclass instanceof EnumM){
            EnumM e = (EnumM) interclass;
            e.setNaziv(nazivUndo);
        }
        dijagramView.repaint();
    }
}
