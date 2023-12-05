package raf.dsw.classycraft.app.gui.swing.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EnumProzor extends JFrame {

    private JButton jbIme;
    private JTextField tfIme;

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

        //polje za proenu imena klase
        JLabel lbIme = new JLabel("Novo ime:");
        tfIme = new JTextField();
        jbIme = new JButton("Promeni ime");

        JLabel lbNaziv = new JLabel("Naziv:");
        tfNaziv = new JTextField();

        jbDodaj = new JButton("Dodaj");

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        jPanel.add(lbIme); jPanel.add(tfIme); jPanel.add(jbIme); jPanel.add(lbNaziv); jPanel.add(tfNaziv); jPanel.add(jbDodaj);

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

    public JButton getJbIme() {
        return jbIme;
    }

    public void setJbIme(JButton jbIme) {
        this.jbIme = jbIme;
    }

    public JTextField getTfIme() {
        return tfIme;
    }

    public void setTfIme(JTextField tfIme) {
        this.tfIme = tfIme;
    }
}
