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
        if(MainFrame.getInstance().getInterfejsProzor().getLista().getSelectedValue() == null){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Nije odabran element iz liste!", MessageType.ERROR);
            return;
        }
        else if(!MainFrame.getInstance().getInterfejsProzor().getJbPrivate().isSelected() &&
                !MainFrame.getInstance().getInterfejsProzor().getJbPublic().isSelected() &&
                !MainFrame.getInstance().getInterfejsProzor().getJbProtected().isSelected() &&
                !MainFrame.getInstance().getInterfejsProzor().getJbInt().isSelected() &&
                !MainFrame.getInstance().getInterfejsProzor().getJbFloat().isSelected() &&
                !MainFrame.getInstance().getInterfejsProzor().getJbDouble().isSelected() &&
                !MainFrame.getInstance().getInterfejsProzor().getJbString().isSelected() &&
                !MainFrame.getInstance().getInterfejsProzor().getJbBoolean().isSelected() &&
                !MainFrame.getInstance().getInterfejsProzor().getJbVoid().isSelected() &&
                (MainFrame.getInstance().getInterfejsProzor().getTfNaziv().getText().isEmpty()
                        || MainFrame.getInstance().getInterfejsProzor().getTfNaziv().getText().equals(" "))){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Nije unet podatak za promenu!", MessageType.ERROR);
            return;
        }
        else if(MainFrame.getInstance().getInterfejsProzor().getTfNaziv().getText() != null && MainFrame.getInstance().getInterfejsProzor().getTfNaziv().getText().trim().contains(" ")){
            ApplicationFramework.getInstance().getMessageGenerator().GenerateMessage("Naziv ne sme da sadrzi razmak!", MessageType.ERROR);
            MainFrame.getInstance().getInterfejsProzor().getTfNaziv().setText("");
            return;
        }

        //promena
        Metode promeni = MainFrame.getInstance().getInterfejsProzor().getLista().getSelectedValue();

        for(Metode m : MainFrame.getInstance().getInterfejsProzor().getMetodeList()){
            if(m.getTip().equals(promeni.getTip()) && m.getNaziv().equals(promeni.getNaziv()) && m.getVidljivost().equals(promeni.getVidljivost())){

                if(MainFrame.getInstance().getInterfejsProzor().getJbPrivate().isSelected()) m.setVidljivost(Vidljivost.PRIVATE);
                else if(MainFrame.getInstance().getInterfejsProzor().getJbPublic().isSelected()) m.setVidljivost(Vidljivost.PUBLIC);
                else if(MainFrame.getInstance().getInterfejsProzor().getJbProtected().isSelected()) m.setVidljivost(Vidljivost.PROTECTED);

                if(MainFrame.getInstance().getInterfejsProzor().getJbInt().isSelected()) m.setTip("int");
                else if(MainFrame.getInstance().getInterfejsProzor().getJbFloat().isSelected()) m.setTip("float");
                else if(MainFrame.getInstance().getInterfejsProzor().getJbDouble().isSelected()) m.setTip("double");
                else if(MainFrame.getInstance().getInterfejsProzor().getJbString().isSelected()) m.setTip("string");
                else if(MainFrame.getInstance().getInterfejsProzor().getJbBoolean().isSelected()) m.setTip("boolean");
                else if(MainFrame.getInstance().getInterfejsProzor().getJbVoid().isSelected()) m.setTip("void");

                if(MainFrame.getInstance().getInterfejsProzor().getTfNaziv().getText() != null && !MainFrame.getInstance().getInterfejsProzor().getTfNaziv().getText().equals(""))
                    m.setNaziv(MainFrame.getInstance().getInterfejsProzor().getTfNaziv().getText());
                break;
            }
        }

        dijagramView.repaint();
        MainFrame.getInstance().getInterfejsProzor().setMetodeList(((Interfejs) interfejsPainter.getElement()).getMetodeList());
        MainFrame.getInstance().getInterfejsProzor().getTfNaziv().setText("");
        MainFrame.getInstance().getInterfejsProzor().getBgVidljivost().clearSelection();
        MainFrame.getInstance().getInterfejsProzor().getBgTip().clearSelection();
    }
}
