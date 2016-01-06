package pl.edu.agh.kis.project.main.gui;

import pl.edu.agh.kis.project.main.AutomatonBox;
import pl.edu.agh.kis.project.main.Ticker;
import pl.edu.agh.kis.project.main.model.automaton.AutomatonFactory;

import javax.swing.*;

/**
 * Created by Kamil on 01.01.2016.
 */
public class Main{

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Ticker ticker = new Ticker();
                AutomatonBox initialAutomatonBox = new AutomatonBox(new AutomatonFactory().createAutomaton());
                MainFrame frame = new MainFrame(ticker, initialAutomatonBox);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }


}
