package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.Atributi;
import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.ClassContent;
import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.Metode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Klasa;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Vidljivost;
import raf.dsw.classycraft.app.commands.AbstractCommand;
import raf.dsw.classycraft.app.commands.implementation.ChangeContentCommand;
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
            MainFrame.getInstance().getKlasaProzor().getBg().clearSelection();
            return;
        }
        else if(MainFrame.getInstance().getKlasaProzor().getMetoda().isSelected() && MainFrame.getInstance().getKlasaProzor().getLista().getSelectedValue() instanceof Atributi){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Atribut ne moze da postane metoda!", MessageType.ERROR);
            MainFrame.getInstance().getKlasaProzor().getBg().clearSelection();
            return;
        }
        else if(MainFrame.getInstance().getKlasaProzor().getLista().getSelectedValue() instanceof Atributi && MainFrame.getInstance().getKlasaProzor().getJbVoid().isSelected()){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Atribut ne moze imati tip void!", MessageType.ERROR);
            MainFrame.getInstance().getKlasaProzor().getBgTip().clearSelection();
            return;
        }

        //provera da li je promenjeni element duplikat
        String vidljivost = null;
        String tip = null;
        String naziv = null;
        Vidljivost v = null;

        Atributi atributi = null;
        Metode metode = null;
        if(MainFrame.getInstance().getKlasaProzor().getLista().getSelectedValue() instanceof Atributi)
            atributi = (Atributi) MainFrame.getInstance().getKlasaProzor().getLista().getSelectedValue();
        else if(MainFrame.getInstance().getKlasaProzor().getLista().getSelectedValue() instanceof Metode)
            metode = (Metode) MainFrame.getInstance().getKlasaProzor().getLista().getSelectedValue();

        for(ClassContent c : MainFrame.getInstance().getKlasaProzor().getClassContentList()){
            if(atributi != null && c instanceof Atributi && c.getVidljivost().equals(atributi.getVidljivost()) && c.getTip().equals(atributi.getTip()) && c.getNaziv().equals(atributi.getNaziv())){

                if (c.getVidljivost().equals("+")) v = Vidljivost.PUBLIC;
                else if (c.getVidljivost().equals("-")) v = Vidljivost.PRIVATE;
                else if (c.getVidljivost().equals("#")) v = Vidljivost.PROTECTED;

                if(MainFrame.getInstance().getKlasaProzor().getJbPrivate().isSelected()) {
                    vidljivost = "-"; v = Vidljivost.PRIVATE;
                }
                else if(MainFrame.getInstance().getKlasaProzor().getJbPublic().isSelected()) {
                    vidljivost = "+"; v = Vidljivost.PUBLIC;
                }
                else if(MainFrame.getInstance().getKlasaProzor().getJbProtected().isSelected()){
                    vidljivost = "#"; v = Vidljivost.PROTECTED;
                }
                else vidljivost = c.getVidljivost();

                if(MainFrame.getInstance().getKlasaProzor().getJbInt().isSelected()) tip = "int";
                else if(MainFrame.getInstance().getKlasaProzor().getJbFloat().isSelected()) tip = "float";
                else if(MainFrame.getInstance().getKlasaProzor().getJbDouble().isSelected()) tip = "double";
                else if(MainFrame.getInstance().getKlasaProzor().getJbString().isSelected()) tip = "string";
                else if(MainFrame.getInstance().getKlasaProzor().getJbBoolean().isSelected()) tip = "boolean";
                else tip = c.getTip();

                if(MainFrame.getInstance().getKlasaProzor().getTfNaziv().getText() != null && !MainFrame.getInstance().getKlasaProzor().getTfNaziv().getText().isEmpty())
                    naziv = MainFrame.getInstance().getKlasaProzor().getTfNaziv().getText();
                else
                    naziv = c.getNaziv();

                break;
            }
            else if(metode != null && c instanceof Metode && c.getVidljivost().equals(metode.getVidljivost()) && c.getTip().equals(metode.getTip()) && c.getNaziv().equals(metode.getNaziv())){

                if (c.getVidljivost().equals("+")) v = Vidljivost.PUBLIC;
                else if (c.getVidljivost().equals("-")) v = Vidljivost.PRIVATE;
                else if (c.getVidljivost().equals("#")) v = Vidljivost.PROTECTED;

                if(MainFrame.getInstance().getKlasaProzor().getJbPrivate().isSelected()) {
                    vidljivost = "-"; v = Vidljivost.PRIVATE;
                }
                else if(MainFrame.getInstance().getKlasaProzor().getJbPublic().isSelected()) {
                    vidljivost = "+"; v = Vidljivost.PRIVATE;
                }
                else if(MainFrame.getInstance().getKlasaProzor().getJbProtected().isSelected()){
                    vidljivost = "#"; v = Vidljivost.PROTECTED;
                }
                else vidljivost = c.getVidljivost();

                if(MainFrame.getInstance().getKlasaProzor().getJbInt().isSelected()) tip = "int";
                else if(MainFrame.getInstance().getKlasaProzor().getJbFloat().isSelected()) tip = "float";
                else if(MainFrame.getInstance().getKlasaProzor().getJbDouble().isSelected()) tip = "double";
                else if(MainFrame.getInstance().getKlasaProzor().getJbString().isSelected()) tip = "string";
                else if(MainFrame.getInstance().getKlasaProzor().getJbBoolean().isSelected()) tip = "boolean";
                else if(MainFrame.getInstance().getKlasaProzor().getJbVoid().isSelected()) tip = "void";
                else tip = c.getTip();

                if(MainFrame.getInstance().getKlasaProzor().getTfNaziv().getText() != null && !MainFrame.getInstance().getKlasaProzor().getTfNaziv().getText().isEmpty())
                    naziv = MainFrame.getInstance().getKlasaProzor().getTfNaziv().getText();
                else
                    naziv = c.getNaziv();

                break;
            }
        }
        for(ClassContent c : MainFrame.getInstance().getKlasaProzor().getClassContentList()){
            if (c instanceof Atributi && c.getVidljivost().equals(vidljivost) && c.getTip().equals(tip) && c.getNaziv().equals(naziv)){
                ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Atribut vec postoji!", MessageType.ERROR);
                MainFrame.getInstance().getInterfejsProzor().getTfNaziv().setText("");
                MainFrame.getInstance().getKlasaProzor().getBg().clearSelection();
                MainFrame.getInstance().getKlasaProzor().getBgVidljivost().clearSelection();
                MainFrame.getInstance().getKlasaProzor().getBgTip().clearSelection();
                return;
            }
            else if (c instanceof Metode && c.getVidljivost().equals(vidljivost) && c.getTip().equals(tip) && c.getNaziv().equals(naziv)){
                ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Metoda vec postoji!", MessageType.ERROR);
                MainFrame.getInstance().getInterfejsProzor().getTfNaziv().setText("");
                MainFrame.getInstance().getKlasaProzor().getBg().clearSelection();
                MainFrame.getInstance().getKlasaProzor().getBgVidljivost().clearSelection();
                MainFrame.getInstance().getKlasaProzor().getBgTip().clearSelection();
                return;
            }
        }

        Atributi atribut = null;
        Metode metoda = null;
        //promena elementa
        for(ClassContent c : MainFrame.getInstance().getKlasaProzor().getClassContentList()){
            if (c instanceof Atributi && metode == null && atributi != null){
                if(c.getVidljivost().equals(atributi.getVidljivost()) && c.getTip().equals(atributi.getTip()) && c.getNaziv().equals(atributi.getNaziv())){
                    if(v==Vidljivost.PUBLIC)
                        atribut = new Atributi(Vidljivost.PUBLIC, tip, naziv);
                    else if(v==Vidljivost.PRIVATE)
                        atribut = new Atributi(Vidljivost.PRIVATE, tip, naziv);
                    else
                        atribut = new Atributi(Vidljivost.PROTECTED, tip, naziv);
                }
            }
            else if (c instanceof Metode && atributi == null && metode != null){
                if(c.getVidljivost().equals(metode.getVidljivost()) && c.getTip().equals(metode.getTip()) && c.getNaziv().equals(metode.getNaziv())){
                    if(v==Vidljivost.PUBLIC)
                        metoda = new Metode(Vidljivost.PUBLIC, tip, naziv);
                    else if(v==Vidljivost.PRIVATE)
                        metoda = new Metode(Vidljivost.PRIVATE, tip, naziv);
                    else
                        metoda = new Metode(Vidljivost.PROTECTED, tip, naziv);

                }
            }
        }

        int index = 0;
        for(int i=0; i<MainFrame.getInstance().getKlasaProzor().getClassContentList().size(); i++){
            if(MainFrame.getInstance().getKlasaProzor().getClassContentList().get(i).equals(MainFrame.getInstance().getKlasaProzor().getLista().getSelectedValue()))
                index = i;
        }

        AbstractCommand command = new ChangeContentCommand(index, atribut, metoda, null, dijagramView, (Klasa) klasaPainter.getElement());
        ((DijagramView) MainFrame.getInstance().getPackageView().getjTabbedPane().getSelectedComponent()).getCommandManager().addCommand(command);

        ((Klasa)klasaPainter.getElement()).projectChanged();

        MainFrame.getInstance().getKlasaProzor().getTfNaziv().setText("");
        MainFrame.getInstance().getKlasaProzor().getBg().clearSelection();
        MainFrame.getInstance().getKlasaProzor().getBgVidljivost().clearSelection();
        MainFrame.getInstance().getKlasaProzor().getBgTip().clearSelection();
    }
}
