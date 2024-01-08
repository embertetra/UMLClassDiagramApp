package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.controller.DependencyPotvrdi;
import raf.dsw.classycraft.app.gui.swing.view.painters.connectionPainter.ZavisnostPainter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DependencyProzor extends JFrame {

    private JLabel jblName;
    private JComboBox<String> comboBox;
    private JButton jbOk;
    private JPanel jPanel;
    private ZavisnostPainter painter;

    public DependencyProzor() {

        //namestanje prozora
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth/3, screenHeight/3);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        jblName = new JLabel("call/instantiate");
        comboBox = new JComboBox<>();
        comboBox.addItem("-");
        comboBox.addItem("call");
        comboBox.addItem("instantiate");
        jbOk = new JButton("Potvrdi");
        jbOk.setAction(new DependencyPotvrdi(this, painter));

        jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.add(jblName);
        jPanel.add(comboBox);
        jPanel.add(jbOk);
        jPanel.setBorder(new EmptyBorder(new Insets(25, 20, 25, 20)));
        add(jPanel);
        pack();
    }

    public JComboBox<String> getComboBox() {
        return comboBox;
    }
    public void setPainter(ZavisnostPainter painter) {
        this.painter = painter;
        jbOk.setAction(new DependencyPotvrdi(this, painter));
    }
}
