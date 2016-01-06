package pl.edu.agh.kis.project.main.gui.components;

import pl.edu.agh.kis.project.main.actions.ShowInsertDialog;
import pl.edu.agh.kis.project.main.gui.boards.Board2D;
import pl.edu.agh.kis.project.main.model.automaton.Automaton2Dim;
import pl.edu.agh.kis.project.main.model.automaton.GameOfLife;
import pl.edu.agh.kis.project.main.model.automaton.StructuresContainer;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Kamil on 05.01.2016.
 */
public class InsertStructureButton implements Observer{
    private final JButton insertButton;

    public InsertStructureButton(Board2D board, StructuresContainer.Structure[] structure) {
        insertButton = new JButton(new ShowInsertDialog(board, structure));
    }

    public void setNewBoard(Board2D board2D) {
        StructuresContainer.Structure[] structures =
                StructuresContainer.get((Class<? extends Automaton2Dim>) board2D.getAutomaton().getClass());
        if (structures == null){
            insertButton.setEnabled(false);
            return;
        }
        insertButton.setAction(new ShowInsertDialog(board2D, structures));
        insertButton.setEnabled(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg.getClass().equals(Boolean.class))
            insertButton.setEnabled((Boolean)arg);
    }

    public JButton getInsertButton(){
        return insertButton;
    }
}
