package raf.dsw.classycraft.app.gui.swing.view;


import raf.dsw.classycraft.app.classyCraftRepository.composite.classContent.Metode;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class InterfejsProzor extends JFrame {

    private List<Metode> metodeList;
    private DefaultListModel<Metode> defaultListModel;
    private  JList<Metode> lista;

    private JButton jbPromeni;
    private JButton jbObrisi;

    private JButton jbIme;
    private JTextField tfIme;

    private ButtonGroup bgVidljivost;
    private JRadioButton jbPrivate;
    private JRadioButton jbPublic;
    private JRadioButton jbProtected;

    private ButtonGroup bgTip;
    private JRadioButton jbInt;
    private JRadioButton jbFloat;
    private JRadioButton jbDouble;
    private JRadioButton jbString;
    private JRadioButton jbBoolean;
    private JRadioButton jbVoid;

    private JTextField tfNaziv;

    private JButton jbDodaj;

    public InterfejsProzor(){

        //namestanje prozora
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth/3, screenHeight/3);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Dodavanje u interfejs");

        //prikaz metoda
        JLabel lbLista = new JLabel("Lista metoda:");
        metodeList = new ArrayList<>();
        defaultListModel = new DefaultListModel<>();
        lista = new JList<>();
        lista.setModel(defaultListModel);
        for(Metode m : metodeList)
            defaultListModel.addElement(m);

        //dugmad
        jbPromeni = new JButton("Promeni element");
        jbObrisi = new JButton("Obrisi element");
        jbDodaj = new JButton("Dodaj");
        //horizontalno grupisanje
        JPanel jpPromeniObrisi = new JPanel();
        jpPromeniObrisi.setAlignmentX(Component.LEFT_ALIGNMENT);
        jpPromeniObrisi.setLayout(new BoxLayout(jpPromeniObrisi, BoxLayout.X_AXIS));
        jpPromeniObrisi.add(jbDodaj); jpPromeniObrisi.add(jbPromeni); jpPromeniObrisi.add(jbObrisi);

        //polje za proenu imena klase
        JLabel lbIme = new JLabel("Novo ime:");
        tfIme = new JTextField();
        jbIme = new JButton("Promeni ime");

        //vidljivost : + - #
        JLabel lbVidljivost = new JLabel("Vidljivost:");
        bgVidljivost = new ButtonGroup();
        jbPrivate = new JRadioButton("private");
        jbPublic = new JRadioButton("public");
        jbProtected = new JRadioButton("protected");
        bgVidljivost.add(jbPrivate); bgVidljivost.add(jbPublic); bgVidljivost.add(jbProtected);
        //horizontalno grupisanje
        JPanel jpVidljivost = new JPanel();
        jpVidljivost.setAlignmentX(Component.LEFT_ALIGNMENT);
        jpVidljivost.setLayout(new BoxLayout(jpVidljivost, BoxLayout.X_AXIS));
        jpVidljivost.add(jbPrivate); jpVidljivost.add(jbPublic); jpVidljivost.add(jbProtected);

        // tip
        JLabel lbTip = new JLabel("Tip:");
        bgTip = new ButtonGroup();
        jbInt = new JRadioButton("int");
        jbFloat = new JRadioButton("float");
        jbDouble = new JRadioButton("double");
        jbString = new JRadioButton("string");
        jbBoolean = new JRadioButton("boolean");
        jbVoid = new JRadioButton("void");
        bgTip.add(jbInt); bgTip.add(jbFloat); bgTip.add(jbDouble); bgTip.add(jbString); bgTip.add(jbBoolean); bgTip.add(jbVoid);
        //horizontalno grupisanje
        JPanel jpTip = new JPanel();
        jpTip.setAlignmentX(Component.LEFT_ALIGNMENT);
        jpTip.setLayout(new BoxLayout(jpTip, BoxLayout.X_AXIS));
        jpTip.add(jbInt); jpTip.add(jbFloat); jpTip.add(jbDouble); jpTip.add(jbString); jpTip.add(jbBoolean);

        //naziv
        JLabel lbNaziv = new JLabel("Naziv:");
        tfNaziv = new JTextField();

        //glavni JPanel:
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        jPanel.add(new JLabel("Prilikom promene elementa, selektovati zeljene promene kao i sam element iz liste."));
        jPanel.add(lbLista); jPanel.add(new JScrollPane(lista));
        jPanel.add(lbIme); jPanel.add(tfIme); jPanel.add(jbIme);
        jPanel.add(lbVidljivost); jPanel.add(jpVidljivost);
        jPanel.add(lbTip); jPanel.add(jpTip);
        jPanel.add(lbNaziv); jPanel.add(tfNaziv);
        jPanel.add(jpPromeniObrisi);

        jPanel.setBorder(new EmptyBorder(new Insets(15, 10, 15, 10)));
        this.add(jPanel);
    }

    public void setMetodeList(List<Metode> metodeList) {
        this.metodeList = metodeList;

        defaultListModel.clear();
        for(Metode m : metodeList)
            defaultListModel.addElement(m);
    }

    public ButtonGroup getBgVidljivost() {
        return bgVidljivost;
    }

    public ButtonGroup getBgTip() {
        return bgTip;
    }

    public List<Metode> getMetodeList() {
        return metodeList;
    }

    public DefaultListModel<Metode> getDefaultListModel() {
        return defaultListModel;
    }

    public JList<Metode> getLista() {
        return lista;
    }

    public JButton getJbPromeni() {
        return jbPromeni;
    }

    public JButton getJbObrisi() {
        return jbObrisi;
    }

    public JRadioButton getJbVoid() {
        return jbVoid;
    }

    public JButton getJbIme() {
        return jbIme;
    }

    public JTextField getTfIme() {
        return tfIme;
    }

    public JRadioButton getJbPrivate() {
        return jbPrivate;
    }

    public JRadioButton getJbPublic() {
        return jbPublic;
    }

    public JRadioButton getJbProtected() {
        return jbProtected;
    }

    public JRadioButton getJbInt() {
        return jbInt;
    }

    public JRadioButton getJbFloat() {
        return jbFloat;
    }

    public JRadioButton getJbDouble() {
        return jbDouble;
    }

    public JRadioButton getJbString() {
        return jbString;
    }

    public JRadioButton getJbBoolean() {
        return jbBoolean;
    }

    public JTextField getTfNaziv() {
        return tfNaziv;
    }

    public JButton getJbDodaj() {
        return jbDodaj;
    }
}
