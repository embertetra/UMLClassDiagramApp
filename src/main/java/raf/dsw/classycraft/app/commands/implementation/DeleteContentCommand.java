package raf.dsw.classycraft.app.commands.implementation;

import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.Atributi;
import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.Metode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.EnumM;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Interfejs;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Klasa;
import raf.dsw.classycraft.app.commands.AbstractCommand;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

public class DeleteContentCommand extends AbstractCommand {

    public Interclass interclass;
    public DijagramView dijagramView;
    public Atributi atribut;
    public Metode metoda;
    public String naziv;

    public DeleteContentCommand(Interclass interclass, DijagramView dijagramView){
        atribut = null;
        metoda = null;
        naziv = null;
        this.interclass = interclass;
        this.dijagramView = dijagramView;
    }

    @Override
    public void doCommand() {
        if(interclass instanceof Klasa){
            Klasa k = (Klasa) interclass;
            if(k.getClassContentList().get(k.getClassContentList().size()-1) instanceof Atributi)
                atribut = (Atributi) k.getClassContentList().get(k.getClassContentList().size()-1);
            else if(k.getClassContentList().get(k.getClassContentList().size()-1) instanceof Metode)
                metoda = (Metode)k.getClassContentList().get(k.getClassContentList().size()-1);
            k.getClassContentList().remove(k.getClassContentList().size()-1);
            MainFrame.getInstance().getKlasaProzor().setClassContentList(k.getClassContentList());
        }
        else if(interclass instanceof Interfejs){
            Interfejs i = (Interfejs) interclass;
            metoda = i.getMetodeList().get(i.getMetodeList().size()-1);
            i.getMetodeList().remove(i.getMetodeList().size()-1);
            MainFrame.getInstance().getInterfejsProzor().setMetodeList(i.getMetodeList());
        }
        else if(interclass instanceof EnumM){
            EnumM e = (EnumM) interclass;
            naziv = e.getListEnuma().get(e.getListEnuma().size()-1);
            e.getListEnuma().remove(e.getListEnuma().size()-1);
            MainFrame.getInstance().getEnumProzor().setEnumMList(e.getListEnuma());
        }
        dijagramView.repaint();
    }

    @Override
    public void undoCommand() {
        if(interclass instanceof Klasa){
            Klasa k = (Klasa) interclass;
            if(metoda == null) {
                k.getClassContentList().add(atribut);
            } else if(atribut == null) {
                k.getClassContentList().add(metoda);
            }
            MainFrame.getInstance().getKlasaProzor().setClassContentList(k.getClassContentList());
        }
        else if(interclass instanceof Interfejs){
            Interfejs i = (Interfejs) interclass;
            i.getMetodeList().add(metoda);
            MainFrame.getInstance().getInterfejsProzor().setMetodeList(i.getMetodeList());
        }
        else if(interclass instanceof EnumM){
            EnumM e = (EnumM) interclass;
            e.getListEnuma().add(naziv);
            MainFrame.getInstance().getEnumProzor().setEnumMList(e.getListEnuma());
        }
        dijagramView.repaint();
    }
}
