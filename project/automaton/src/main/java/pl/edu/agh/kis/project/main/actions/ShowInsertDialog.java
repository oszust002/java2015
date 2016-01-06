package pl.edu.agh.kis.project.main.actions;

import pl.edu.agh.kis.project.main.gui.boards.Board2D;
import pl.edu.agh.kis.project.main.model.automaton.StructuresContainer;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Kamil on 05.01.2016.
 */
public class ShowInsertDialog extends AbstractAction {
    private Board2D board2D;
    private StructuresContainer.Structure[] structures;

    public ShowInsertDialog(Board2D board2D, StructuresContainer.Structure[] structures){
        super("Dodaj strukture");
        this.board2D = board2D;
        this.structures = structures;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        new InsertDialog(board2D,structures);
    }
}
