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

public class ChangeContentCommand extends AbstractCommand {

    public int index;
    public Atributi atribut;
    public Metode metoda;
    public String naziv;
    public DijagramView dijagramView;
    public Interclass interclass;

    public Atributi atributStari;
    public Metode metodaStara;
    public String nazivStari;

    public ChangeContentCommand(int index, Atributi atribut, Metode metoda, String naziv, DijagramView dijagramView, Interclass interclass){
        this.index = index;
        this.atribut = atribut;
        this.metoda = metoda;
        this.naziv = naziv;
        this.dijagramView = dijagramView;
        this.interclass = interclass;
    }

    @Override
    public void doCommand() {
        if (interclass instanceof Klasa) {
            if(atribut != null){
                atributStari = (Atributi) MainFrame.getInstance().getKlasaProzor().getLista().getSelectedValue();
                MainFrame.getInstance().getKlasaProzor().getClassContentList().set(index, atribut);
            } else if(metoda != null){
                metodaStara = (Metode) MainFrame.getInstance().getKlasaProzor().getLista().getSelectedValue();
                MainFrame.getInstance().getKlasaProzor().getClassContentList().set(index, metoda);
            } MainFrame.getInstance().getKlasaProzor().setClassContentList(((Klasa) interclass).getClassContentList());
        } else if (interclass instanceof Interfejs){
            metodaStara = MainFrame.getInstance().getInterfejsProzor().getLista().getSelectedValue();
            MainFrame.getInstance().getInterfejsProzor().getMetodeList().set(index, metoda);
            MainFrame.getInstance().getInterfejsProzor().setMetodeList(((Interfejs) interclass).getMetodeList());
        }else if(interclass instanceof EnumM) {
            nazivStari = MainFrame.getInstance().getEnumProzor().getEnumMList().get(index);
            MainFrame.getInstance().getEnumProzor().getEnumMList().set(index, naziv);
            MainFrame.getInstance().getEnumProzor().setEnumMList(((EnumM)interclass).getListEnuma());
        }
        dijagramView.repaint();
    }

    @Override
    public void undoCommand() {

        if (interclass instanceof Klasa) {
            if(atribut != null)
                MainFrame.getInstance().getKlasaProzor().getClassContentList().set(index, atributStari);
            else if(metoda != null)
                MainFrame.getInstance().getKlasaProzor().getClassContentList().set(index, metodaStara);
            MainFrame.getInstance().getKlasaProzor().setClassContentList(((Klasa) interclass).getClassContentList());
        } else if (interclass instanceof Interfejs){
            MainFrame.getInstance().getInterfejsProzor().getMetodeList().set(index, metodaStara);
            MainFrame.getInstance().getInterfejsProzor().setMetodeList(((Interfejs) interclass).getMetodeList());
        }else if(interclass instanceof EnumM) {
            MainFrame.getInstance().getEnumProzor().getEnumMList().set(index, nazivStari);
            MainFrame.getInstance().getEnumProzor().setEnumMList(((EnumM)interclass).getListEnuma());
        }
        dijagramView.repaint();

    }
}
