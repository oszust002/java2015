package pl.edu.agh.kis.project.main.gui.boards;

import pl.edu.agh.kis.project.main.AutomatonBox;
import pl.edu.agh.kis.project.main.gui.cells.CellPaint;
import pl.edu.agh.kis.project.main.model.automaton.Automaton;

import javax.swing.*;
import java.awt.*;
import java.util.Observer;

/**
 * Created by Kamil on 02.01.2016.
 */
public abstract class Board extends JPanel implements Observer{
    protected AutomatonBox automatonBox;
    protected CellPaint cellPaint;


    public Board(AutomatonBox automatonBox) {
        this.automatonBox = automatonBox;
    }

    protected abstract void paintComponent(Graphics g);

    public Automaton getAutomaton(){
        return automatonBox.getAutomaton();
    }

    public void setAutomaton(Automaton automaton){
        automatonBox.setAutomaton(automaton);
    }

    protected abstract void restartCellSize();


}
