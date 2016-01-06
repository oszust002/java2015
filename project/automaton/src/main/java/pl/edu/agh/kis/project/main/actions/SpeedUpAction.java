package pl.edu.agh.kis.project.main.actions;

import pl.edu.agh.kis.project.main.Ticker;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Kamil on 05.01.2016.
 */
public class SpeedUpAction extends AbstractAction {
    private final Ticker ticker;

    public SpeedUpAction(Ticker ticker) {
        super("Przyspiesz");
        this.ticker = ticker;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int newSleepTime = ticker.getSleepTime() - 100;
        if(newSleepTime<=10)
            newSleepTime = 10;
        ticker.setSleepTime(newSleepTime);
    }
}
