package pl.edu.agh.kis.project.main.gui;

import pl.edu.agh.kis.project.main.AutomatonBox;
import pl.edu.agh.kis.project.main.Ticker;
import pl.edu.agh.kis.project.main.gui.boards.Board;
import pl.edu.agh.kis.project.main.gui.boards.Board1D;
import pl.edu.agh.kis.project.main.gui.boards.Board2D;
import pl.edu.agh.kis.project.main.gui.components.OptionsPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Kamil on 04.01.2016.
 */
public class MainFrame extends JFrame implements Observer{
    private Board board;
    private OptionsPanel options;
    private AutomatonBox automatonBox;

    public MainFrame(Ticker ticker, AutomatonBox automatonBox){
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.automatonBox = automatonBox;
        board = new Board2D(automatonBox,getWidth()-450,getHeight()-100);
        options = new OptionsPanel((Board2D) board,new Dimension(400,getHeight()-10),automatonBox, ticker);
        this.setLayout(new BorderLayout());
        this.getRootPane().setBorder(new EmptyBorder(10,10,10,10));
        this.add(options.getMainOptionsPanel(),BorderLayout.EAST);
        options.addObserver(board);
        options.addObserver(this);
        automatonBox.addObserver(board);
        this.add(board);
    }


    @Override
    public void update(Observable o, Object arg) {
        if(arg.getClass().equals(String.class)){
            String name = (String) arg;
            automatonBox.getFactory().setAutomatonClassByName(name);
            options.deleteObserver(board);
            automatonBox.deleteObserver(board);
            this.remove(board);
            if(name.equals("Automat jednowymiarowy"))
                board = new Board1D(automatonBox,board.getWidth(),board.getHeight());
            else
                board = new Board2D(automatonBox,board.getWidth(),board.getHeight());
            this.add(board);
            options.addObserver(board);
            automatonBox.addObserver(board);
            automatonBox.setAutomaton(automatonBox.getFactory().createAutomaton());
            if(board.getClass().equals(Board2D.class))
                options.setNewBoardToInserts((Board2D)board);
        }
    }
}
