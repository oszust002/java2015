package pl.edu.agh.kis.project.main.actions;

import pl.edu.agh.kis.project.main.gui.boards.Board2D;
import pl.edu.agh.kis.project.main.model.automaton.StructuresContainer;

import javax.swing.*;

/**
 * Created by Kamil on 05.01.2016.
 */
public class InsertDialog {
    public InsertDialog(Board2D board2D, StructuresContainer.Structure[] structures){
        StructuresContainer.Structure selected = (StructuresContainer.Structure) (JOptionPane.showInputDialog(
                board2D, "Struktury do dodania", "Dodanie struktury", JOptionPane.PLAIN_MESSAGE,null,structures,
                structures[0]));
        if (selected != null){
            new EnterInsertMode(board2D, selected.getStructure()).actionPerformed(null);
        }
    }
}
