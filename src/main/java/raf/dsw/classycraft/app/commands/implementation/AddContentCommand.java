package raf.dsw.classycraft.app.commands.implementation;

import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.Metode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.EnumM;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Interfejs;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Klasa;
import raf.dsw.classycraft.app.commands.AbstractCommand;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.MessageType;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.util.List;

public class AddContentCommand extends AbstractCommand {

    public Interclass interclass;
    public DijagramView dijagramView;


    public List<Metode> obrisaniInterfejsi;
    public List<String> obisaniEnumi;

    public AddContentCommand(Interclass interclass, DijagramView dijagramView){
        this.interclass = interclass;
        this.dijagramView = dijagramView;
    }

    @Override
    public void doCommand() {
        if(interclass instanceof Klasa){
            Klasa k = (Klasa) interclass;

        }
        else if(interclass instanceof Interfejs){
            Interfejs i = (Interfejs) interclass;

        }
        else if(interclass instanceof EnumM){

        }
    }

    @Override
    public void undoCommand() {
        if(interclass instanceof Klasa){
            Klasa k = (Klasa) interclass;

        }
        else if(interclass instanceof Interfejs){
            Interfejs i = (Interfejs) interclass;
            List<Metode> listaMetoda = i.getMetodeList();
            obrisaniInterfejsi.add(listaMetoda.get(listaMetoda.size()-1));
            listaMetoda.remove(listaMetoda.size()-1);
            MainFrame.getInstance().getInterfejsProzor().setMetodeList(listaMetoda);
            dijagramView.repaint();
        }
        else if(interclass instanceof EnumM){
            EnumM e = (EnumM) interclass;
            List<String> listaEnumEl = e.getListEnuma();
            obisaniEnumi.add(listaEnumEl.get(listaEnumEl.size()-1));
            listaEnumEl.remove(listaEnumEl.size()-1);
            MainFrame.getInstance().getEnumProzor().setEnumMList(listaEnumEl);
            dijagramView.repaint();
        }
    }
}
