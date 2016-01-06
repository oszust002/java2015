package pl.edu.agh.kis.project.main.actions;

import pl.edu.agh.kis.project.main.AutomatonBox;
import pl.edu.agh.kis.project.main.Ticker;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Kamil on 03.01.2016.
 */
public class ExecuteAction extends AbstractAction {
    private Ticker ticker;
    private AutomatonBox automatonBox;

    public ExecuteAction(Ticker ticker, AutomatonBox automatonBox){
        super("Start");
        this.ticker = ticker;
        this.automatonBox = automatonBox;
        ticker.addObserver(automatonBox);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        ticker.start(automatonBox);
    }
}
