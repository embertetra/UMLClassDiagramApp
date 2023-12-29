package raf.dsw.classycraft.app.commands.implementation;

import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.Atributi;
import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.Metode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.EnumM;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Interfejs;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Klasa;
import raf.dsw.classycraft.app.commands.AbstractCommand;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;

import java.util.List;

public class AddContentCommand extends AbstractCommand {

    public Interclass interclass;
    public DijagramView dijagramView;
    //public List<ClassContent> classContentList = new ArrayList<>();
    public Atributi atribut;
    public Metode metoda;
    public String naziv;


    public List<Metode> obrisaniInterfejsi;

    public AddContentCommand(Atributi atribut, Metode metoda, String naziv, Interclass interclass, DijagramView dijagramView){
        this.atribut = atribut;
        this.metoda = metoda;
        this.naziv = naziv;
        this.interclass = interclass;
        this.dijagramView = dijagramView;
    }

    @Override
    public void doCommand() {
        if(interclass instanceof Klasa){
            Klasa k = (Klasa) interclass;
            if(atribut != null)
                k.getClassContentList().add(atribut);
            else if(metoda != null)
                k.getClassContentList().remove(metoda);
        }
        else if(interclass instanceof Interfejs){
            Interfejs i = (Interfejs) interclass;
            i.getMetodeList().add(metoda);
        }
        else if(interclass instanceof EnumM){
            EnumM e = (EnumM) interclass;
            e.getListEnuma().add(naziv);
        }
        dijagramView.repaint();
    }

    @Override
    public void undoCommand() {
        if(interclass instanceof Klasa){
            Klasa k = (Klasa) interclass;
            k.getClassContentList().remove(k.getClassContentList().size()-1);
        }
        else if(interclass instanceof Interfejs){
            Interfejs i = (Interfejs) interclass;
            i.getMetodeList().remove(i.getMetodeList().size()-1);
        }
        else if(interclass instanceof EnumM){
            EnumM e = (EnumM) interclass;
            e.getListEnuma().remove(e.getListEnuma().size()-1);
        }
        dijagramView.repaint();
    }
}
