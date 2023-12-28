package raf.dsw.classycraft.app.gui.swing.view;


import raf.dsw.classycraft.app.controller.SingleTemplateAction;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GalleryTemplates extends JFrame {

    private File folder;
    private File[] listFiles;
    private JPanel mainPanel;
    private JLabel title;
    private JPanel templates;
    private JPanel blankTemplate;
    private JButton btnBlank;
    private Image imgBlank;
    private JLabel lblBlank;

    public GalleryTemplates() throws HeadlessException {

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth / 3, screenHeight / 3);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Gallery");

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        title = new JLabel("Izaberite sablon");
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(new Font("Serif", Font.PLAIN, 35));
        mainPanel.add(title);
        mainPanel.add(new JLabel("  "));
        mainPanel.add(new JLabel("  "));

        templates = new JPanel();
        templates.setLayout(new FlowLayout(FlowLayout.CENTER));

        blankTemplate = new JPanel();
        blankTemplate.setLayout(new BoxLayout(blankTemplate, BoxLayout.Y_AXIS));
        btnBlank = new JButton("Prazan dijagram");
        BufferedImage raw = null;
        try {
            raw = ImageIO.read(new File("src/main/resources/images/newDiagram.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgBlank = raw.getScaledInstance(100,100, Image.SCALE_DEFAULT);
        lblBlank = new JLabel(new ImageIcon(imgBlank));
        lblBlank.setAlignmentX(CENTER_ALIGNMENT);
        btnBlank.setAlignmentX(CENTER_ALIGNMENT);
        blankTemplate.add(lblBlank);
        blankTemplate.add(btnBlank);
        templates.add(blankTemplate);

        folder = new File("src/main/resources/templates");
        listFiles = folder.listFiles();

        ///dodavanje fajlova
        for (File f : listFiles) {

            JPanel panel = new JPanel();
            JButton dugme = new JButton(f.getName());
            BufferedImage rawPicture = null;
            Image scaledPicture = null;
            try {
                rawPicture = ImageIO.read(new File("src/main/resources/images/existingDiagram.png"));
                scaledPicture = rawPicture.getScaledInstance(100, 100, Image.SCALE_DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
            JLabel picLabel = new JLabel(new ImageIcon(scaledPicture));
            picLabel.setBorder(new EmptyBorder(new Insets(0,0,6,0)));
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            picLabel.setAlignmentX(CENTER_ALIGNMENT);
            dugme.setAlignmentX(CENTER_ALIGNMENT);

            panel.add(picLabel);
            panel.add(dugme);
            dugme.setAction(new SingleTemplateAction(f.getName()));

            templates.add(panel);
        }
        mainPanel.add(templates);
        add(mainPanel);
        pack();
    }
}
