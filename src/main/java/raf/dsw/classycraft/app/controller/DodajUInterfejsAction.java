package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.Metode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Interfejs;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Vidljivost;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.MessageType;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.InterfejsPainter;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DodajUInterfejsAction extends AbstractClassyAction{
    private InterfejsPainter interfejsPainter;
    private DijagramView dijagramView;

    public DodajUInterfejsAction(InterclassPainter interclassPainter, DijagramView d) {

        interfejsPainter = (InterfejsPainter) interclassPainter;
        dijagramView = d;

        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(null));
        putValue(NAME, "Dodaj");
        putValue(SHORT_DESCRIPTION, "");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //greske pri unosu podataka:
        if(!MainFrame.getInstance().getInterfejsProzor().getJbPrivate().isSelected() && !MainFrame.getInstance().getInterfejsProzor().getJbPublic().isSelected() && !MainFrame.getInstance().getInterfejsProzor().getJbProtected().isSelected()){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Nije odabrana vidljivost.", MessageType.ERROR);
            return;
        }
        else if(!MainFrame.getInstance().getInterfejsProzor().getJbInt().isSelected() &&
                !MainFrame.getInstance().getInterfejsProzor().getJbFloat().isSelected() &&
                !MainFrame.getInstance().getInterfejsProzor().getJbDouble().isSelected() &&
                !MainFrame.getInstance().getInterfejsProzor().getJbString().isSelected() &&
                !MainFrame.getInstance().getInterfejsProzor().getJbBoolean().isSelected()){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Nije odabran tip.", MessageType.ERROR);
            return;
        }
        else if(MainFrame.getInstance().getInterfejsProzor().getTfNaziv().getText().isEmpty() || MainFrame.getInstance().getInterfejsProzor().getTfNaziv().getText().equals(" ")){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Nije upisano ime.", MessageType.ERROR);
            return;
        }

        Vidljivost vidljivost = null;
        if(MainFrame.getInstance().getInterfejsProzor().getJbPrivate().isSelected()) vidljivost = Vidljivost.PRIVATE;
        else if(MainFrame.getInstance().getInterfejsProzor().getJbPublic().isSelected()) vidljivost = Vidljivost.PUBLIC;
        else if(MainFrame.getInstance().getInterfejsProzor().getJbProtected().isSelected()) vidljivost = Vidljivost.PROTECTED;

        String tip = null;
        if(MainFrame.getInstance().getInterfejsProzor().getJbInt().isSelected()) tip = "int";
        else if(MainFrame.getInstance().getInterfejsProzor().getJbFloat().isSelected()) tip = "float";
        else if(MainFrame.getInstance().getInterfejsProzor().getJbDouble().isSelected()) tip = "double";
        else if(MainFrame.getInstance().getInterfejsProzor().getJbString().isSelected()) tip = "string";
        else if(MainFrame.getInstance().getInterfejsProzor().getJbBoolean().isSelected()) tip = "boolean";

        String naziv = MainFrame.getInstance().getInterfejsProzor().getTfNaziv().getText().toLowerCase();

        //provera da li je duplikat
        for(Metode m : ((Interfejs) interfejsPainter.getElement()).getMetodeList() )
            if(m.getNaziv().equals(naziv)) {
                ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Zeljeno ime je vec rezervisano!", MessageType.ERROR);
                MainFrame.getInstance().getInterfejsProzor().getTfNaziv().setText("");
                return;
            }

        ((Interfejs) interfejsPainter.getElement()).getMetodeList().add(new Metode(vidljivost, tip, naziv));

        dijagramView.repaint();
        MainFrame.getInstance().getInterfejsProzor().getTfNaziv().setText("");
    }
}
