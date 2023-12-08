package raf.dsw.classycraft.app.gui.swing.view;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EnumProzor extends JFrame {

    private List<String> enumMList;
    private DefaultListModel<String> defaultListModel;
    private JList<String> lista;

    private JButton jbPromeni;
    private JButton jbObrisi;

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

        //prikaz enuma
        JLabel lbLista = new JLabel("Lista enuma:");
        enumMList = new ArrayList<>();
        defaultListModel = new DefaultListModel<>();
        lista = new JList<>();
        lista.setModel(defaultListModel);
        for(String s : enumMList)
            defaultListModel.addElement(s);


        //dugmad
        jbDodaj = new JButton("Dodaj");
        jbPromeni = new JButton("Promeni");
        jbObrisi = new JButton("Obrisi");
        //horizontalno grupisanje
        JPanel jpPromeniObrisi = new JPanel();
        jpPromeniObrisi.setAlignmentX(Component.LEFT_ALIGNMENT);
        jpPromeniObrisi.setLayout(new BoxLayout(jpPromeniObrisi, BoxLayout.X_AXIS));
        jpPromeniObrisi.add(jbDodaj); jpPromeniObrisi.add(jbPromeni); jpPromeniObrisi.add(jbObrisi);

        //polje za proenu imena klase
        JLabel lbIme = new JLabel("Novo ime:");
        tfIme = new JTextField();
        jbIme = new JButton("Promeni ime");

        //naziv
        JLabel lbNaziv = new JLabel("Naziv:");
        tfNaziv = new JTextField();


        //glavni JPanel:
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        jPanel.add(new JLabel("Prilikom promene elementa dodeliti novo ime i selektovati element iz liste."));
        jPanel.add(lbLista); jPanel.add(new JScrollPane(lista));
        jPanel.add(lbIme); jPanel.add(tfIme); jPanel.add(jbIme);
        jPanel.add(lbNaziv); jPanel.add(tfNaziv);
        jPanel.add(jpPromeniObrisi);

        jPanel.setBorder(new EmptyBorder(new Insets(15, 10, 15, 10)));
        this.add(jPanel);
    }

    public void setEnumMList(List<String> enumMList) {
        this.enumMList = enumMList;

        defaultListModel.clear();
        for(String s : enumMList)
            defaultListModel.addElement(s);
        this.pack();
    }

    public List<String> getEnumMList() {
        return enumMList;
    }

    public JList<String> getLista() {
        return lista;
    }

    public JButton getJbPromeni() {
        return jbPromeni;
    }

    public JButton getJbObrisi() {
        return jbObrisi;
    }

    public JButton getJbIme() {
        return jbIme;
    }

    public JTextField getTfIme() {
        return tfIme;
    }

    public JTextField getTfNaziv() {
        return tfNaziv;
    }

    public JButton getJbDodaj() {
        return jbDodaj;
    }
}
