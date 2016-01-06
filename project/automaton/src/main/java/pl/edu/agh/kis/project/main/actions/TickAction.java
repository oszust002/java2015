package pl.edu.agh.kis.project.main.actions;

import pl.edu.agh.kis.project.main.AutomatonBox;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Kamil on 03.01.2016.
 */
public class TickAction extends AbstractAction {
    private AutomatonBox automatonBox;

    public TickAction(AutomatonBox automatonBox){
        super("Tick");
        this.automatonBox = automatonBox;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        automatonBox.setAutomaton(automatonBox.getAutomaton().nextState());
    }
}
