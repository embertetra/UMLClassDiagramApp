package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.Atributi;
import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.ClassContent;
import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.Metode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Klasa;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Vidljivost;
import raf.dsw.classycraft.app.commands.AbstractCommand;
import raf.dsw.classycraft.app.commands.implementation.AddContentCommand;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.MessageType;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.KlasaPainter;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DodajUKlasuAction extends AbstractClassyAction{

    private KlasaPainter klasaPainter;
    private DijagramView dijagramView;

    public DodajUKlasuAction(InterclassPainter interclassPainter, DijagramView d) {

        klasaPainter = (KlasaPainter) interclassPainter;
        dijagramView = d;

        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(null));
        putValue(NAME, "Dodaj element");
        putValue(SHORT_DESCRIPTION, "");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //greske pri unosu podataka:
        if (!MainFrame.getInstance().getKlasaProzor().getAtribut().isSelected() && !MainFrame.getInstance().getKlasaProzor().getMetoda().isSelected()){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Nije odabrano da li se dodaje atribut ili metoda!", MessageType.ERROR);
            return;
        }
        else if(!MainFrame.getInstance().getKlasaProzor().getJbPrivate().isSelected() && !MainFrame.getInstance().getKlasaProzor().getJbPublic().isSelected() && !MainFrame.getInstance().getKlasaProzor().getJbProtected().isSelected()){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Nije odabrana vidljivost!", MessageType.ERROR);
            return;
        }
        else if(!MainFrame.getInstance().getKlasaProzor().getJbInt().isSelected() &&
                !MainFrame.getInstance().getKlasaProzor().getJbFloat().isSelected() &&
                !MainFrame.getInstance().getKlasaProzor().getJbDouble().isSelected() &&
                !MainFrame.getInstance().getKlasaProzor().getJbString().isSelected() &&
                !MainFrame.getInstance().getKlasaProzor().getJbBoolean().isSelected() &&
                !MainFrame.getInstance().getKlasaProzor().getJbVoid().isSelected()){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Nije odabran tip!", MessageType.ERROR);
            return;
        }
        else if(MainFrame.getInstance().getKlasaProzor().getTfNaziv().getText().isEmpty() || MainFrame.getInstance().getKlasaProzor().getTfNaziv().getText().equals(" ")){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Nije upisan naziv!", MessageType.ERROR);
            return;
        }
        else if(MainFrame.getInstance().getKlasaProzor().getAtribut().isSelected() && MainFrame.getInstance().getKlasaProzor().getJbVoid().isSelected()){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Atribut ne moze imati tip void!", MessageType.ERROR);
            return;
        }
        else if(MainFrame.getInstance().getKlasaProzor().getTfNaziv().getText().trim().contains(" ")){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Naziv ne sme da sadrzi razmak!", MessageType.ERROR);
            MainFrame.getInstance().getKlasaProzor().getTfNaziv().setText("");
            return;
        }

        Vidljivost vidljivost = null;
        if(MainFrame.getInstance().getKlasaProzor().getJbPrivate().isSelected()) vidljivost = Vidljivost.PRIVATE;
        else if(MainFrame.getInstance().getKlasaProzor().getJbPublic().isSelected()) vidljivost = Vidljivost.PUBLIC;
        else if(MainFrame.getInstance().getKlasaProzor().getJbProtected().isSelected()) vidljivost = Vidljivost.PROTECTED;

        String tip = null;
        if(MainFrame.getInstance().getKlasaProzor().getJbInt().isSelected()) tip = "int";
        else if(MainFrame.getInstance().getKlasaProzor().getJbFloat().isSelected()) tip = "float";
        else if(MainFrame.getInstance().getKlasaProzor().getJbDouble().isSelected()) tip = "double";
        else if(MainFrame.getInstance().getKlasaProzor().getJbString().isSelected()) tip = "string";
        else if(MainFrame.getInstance().getKlasaProzor().getJbBoolean().isSelected()) tip = "boolean";
        else if(MainFrame.getInstance().getKlasaProzor().getJbVoid().isSelected()) tip = "void";

        String naziv = MainFrame.getInstance().getKlasaProzor().getTfNaziv().getText().toLowerCase();

        //provera da li je duplikat
        for(ClassContent c : ((Klasa) klasaPainter.getElement()).getClassContentList())
            if( (c.getNaziv().equals(naziv) && c instanceof Atributi && MainFrame.getInstance().getKlasaProzor().getAtribut().isSelected())
            || (c.getNaziv().equals(naziv) && c instanceof Metode && MainFrame.getInstance().getKlasaProzor().getMetoda().isSelected()) ) {
                ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Zeljen naziv je zauzet!", MessageType.ERROR);
                MainFrame.getInstance().getKlasaProzor().getTfNaziv().setText("");
                return;
            }
        Atributi a = new Atributi(vidljivost, tip, naziv);
        Metode m = new Metode(vidljivost, tip, naziv);
        /*
        if(MainFrame.getInstance().getKlasaProzor().getAtribut().isSelected()) {
            ((Klasa) klasaPainter.getElement()).getClassContentList().add(new Atributi(vidljivost, tip, naziv));
        }
        else if(MainFrame.getInstance().getKlasaProzor().getMetoda().isSelected()) {
            ((Klasa) klasaPainter.getElement()).getClassContentList().add(new Metode(vidljivost, tip, naziv));
        } dijagramView.repaint();
        */
        AbstractCommand command = new AddContentCommand(a, m, null, (Klasa) klasaPainter.getElement(), dijagramView);
        ((DijagramView) MainFrame.getInstance().getPackageView().getjTabbedPane().getSelectedComponent()).getCommandManager().addCommand(command);

        MainFrame.getInstance().getKlasaProzor().getTfNaziv().setText("");
        MainFrame.getInstance().getKlasaProzor().setClassContentList(((Klasa) klasaPainter.getElement()).getClassContentList());
        MainFrame.getInstance().getKlasaProzor().getBg().clearSelection();
        MainFrame.getInstance().getKlasaProzor().getBgVidljivost().clearSelection();
        MainFrame.getInstance().getKlasaProzor().getBgTip().clearSelection();
    }
}