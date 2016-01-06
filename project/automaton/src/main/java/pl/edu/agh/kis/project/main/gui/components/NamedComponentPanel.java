package pl.edu.agh.kis.project.main.gui.components;

import javax.swing.*;

/**
 * Created by Kamil on 05.01.2016.
 */
public class NamedComponentPanel{
    private final JPanel jPanel;

    public NamedComponentPanel(String name, JComponent component){
        jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel,BoxLayout.PAGE_AXIS));
        jPanel.add(new JLabel(name));
        jPanel.add(component);
    }

    public JPanel getjPanel(){
        return jPanel;
    }
}
