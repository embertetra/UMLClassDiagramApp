package raf.dsw.classycraft.app.gui.swing.view;


import raf.dsw.classycraft.app.classyCraftRepository.implementation.Dijagram;
import raf.dsw.classycraft.app.controller.SingleTemplateAction;
import raf.dsw.classycraft.app.gui.swing.tree.ClassyTree;
import raf.dsw.classycraft.app.gui.swing.tree.model.ClassyTreeItem;

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
    private Dijagram prosledjen;

    public GalleryTemplates() throws HeadlessException {

        prosledjen = null;

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth / 3, screenHeight / 3);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setTitle("Gallery");

        mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(new Insets(15,15,15,15)));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        title = new JLabel("Izaberite sablon");
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(new Font("Serif", Font.PLAIN, 30));
        mainPanel.add(title);
        mainPanel.add(new JLabel("  "));
        mainPanel.add(new JLabel("  "));

        folder = new File("src/main/resources/templates");
        listFiles = folder.listFiles();

        templates = new JPanel();
        //templates.setLayout(new BoxLayout(templates, BoxLayout.Y_AXIS));
        templates.setLayout(new GridLayout(listFiles.length + 1, 1));

        btnBlank = new JButton("Prazan dijagram");
        btnBlank.setAction(new SingleTemplateAction("Prazan dijagram"));
        btnBlank.setHorizontalAlignment(SwingConstants.LEFT);

        int index = 0;
        ///dodavanje fajlova
        if(listFiles != null) {
            for (File f : listFiles) {

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
                JButton dugme = new JButton(f.getName());
                dugme.setAction(new SingleTemplateAction(f.getName()));
                panel.add(dugme);
                dugme.setHorizontalAlignment(SwingConstants.LEFT);
                templates.add(dugme, index, 0);
                index++;

            }
            templates.add(btnBlank, index,0);
            templates.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainPanel.add(templates);
            add(mainPanel);
            pack();
        }
    }

    public void refreshFoldredTemplate(){
        folder = new File("src/main/resources/templates");
        listFiles = folder.listFiles();
        templates.removeAll();
        int index = 0;
        ///dodavanje fajlova
        if(listFiles != null) {
            for (File f : listFiles) {

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
                JButton dugme = new JButton(f.getName());
                dugme.setAction(new SingleTemplateAction(f.getName()));
                panel.add(dugme);
                dugme.setHorizontalAlignment(SwingConstants.LEFT);
                templates.add(dugme, index, 0);
                index++;

            }
            templates.add(btnBlank, index,0);
            templates.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainPanel.add(templates);
            add(mainPanel);
            pack();
        }
    }

    public File[] getListFiles() {
        return listFiles;
    }

    public Dijagram getProsledjen() {
        return prosledjen;
    }

    public void setProsledjen(Dijagram prosledjen) {
        this.prosledjen = prosledjen;
    }
}
