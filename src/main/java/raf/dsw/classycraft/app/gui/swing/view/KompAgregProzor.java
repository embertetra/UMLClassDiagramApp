package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.classyCraftRepository.composite.dijagramElementi.Connection;
import raf.dsw.classycraft.app.controller.KompAregPotvrdi;
import raf.dsw.classycraft.app.gui.swing.view.painters.ConnectionPainter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class KompAgregProzor extends JFrame {

    private JPanel jPanel;
    private JLabel lblKardinalnost;
    private JLabel lblVidljivost;
    private JLabel lblTip;
    private JLabel lblname;
    private JTextField tfName;
    private JTextField tfTip;
    private JComboBox<String> cbKardinalnost;
    private JComboBox<String> cbVidljivost;
    private JButton jbOk;
    private ConnectionPainter connectionPainter;

    public KompAgregProzor() throws HeadlessException {

        //namestanje prozora
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth/3, screenHeight/3);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        jPanel = new JPanel();


        tfName = new JTextField();
        tfTip = new JTextField();
        lblname = new JLabel("Name");
        lblKardinalnost = new JLabel("Kardinalnost:");
        lblVidljivost = new JLabel("Vidljivost:");
        lblTip = new JLabel("Tip promenljive:");
        cbKardinalnost = new JComboBox<>();
        cbKardinalnost.addItem("-");
        cbKardinalnost.addItem("0");
        cbKardinalnost.addItem("0..1");
        cbKardinalnost.addItem("0..*");
        cbKardinalnost.addItem("1");
        cbKardinalnost.addItem("1..*");
        cbKardinalnost.addItem("*");
        cbVidljivost = new JComboBox<>();
        cbVidljivost.addItem("-");
        cbVidljivost.addItem("private");
        cbVidljivost.addItem("public");
        cbVidljivost.addItem("protected");
        cbVidljivost.addItem("package");
        jbOk = new JButton("Potvrdi");
        jbOk.setAction(new KompAregPotvrdi(this, connectionPainter));

        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.add(lblname);
        jPanel.add(tfName);
        jPanel.add(lblTip);
        jPanel.add(tfTip);
        jPanel.add(lblVidljivost);
        jPanel.add(cbVidljivost);
        jPanel.add(lblKardinalnost);
        jPanel.add(cbKardinalnost);
        jPanel.add(jbOk);
        jPanel.setBorder(new EmptyBorder(new Insets(25, 20, 25, 20)));
        add(jPanel);
        pack();
    }

    public JPanel getjPanel() {
        return jPanel;
    }

    public void setjPanel(JPanel jPanel) {
        this.jPanel = jPanel;
    }

    public JLabel getLblKardinalnost() {
        return lblKardinalnost;
    }

    public void setLblKardinalnost(JLabel lblKardinalnost) {
        this.lblKardinalnost = lblKardinalnost;
    }

    public JLabel getLblVidljivost() {
        return lblVidljivost;
    }

    public void setLblVidljivost(JLabel lblVidljivost) {
        this.lblVidljivost = lblVidljivost;
    }

    public JLabel getLblTip() {
        return lblTip;
    }

    public void setLblTip(JLabel lblTip) {
        this.lblTip = lblTip;
    }

    public JLabel getLblname() {
        return lblname;
    }

    public void setLblname(JLabel lblname) {
        this.lblname = lblname;
    }

    public JTextField getTfName() {
        return tfName;
    }

    public void setTfName(JTextField tfName) {
        this.tfName = tfName;
    }

    public JTextField getTfTip() {
        return tfTip;
    }

    public void setTfTip(JTextField tfTip) {
        this.tfTip = tfTip;
    }

    public JComboBox<String> getCbKardinalnost() {
        return cbKardinalnost;
    }

    public void setCbKardinalnost(JComboBox<String> cbKardinalnost) {
        this.cbKardinalnost = cbKardinalnost;
    }

    public JComboBox<String> getCbVidljivost() {
        return cbVidljivost;
    }

    public void setCbVidljivost(JComboBox<String> cbVidljivost) {
        this.cbVidljivost = cbVidljivost;
    }

    public JButton getJbOk() {
        return jbOk;
    }

    public void setJbOk(JButton jbOk) {
        this.jbOk = jbOk;
    }

    public ConnectionPainter getConnectionPainter() {
        return connectionPainter;
    }

    public void setConnectionPainter(ConnectionPainter connectionPainter) {
        this.connectionPainter = connectionPainter;
        jbOk.setAction(new KompAregPotvrdi(this, connectionPainter));
    }
}
