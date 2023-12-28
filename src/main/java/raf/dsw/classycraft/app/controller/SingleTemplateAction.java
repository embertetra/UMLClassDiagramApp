package raf.dsw.classycraft.app.controller;

import java.awt.event.ActionEvent;

public class SingleTemplateAction extends AbstractClassyAction{

    private String name;

    public SingleTemplateAction(String name) {
        this.name = name;
        //putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        putValue(SMALL_ICON, loadIcon("/images/existingDiagram.png"));
        putValue(NAME, name);
        putValue(SHORT_DESCRIPTION, name);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        System.out.println(name);

    }
}
