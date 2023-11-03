package raf.dsw.classycraft.app.gui.swing.view;

import raf.dsw.classycraft.app.controller.PotvrdiAction;

import javax.swing.*;
import java.awt.*;

public class PackageOrDiagram extends JFrame {

    public PackageOrDiagram(){

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth / 3, screenHeight / 3);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Package or Diagram");

        JPanel panBottom = new JPanel();
        BoxLayout box = new BoxLayout(panBottom, BoxLayout.Y_AXIS);
        panBottom.setLayout(box);

        JRadioButton diagram = new JRadioButton("Diagram");
        JRadioButton podpackage = new JRadioButton("Package");
        JButton potvrdi = new JButton("Potvrdi");
        //potvrdi.setPreferredSize(new Dimension(72,25));
        ButtonGroup buttonGroup = new ButtonGroup();


        buttonGroup.add(diagram);
        buttonGroup.add(podpackage);

        panBottom.add(diagram);
        panBottom.add(podpackage);
        panBottom.add(potvrdi);

        this.add(panBottom);

        potvrdi.setAction(new PotvrdiAction());

    }
}
