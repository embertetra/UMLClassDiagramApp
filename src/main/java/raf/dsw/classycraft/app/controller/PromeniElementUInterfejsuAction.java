package raf.dsw.classycraft.app.controller;

import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.Metode;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Interfejs;
import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.interclass.Vidljivost;
import raf.dsw.classycraft.app.commands.AbstractCommand;
import raf.dsw.classycraft.app.commands.implementation.ChangeContentCommand;
import raf.dsw.classycraft.app.core.ApplicationFramework;
import raf.dsw.classycraft.app.errorHandler.MessageType;
import raf.dsw.classycraft.app.gui.swing.view.DijagramView;
import raf.dsw.classycraft.app.gui.swing.view.MainFrame;
import raf.dsw.classycraft.app.gui.swing.view.painters.InterclassPainter;
import raf.dsw.classycraft.app.gui.swing.view.painters.interclassPainter.InterfejsPainter;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PromeniElementUInterfejsuAction extends AbstractClassyAction{

    private InterfejsPainter interfejsPainter;
    private DijagramView dijagramView;

    public PromeniElementUInterfejsuAction(InterclassPainter interclassPainter, DijagramView d) {

        interfejsPainter = (InterfejsPainter) interclassPainter;
        dijagramView = d;

        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(null));
        putValue(NAME, "Promeni element");
        putValue(SHORT_DESCRIPTION, "");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //greske pri unosu:
        if (MainFrame.getInstance().getInterfejsProzor().getLista().getSelectedValue() == null) {
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Nije odabran element iz liste!", MessageType.ERROR);
            return;
        } else if (!MainFrame.getInstance().getInterfejsProzor().getJbPrivate().isSelected() &&
                !MainFrame.getInstance().getInterfejsProzor().getJbPublic().isSelected() &&
                !MainFrame.getInstance().getInterfejsProzor().getJbProtected().isSelected() &&
                !MainFrame.getInstance().getInterfejsProzor().getJbInt().isSelected() &&
                !MainFrame.getInstance().getInterfejsProzor().getJbFloat().isSelected() &&
                !MainFrame.getInstance().getInterfejsProzor().getJbDouble().isSelected() &&
                !MainFrame.getInstance().getInterfejsProzor().getJbString().isSelected() &&
                !MainFrame.getInstance().getInterfejsProzor().getJbBoolean().isSelected() &&
                !MainFrame.getInstance().getInterfejsProzor().getJbVoid().isSelected() &&
                (MainFrame.getInstance().getInterfejsProzor().getTfNaziv().getText().isEmpty()
                        || MainFrame.getInstance().getInterfejsProzor().getTfNaziv().getText().equals(" "))) {
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Nije unet podatak za promenu!", MessageType.ERROR);
            return;
        } else if (MainFrame.getInstance().getInterfejsProzor().getTfNaziv().getText() != null && MainFrame.getInstance().getInterfejsProzor().getTfNaziv().getText().trim().contains(" ")) {
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Naziv ne sme da sadrzi razmak!", MessageType.ERROR);
            MainFrame.getInstance().getInterfejsProzor().getTfNaziv().setText("");
            return;
        }

        //provera da li je promenjeni element duplikat
        Metode promeni = MainFrame.getInstance().getInterfejsProzor().getLista().getSelectedValue();
        String vidljivost = null;
        String tip = null;
        String naziv = null;
        Vidljivost v = null;

        for (Metode m : MainFrame.getInstance().getInterfejsProzor().getMetodeList()) {
            if (m.getTip().equals(promeni.getTip()) && m.getNaziv().equals(promeni.getNaziv()) && m.getVidljivost().equals(promeni.getVidljivost())) {

                if (m.getVidljivost().equals("+")) v = Vidljivost.PUBLIC;
                else if (m.getVidljivost().equals("-")) v = Vidljivost.PRIVATE;
                else if (m.getVidljivost().equals("#")) v = Vidljivost.PROTECTED;


                if (MainFrame.getInstance().getInterfejsProzor().getJbPrivate().isSelected()) {
                    vidljivost = "-";
                    v = Vidljivost.PRIVATE;
                } else if (MainFrame.getInstance().getInterfejsProzor().getJbPublic().isSelected()) {
                    vidljivost = "+";
                    v = Vidljivost.PUBLIC;
                } else if (MainFrame.getInstance().getInterfejsProzor().getJbProtected().isSelected()) {
                    vidljivost = "#";
                    v = Vidljivost.PROTECTED;
                }
                else vidljivost = m.getVidljivost();

                if (MainFrame.getInstance().getInterfejsProzor().getJbInt().isSelected()) tip = "int";
                else if (MainFrame.getInstance().getInterfejsProzor().getJbFloat().isSelected()) tip = "float";
                else if (MainFrame.getInstance().getInterfejsProzor().getJbDouble().isSelected()) tip = "double";
                else if (MainFrame.getInstance().getInterfejsProzor().getJbString().isSelected()) tip = "string";
                else if (MainFrame.getInstance().getInterfejsProzor().getJbBoolean().isSelected()) tip = "boolean";
                else if (MainFrame.getInstance().getInterfejsProzor().getJbVoid().isSelected()) tip = "void";
                else tip = m.getTip();


                if (MainFrame.getInstance().getInterfejsProzor().getTfNaziv().getText() != null && !MainFrame.getInstance().getInterfejsProzor().getTfNaziv().getText().isEmpty())
                    naziv = MainFrame.getInstance().getInterfejsProzor().getTfNaziv().getText();
                else
                    naziv = m.getNaziv();

                break;
            }
        }

        for (Metode m : MainFrame.getInstance().getInterfejsProzor().getMetodeList()) {
            if (m.getVidljivost().equals(vidljivost) && m.getTip().equals(tip) && m.getNaziv().equals(naziv)){
                ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Metoda vec postoji!", MessageType.ERROR);
                MainFrame.getInstance().getInterfejsProzor().getTfNaziv().setText("");
                MainFrame.getInstance().getInterfejsProzor().getBgVidljivost().clearSelection();
                MainFrame.getInstance().getInterfejsProzor().getBgTip().clearSelection();
                return;
            }
        }

        int index = 0;
        for(int i=0; i<MainFrame.getInstance().getInterfejsProzor().getMetodeList().size(); i++){
            if(MainFrame.getInstance().getInterfejsProzor().getMetodeList().get(i).getNaziv().equals(MainFrame.getInstance().getInterfejsProzor().getLista().getSelectedValue().getNaziv())
            && MainFrame.getInstance().getInterfejsProzor().getMetodeList().get(i).getTip().equals(MainFrame.getInstance().getInterfejsProzor().getLista().getSelectedValue().getTip())
            && MainFrame.getInstance().getInterfejsProzor().getMetodeList().get(i).getVidljivost().equals(MainFrame.getInstance().getInterfejsProzor().getLista().getSelectedValue().getVidljivost()))
                index = i;
        }

        Metode metoda;
        if(v==Vidljivost.PRIVATE)
            metoda = new Metode(Vidljivost.PRIVATE, tip, naziv);
        else if(v==Vidljivost.PUBLIC)
            metoda = new Metode(Vidljivost.PUBLIC, tip, naziv);
        else
            metoda = new Metode(Vidljivost.PROTECTED, tip, naziv);

        AbstractCommand command = new ChangeContentCommand(index, null, metoda, null, dijagramView, (Interfejs) interfejsPainter.getElement());
        ((DijagramView) MainFrame.getInstance().getPackageView().getjTabbedPane().getSelectedComponent()).getCommandManager().addCommand(command);

        MainFrame.getInstance().getInterfejsProzor().getTfNaziv().setText("");
        MainFrame.getInstance().getInterfejsProzor().getBgVidljivost().clearSelection();
        MainFrame.getInstance().getInterfejsProzor().getBgTip().clearSelection();
    }
}
