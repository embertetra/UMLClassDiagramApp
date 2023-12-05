package raf.dsw.classycraft.app.gui.swing.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class KlasaProzor extends JFrame {

    public KlasaProzor(){

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth/3, screenHeight/3);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Dodavanje u klasu");

        ButtonGroup bg = new ButtonGroup();
        JRadioButton atribut = new JRadioButton("Atribut");
        JRadioButton metoda = new JRadioButton("Metoda");
        bg.add(atribut); bg.add(metoda);

        JLabel lbVidljivost = new JLabel("Vidljivost:");
        ButtonGroup bgVidljivost = new ButtonGroup();
        JRadioButton jbPrivate = new JRadioButton("private");
        JRadioButton jbPublic = new JRadioButton("public");
        JRadioButton jbProtected = new JRadioButton("protected");
        bgVidljivost.add(jbPrivate); bgVidljivost.add(jbPublic); bgVidljivost.add(jbProtected);

        JLabel lbTip = new JLabel("Tip:");
        JTextField tfTip = new JTextField();

        JLabel lbNaziv = new JLabel("Naziv:");
        JTextField tfNaziv = new JTextField();

        JPanel jpAtrMet = new JPanel();
        jpAtrMet.setAlignmentX(Component.LEFT_ALIGNMENT);
        jpAtrMet.setLayout(new BoxLayout(jpAtrMet, BoxLayout.X_AXIS));
        jpAtrMet.add(atribut); jpAtrMet.add(metoda);

        JPanel jpVidljivost = new JPanel();
        jpVidljivost.setAlignmentX(Component.LEFT_ALIGNMENT);
        jpVidljivost.setLayout(new BoxLayout(jpVidljivost, BoxLayout.X_AXIS));
        jpVidljivost.add(jbPrivate); jpVidljivost.add(jbPublic); jpVidljivost.add(jbProtected);

        JButton jbDodaj = new JButton("Dodaj");

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        jPanel.add(jpAtrMet); jPanel.add(lbVidljivost); jPanel.add(jpVidljivost);jPanel.add(lbTip);
        jPanel.add(tfTip); jPanel.add(lbNaziv); jPanel.add(tfNaziv); jPanel.add(jbDodaj);

        jPanel.setBorder(new EmptyBorder(new Insets(15, 10, 15, 10)));
        this.add(jPanel);
    }


}
