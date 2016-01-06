package pl.edu.agh.kis.project.main.gui.components;

import pl.edu.agh.kis.project.main.AutomatonBox;
import pl.edu.agh.kis.project.main.actions.ExecuteAction;
import pl.edu.agh.kis.project.main.actions.SlowDownAction;
import pl.edu.agh.kis.project.main.actions.SpeedUpAction;
import pl.edu.agh.kis.project.main.actions.TickAction;
import pl.edu.agh.kis.project.main.Ticker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Observable;

/**
 * Created by Kamil on 03.01.2016.
 */
public class Buttons extends Observable{
    private JPanel buttons;
    private JToggleButton start;


    public Buttons(AutomatonBox automatonBox, Ticker ticker){
        buttons = new JPanel();
        start = new JToggleButton(new ExecuteAction(ticker,automatonBox));
        buttons.add(start);
        buttons.add(new JButton(new TickAction(automatonBox)));
        buttons.add(new JButton(new SpeedUpAction(ticker)));
        buttons.add(new JButton(new SlowDownAction(ticker)));
        buttons.setLayout(new GridLayout(2,2,10,10));
        buttons.setBorder(BorderFactory.createLineBorder(Color.black));
        start.addItemListener(new ToggleButtonChangeListener());
    }

    public JPanel getJPanel(){
        return buttons;
    }

    private class ToggleButtonChangeListener implements ItemListener{

        @Override
        public void itemStateChanged(ItemEvent e) {
            setChanged();
            if(e.getStateChange() == ItemEvent.SELECTED)
                notifyObservers(Boolean.FALSE);
            else if(e.getStateChange() == ItemEvent.DESELECTED)
                notifyObservers(Boolean.TRUE);
        }
    }
}
