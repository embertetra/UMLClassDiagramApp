package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.Atributi;
import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.ClassContent;
import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.Metode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Klasa;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Vidljivost;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.MessageType;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.KlasaPainter;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PromeniElementUKlasiAction extends AbstractClassyAction{

    private KlasaPainter klasaPainter;
    private DijagramView dijagramView;

    public PromeniElementUKlasiAction(InterclassPainter interclassPainter, DijagramView d) {

        klasaPainter = (KlasaPainter) interclassPainter;
        dijagramView = d;

        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(null));
        putValue(NAME, "Promeni element");
        putValue(SHORT_DESCRIPTION, "");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //greske pri unosu:
        if(MainFrame.getInstance().getKlasaProzor().getLista().getSelectedValue() == null){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Nije odabran element iz liste!", MessageType.ERROR);
            return;
        }
        else if(!MainFrame.getInstance().getKlasaProzor().getAtribut().isSelected() &&
                !MainFrame.getInstance().getKlasaProzor().getMetoda().isSelected() &&
                !MainFrame.getInstance().getKlasaProzor().getJbPrivate().isSelected() &&
                !MainFrame.getInstance().getKlasaProzor().getJbPublic().isSelected() &&
                !MainFrame.getInstance().getKlasaProzor().getJbProtected().isSelected() &&
                !MainFrame.getInstance().getKlasaProzor().getJbInt().isSelected() &&
                !MainFrame.getInstance().getKlasaProzor().getJbFloat().isSelected() &&
                !MainFrame.getInstance().getKlasaProzor().getJbDouble().isSelected() &&
                !MainFrame.getInstance().getKlasaProzor().getJbString().isSelected() &&
                !MainFrame.getInstance().getKlasaProzor().getJbBoolean().isSelected() &&
                !MainFrame.getInstance().getKlasaProzor().getJbVoid().isSelected() &&
                (MainFrame.getInstance().getKlasaProzor().getTfNaziv().getText().isEmpty() ||
                MainFrame.getInstance().getKlasaProzor().getTfNaziv().getText().equals(" ")) ){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Nije unet podatak za promenu!", MessageType.ERROR);
            return;
        }
        else if(MainFrame.getInstance().getKlasaProzor().getTfNaziv().getText().trim().contains(" ")){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Naziv ne sme da sadrzi razmak!", MessageType.ERROR);
            MainFrame.getInstance().getKlasaProzor().getTfNaziv().setText("");
            return;
        }
        else if(MainFrame.getInstance().getKlasaProzor().getAtribut().isSelected() && MainFrame.getInstance().getKlasaProzor().getLista().getSelectedValue() instanceof Metode){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Metoda ne moze da postane atribut!", MessageType.ERROR);
            return;
        }
        else if(MainFrame.getInstance().getKlasaProzor().getMetoda().isSelected() && MainFrame.getInstance().getKlasaProzor().getLista().getSelectedValue() instanceof Atributi){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Atribut ne moze da postane metoda!", MessageType.ERROR);
            return;
        }
        else if(MainFrame.getInstance().getKlasaProzor().getLista().getSelectedValue() instanceof Atributi && MainFrame.getInstance().getKlasaProzor().getJbVoid().isSelected()){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Atribut ne moze imati tip void!", MessageType.ERROR);
            return;
        }

        //promena
        Atributi atributi = null; Metode metode = null;
        if(MainFrame.getInstance().getKlasaProzor().getLista().getSelectedValue() instanceof Atributi)
            atributi = (Atributi) MainFrame.getInstance().getKlasaProzor().getLista().getSelectedValue();
        else if(MainFrame.getInstance().getKlasaProzor().getLista().getSelectedValue() instanceof Metode)
            metode = (Metode) MainFrame.getInstance().getKlasaProzor().getLista().getSelectedValue();

        for(ClassContent c : MainFrame.getInstance().getKlasaProzor().getClassContentList()){
            if(atributi != null && c instanceof Atributi && c.getVidljivost().equals(atributi.getVidljivost()) && c.getTip().equals(atributi.getTip()) && c.getNaziv().equals(atributi.getNaziv())){

                if(MainFrame.getInstance().getKlasaProzor().getJbPrivate().isSelected()) c.setVidljivost(Vidljivost.PRIVATE);
                else if(MainFrame.getInstance().getKlasaProzor().getJbPublic().isSelected()) c.setVidljivost(Vidljivost.PUBLIC);
                else if(MainFrame.getInstance().getKlasaProzor().getJbProtected().isSelected()) c.setVidljivost(Vidljivost.PROTECTED);

                if(MainFrame.getInstance().getKlasaProzor().getJbInt().isSelected()) c.setTip("int");
                else if(MainFrame.getInstance().getKlasaProzor().getJbFloat().isSelected()) c.setTip("float");
                else if(MainFrame.getInstance().getKlasaProzor().getJbDouble().isSelected()) c.setTip("double");
                else if(MainFrame.getInstance().getKlasaProzor().getJbString().isSelected()) c.setTip("string");
                else if(MainFrame.getInstance().getKlasaProzor().getJbBoolean().isSelected()) c.setTip("boolean");

                if(MainFrame.getInstance().getKlasaProzor().getTfNaziv().getText() != null && !MainFrame.getInstance().getKlasaProzor().getTfNaziv().getText().equals(""))
                    c.setNaziv(MainFrame.getInstance().getKlasaProzor().getTfNaziv().getText());
                break;
            }
            else if(metode != null){

                if(MainFrame.getInstance().getKlasaProzor().getJbPrivate().isSelected()) c.setVidljivost(Vidljivost.PRIVATE);
                else if(MainFrame.getInstance().getKlasaProzor().getJbPublic().isSelected()) c.setVidljivost(Vidljivost.PUBLIC);
                else if(MainFrame.getInstance().getKlasaProzor().getJbProtected().isSelected()) c.setVidljivost(Vidljivost.PROTECTED);

                if(MainFrame.getInstance().getKlasaProzor().getJbInt().isSelected()) c.setTip("int");
                else if(MainFrame.getInstance().getKlasaProzor().getJbFloat().isSelected()) c.setTip("float");
                else if(MainFrame.getInstance().getKlasaProzor().getJbDouble().isSelected()) c.setTip("double");
                else if(MainFrame.getInstance().getKlasaProzor().getJbString().isSelected()) c.setTip("string");
                else if(MainFrame.getInstance().getKlasaProzor().getJbBoolean().isSelected()) c.setTip("boolean");
                else if(MainFrame.getInstance().getKlasaProzor().getJbVoid().isSelected()) c.setTip("void");

                if(MainFrame.getInstance().getKlasaProzor().getTfNaziv().getText() != null && !MainFrame.getInstance().getKlasaProzor().getTfNaziv().getText().equals(""))
                    c.setNaziv(MainFrame.getInstance().getKlasaProzor().getTfNaziv().getText());
                break;
            }
        }

        dijagramView.repaint();
        MainFrame.getInstance().getKlasaProzor().setClassContentList(((Klasa) klasaPainter.getElement()).getClassContentList());
        MainFrame.getInstance().getKlasaProzor().getBg().clearSelection();
        MainFrame.getInstance().getKlasaProzor().getBgVidljivost().clearSelection();
        MainFrame.getInstance().getKlasaProzor().getBgTip().clearSelection();
    }
}
