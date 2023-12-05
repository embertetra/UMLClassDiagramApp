package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.controller.DodajUEnumAction;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EnumProzor extends JFrame {

    private JTextField tfNaziv;

    private JButton jbDodaj;

    public EnumProzor(){

        //namestanje prozora
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth/3, screenHeight/3);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Dodavanje u enum");

        JLabel lbNaziv = new JLabel("Naziv:");
        tfNaziv = new JTextField();

        jbDodaj = new JButton("Dodaj");

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        jPanel.add(lbNaziv); jPanel.add(tfNaziv); jPanel.add(jbDodaj);

        jPanel.setBorder(new EmptyBorder(new Insets(15, 10, 15, 10)));
        this.add(jPanel);
    }

    public JTextField getTfNaziv() {
        return tfNaziv;
    }

    public void setTfNaziv(JTextField tfNaziv) {
        this.tfNaziv = tfNaziv;
    }

    public JButton getJbDodaj() {
        return jbDodaj;
    }

    public void setJbDodaj(JButton jbDodaj) {
        this.jbDodaj = jbDodaj;
    }
}
