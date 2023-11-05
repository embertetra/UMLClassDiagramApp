package raf.dsw.classycraft.app.gui.swing.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;

public class AboutUsFrame extends JFrame {
    //buduca polja za sve komponente view-a na glavnom prozoru

    public AboutUsFrame(){
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth / 3, screenHeight / 3);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("About us");

        Icon icon = null;
        URL ImageURL = getClass().getResource("/images/slikaaboutus.jpg");

        if(ImageURL != null)
        {
            Image img = new ImageIcon(ImageURL).getImage();
            Image newImg = img.getScaledInstance(250, 300, Image.SCALE_DEFAULT);
            icon = new ImageIcon(newImg);
        }
        else
        {
            System.err.println("File " + "images/slikaaboutus.jpg" + " not found");
        }

        JLabel lblSlika = new JLabel();
        JLabel lblClan1 = new JLabel("Marta Ljiljak");
        JLabel lblClan2 = new JLabel("Dimitrije Mitic");

        lblSlika.setIcon(icon);
        lblSlika.setHorizontalAlignment(0);
        lblClan1.setHorizontalAlignment(0);
        lblClan2.setHorizontalAlignment(0);

        BorderLayout borderLayout = new BorderLayout();
        lblClan1.setBorder(new EmptyBorder(new Insets(10,0,10,0)));
        lblClan2.setBorder(new EmptyBorder(new Insets(10,0,10,0)));
        setLayout(borderLayout);
        add(lblSlika, BorderLayout.CENTER);
        add(lblClan1, BorderLayout.NORTH);
        add(lblClan2, BorderLayout.SOUTH);


    }

}
