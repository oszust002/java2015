package pl.edu.agh.kis.project.main.gui.boards;

import pl.edu.agh.kis.project.main.AutomatonBox;
import pl.edu.agh.kis.project.main.exceptions.InvalidAutomatonException;
import pl.edu.agh.kis.project.main.model.Cell;
import pl.edu.agh.kis.project.main.actions.NextCellAction;
import pl.edu.agh.kis.project.main.model.automaton.Automaton;
import pl.edu.agh.kis.project.main.model.automaton.Automaton2Dim;
import pl.edu.agh.kis.project.main.model.automaton.AutomatonFactory;
import pl.edu.agh.kis.project.main.model.coords.Coords2D;
import pl.edu.agh.kis.project.main.gui.cells.CellPaint;
import pl.edu.agh.kis.project.main.gui.cells.CellPaintFactory;
import pl.edu.agh.kis.project.main.model.states.CellState;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.Observable;

/**
 * Created by Kamil on 02.01.2016.
 */
public class Board2D extends Board{
    private Board2DMouseListener listener;
    private InsertModeMouseListener insertModeMouseListener;
    private MotionListener mouseMotionListener;
    private int cellSizeX;
    private int cellSizeY;

    public Board2D(AutomatonBox automatonBox, int width, int height) {
        super(automatonBox);
        setSize(new Dimension(width, height));
        listener = new Board2DMouseListener();
        addMouseListener(listener);
    }

    @Override
    protected void paintComponent(Graphics g) {
        restartCellSize();
        g.setColor(new Color(238,238,238));
        g.fillRect(0,0,getWidth(),getHeight());
        for(Cell c: getAutomaton()){
            g.setColor(Color.black);
            Coords2D coords2D = (Coords2D) c.coords;
            coords2D = new Coords2D(coords2D.x*cellSizeX,coords2D.y*cellSizeY);
            cellPaint.paint(g,coords2D,c.state);
            g.setColor(Color.black);
            g.drawRect(coords2D.x,coords2D.y, cellSizeX,cellSizeY);
        }
    }

    @Override
    protected void restartCellSize(){
        int oldXSize = cellSizeX;
        int oldYSize = cellSizeY;
        Automaton2Dim automaton2Dim = (Automaton2Dim) automatonBox.getAutomaton();
        cellSizeX = getWidth()/automaton2Dim.getWidth();
        cellSizeY = getHeight()/automaton2Dim.getHeight();
        if(oldYSize != cellSizeY || oldXSize != cellSizeX)
            try {
                cellPaint = CellPaintFactory.getCellPaint(automaton2Dim.getCellStateClass(),
                        new Coords2D(cellSizeX,cellSizeY));
            } catch (IOException e) {
                throw new AssertionError("Ant image crashed a program");
            }
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg.getClass().equals(AutomatonFactory.class)) {
            AutomatonFactory factory = (AutomatonFactory) arg;
            getAutomaton().resize(factory.getWidth(), factory.getHeight());
            automatonBox.getFactory().setWidth(factory.getWidth());
            automatonBox.getFactory().setHeight(factory.getHeight());
            repaint();
        }
        else if(arg instanceof Automaton){
            repaint();
        }
    }

    public void insertMode(CellState[][] structure) throws InvalidAutomatonException {
        try {
            final Automaton2Dim copy = (Automaton2Dim) automatonBox.getAutomaton().clone();
            removeMouseListener(listener);
            insertModeMouseListener = new InsertModeMouseListener(this);
            addMouseListener(insertModeMouseListener);
            mouseMotionListener = new MotionListener(copy, structure);
            addMouseMotionListener(mouseMotionListener);
        }catch (ClassCastException e){
            throw new InvalidAutomatonException();
        }
    }

    public void exitInsertMode(){
        removeMouseListener(insertModeMouseListener);
        removeMouseMotionListener(mouseMotionListener);
        addMouseListener(listener);
    }

    private class Board2DMouseListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent event) {

        }

        @Override
        public void mousePressed(MouseEvent event) {
            int x = event.getX()/cellSizeX;
            int y = event.getY()/cellSizeY;
            new NextCellAction(getAutomaton(),new Coords2D(x,y)).actionPerformed(null);
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent event) {

        }

        @Override
        public void mouseEntered(MouseEvent event) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private static class InsertModeMouseListener implements MouseListener{
        Board2D board2D;

        public InsertModeMouseListener(Board2D parent){
            board2D = parent;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            board2D.exitInsertMode();
        }

        @Override
        public void mousePressed(MouseEvent e) {

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

    private class MotionListener implements MouseMotionListener{
        private final Automaton original;
        private final CellState[][] structureToInsert;

        public MotionListener(Automaton original, CellState[][] structureToInsert){
            this.original = original;
            this.structureToInsert = structureToInsert;
        }

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            int cellX = e.getX()/cellSizeX;
            int cellY = e.getY()/cellSizeY;

            automatonBox.setAutomaton((Automaton) original.clone());
            try {
                automatonBox.putStructure(structureToInsert,new Coords2D(cellX,cellY));
            } catch (InvalidAutomatonException e1) {
                e1.printStackTrace();
            }
        }
    }
}
