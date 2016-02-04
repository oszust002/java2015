package pl.edu.agh.java2015.ftp.server.gui;

import javax.swing.*;

/**
 * Created by Kamil on 03.02.2016.
 */

public class NamedComponentPanel{
    private final JPanel jPanel;

    public JComponent getComponent() {
        return component;
    }

    private JComponent component;

    public NamedComponentPanel(String name, JComponent component){
        jPanel = new JPanel();
        this.component = component;
        jPanel.setLayout(new BoxLayout(jPanel,BoxLayout.LINE_AXIS));
        jPanel.add(new JLabel(name));
        jPanel.add(component);
    }

    public JPanel getjPanel(){
        return jPanel;
    }

}
