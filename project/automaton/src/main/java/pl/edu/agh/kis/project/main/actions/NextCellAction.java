package pl.edu.agh.kis.project.main.actions;

import pl.edu.agh.kis.project.main.model.automaton.Automaton;
import pl.edu.agh.kis.project.main.model.coords.CellCoordinates;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Kamil on 03.01.2016.
 */
public class NextCellAction extends AbstractAction{
    private Automaton automaton;
    private CellCoordinates coords;


    public NextCellAction(Automaton automaton, CellCoordinates coordinates){
        this.automaton = automaton;
        this.coords = coordinates;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        automaton.advanceCellState(coords);
    }
}
