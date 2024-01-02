package raf.dsw.classycraft.app.commands.implementation;

import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.Atributi;
import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.ClassContent;
import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.Metode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Interclass;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.EnumM;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Interfejs;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Klasa;
import raf.dsw.classycraft.app.commands.AbstractCommand;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;

import java.util.ArrayList;
import java.util.List;

public class ChangeContentCommand extends AbstractCommand {

    private int index;
    private Interclass interclass;
    private DijagramView dijagramView;
    private Atributi atributRedo;
    private List<Atributi> atributUndo;
    private Metode metodaRedo;
    private List<Metode> metodaUndo;
    private String nazivRedo;
    private String nazivUndo;

    public ChangeContentCommand(int index, Atributi atribut, Metode metoda, String naziv, DijagramView dijagramView, Interclass interclass){
        this.index = index;
        this.atributRedo = atribut;
        this.metodaRedo = metoda;
        this.nazivRedo = naziv;
        this.dijagramView = dijagramView;
        this.interclass = interclass;
        atributUndo = new ArrayList<>();
        metodaUndo = new ArrayList<>();

        if(!MainFrame.getInstance().getInterfejsProzor().getTfNaziv().getText().isEmpty() || !MainFrame.getInstance().getInterfejsProzor().getTfNaziv().getText().equals(" ") && atribut == null)
            metodaUndo.add(MainFrame.getInstance().getInterfejsProzor().getMetodeList().get(index));
        else if(!MainFrame.getInstance().getKlasaProzor().getTfNaziv().getText().isEmpty() || !MainFrame.getInstance().getKlasaProzor().getTfNaziv().getText().equals(" ")) {
            if (MainFrame.getInstance().getKlasaProzor().getClassContentList().get(index) instanceof Atributi)
                atributUndo.add((Atributi) MainFrame.getInstance().getKlasaProzor().getClassContentList().get(index));
            else metodaUndo.add((Metode) MainFrame.getInstance().getKlasaProzor().getClassContentList().get(index));
        }
    }

    @Override
    public void doCommand() {
        if (interclass instanceof Klasa) {
            if(atributRedo != null){
                atributUndo.add(atributRedo);
                MainFrame.getInstance().getKlasaProzor().getClassContentList().set(index, atributRedo);
            } else if(metodaRedo != null){
                metodaUndo.add(metodaRedo);
                MainFrame.getInstance().getKlasaProzor().getClassContentList().set(index, metodaRedo);
            }
            MainFrame.getInstance().getKlasaProzor().setClassContentList(((Klasa) interclass).getClassContentList());
        } else if (interclass instanceof Interfejs){
            metodaUndo.add(metodaRedo);
            MainFrame.getInstance().getInterfejsProzor().getMetodeList().set(index, metodaRedo);
            MainFrame.getInstance().getInterfejsProzor().setMetodeList(((Interfejs) interclass).getMetodeList());
        }else if(interclass instanceof EnumM) {
            nazivUndo = MainFrame.getInstance().getEnumProzor().getEnumMList().get(index);
            MainFrame.getInstance().getEnumProzor().getEnumMList().set(index, nazivRedo);
            MainFrame.getInstance().getEnumProzor().setEnumMList(((EnumM)interclass).getListEnuma());
        }
        dijagramView.repaint();
    }

    @Override
    public void undoCommand() {
        if (interclass instanceof Klasa) {
            if(atributRedo != null && atributUndo != null) {
                atributUndo.remove(atributUndo.size()-1);
                MainFrame.getInstance().getKlasaProzor().getClassContentList().set(index, atributUndo.get(atributUndo.size()-1));
            }else if(metodaRedo != null && metodaUndo != null) {
                metodaUndo.remove(metodaUndo.size() - 1);
                MainFrame.getInstance().getKlasaProzor().getClassContentList().set(index, metodaUndo.get(metodaUndo.size() - 1));
            }MainFrame.getInstance().getKlasaProzor().setClassContentList(((Klasa) interclass).getClassContentList());
        } else if (interclass instanceof Interfejs && metodaRedo != null && metodaUndo != null){
            metodaUndo.remove(metodaUndo.size() - 1);
            MainFrame.getInstance().getInterfejsProzor().getMetodeList().set(index, metodaUndo.get(metodaUndo.size()-1));
            MainFrame.getInstance().getInterfejsProzor().setMetodeList(((Interfejs) interclass).getMetodeList());
        }else if(interclass instanceof EnumM) {
            MainFrame.getInstance().getEnumProzor().getEnumMList().set(index, nazivUndo);
            MainFrame.getInstance().getEnumProzor().setEnumMList(((EnumM)interclass).getListEnuma());
        }
        dijagramView.repaint();
    }
}
