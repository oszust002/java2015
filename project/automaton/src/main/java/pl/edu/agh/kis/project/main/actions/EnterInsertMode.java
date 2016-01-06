package pl.edu.agh.kis.project.main.actions;

import pl.edu.agh.kis.project.main.exceptions.InvalidAutomatonException;
import pl.edu.agh.kis.project.main.gui.boards.Board2D;
import pl.edu.agh.kis.project.main.model.states.CellState;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Kamil on 05.01.2016.
 */
public class EnterInsertMode extends AbstractAction{
    private final Board2D board2D;
    private final CellState[][] structureArrray;

    public EnterInsertMode(Board2D board2D, CellState[][] structureArrray){
        this.board2D = board2D;
        this.structureArrray = structureArrray;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            board2D.insertMode(structureArrray);
        } catch (InvalidAutomatonException e1) {
            e1.printStackTrace();
        }
    }
}
