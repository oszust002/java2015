package pl.edu.agh.kis.project.main.gui.boards;

import pl.edu.agh.kis.project.main.AutomatonBox;
import pl.edu.agh.kis.project.main.gui.cells.BinaryStatePaint;
import pl.edu.agh.kis.project.main.model.Cell;
import pl.edu.agh.kis.project.main.actions.NextCellAction;
import pl.edu.agh.kis.project.main.model.automaton.Automaton;
import pl.edu.agh.kis.project.main.model.automaton.Automaton1Dim;
import pl.edu.agh.kis.project.main.model.automaton.AutomatonFactory;
import pl.edu.agh.kis.project.main.model.coords.Coords1D;
import pl.edu.agh.kis.project.main.model.coords.Coords2D;
import pl.edu.agh.kis.project.main.gui.cells.CellPaint;
import pl.edu.agh.kis.project.main.gui.cells.CellPaintFactory;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Observable;

/**
 * Created by Kamil on 02.01.2016.
 */
public class Board1D extends Board{
    private int cellSize;
    private Board1DMouseListener listener;
    private LinkedList<Automaton1Dim> history;
    private int maxSize;

    public Board1D(AutomatonBox automatonBox, int width, int height) {
        super(automatonBox);
        setSize(new Dimension(width, height));
        listener = new Board1DMouseListener();
        addMouseListener(listener);
        history = new LinkedList<Automaton1Dim>();
        restartCellSize();
    }

    @Override
    protected void paintComponent(Graphics g) {
        restartCellSize();
        g.setColor(new Color(238,238,238));
        g.fillRect(0,0,getWidth(),getHeight());
        int y=0;
        synchronized (history) {
            for (Automaton1Dim automaton1Dim : history) {
                for (Cell c : automaton1Dim) {
                    g.setColor(Color.black);
                    Coords2D coords2D = new Coords2D(((Coords1D) c.coords).x * cellSize, y * cellSize);
                    cellPaint.paint(g, coords2D, c.state);
                    g.setColor(Color.black);
                    g.drawRect(coords2D.x, coords2D.y, cellSize, cellSize);
                }
                y++;
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg.getClass().equals(AutomatonFactory.class)) {
            AutomatonFactory factory = (AutomatonFactory) arg;
            getAutomaton().resize(factory.getWidth(), factory.getHeight());
            automatonBox.getFactory().setWidth(factory.getWidth());
            automatonBox.getFactory().setHeight(factory.getHeight());
            synchronized (history) {
                history.clear();
                history.add((Automaton1Dim) automatonBox.getAutomaton());
            }
            repaint();
        }
        else if(arg instanceof Automaton){
            synchronized (history) {
                history.add(0, (Automaton1Dim) arg);
                if (history.size() > maxSize)
                    history.remove(history.size() - 1);
            }
            repaint();
        }
    }

    @Override
    protected void restartCellSize(){
        int oldSize = cellSize;
        cellSize = getWidth()/automatonBox.getFactory().getWidth();
        maxSize = getHeight()/cellSize;
        if(oldSize != cellSize){
            cellPaint = new BinaryStatePaint(new Coords2D(cellSize, cellSize));
        }
    }

    private class Board1DMouseListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX()/cellSize;
            if(e.getY()<=cellSize)
                new NextCellAction(getAutomaton(),new Coords1D(x)).actionPerformed(null);
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
