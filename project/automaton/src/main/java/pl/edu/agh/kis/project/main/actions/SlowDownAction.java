package pl.edu.agh.kis.project.main.actions;

import pl.edu.agh.kis.project.main.Ticker;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Kamil on 05.01.2016.
 */
public class SlowDownAction extends AbstractAction {
    private final Ticker ticker;

    public SlowDownAction(Ticker ticker) {
        super("Zwolnij");
        this.ticker = ticker;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int newSleepTime = ticker.getSleepTime()+100;
        if(newSleepTime>1000)
            newSleepTime = 1000;
        ticker.setSleepTime(newSleepTime);
    }
}
