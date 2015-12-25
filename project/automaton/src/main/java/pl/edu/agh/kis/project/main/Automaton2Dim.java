package pl.edu.agh.kis.project.main;

import pl.edu.agh.kis.project.main.neighbourhood.CellNeighborhood;
import pl.edu.agh.kis.project.main.states.CellState;

import java.util.NoSuchElementException;
import java.util.TreeMap;

/**
 * Created by Kamil on 24.11.2015.
 */
public abstract class Automaton2Dim extends Automaton{
    private int width;
    private int height;

    public Automaton2Dim(CellNeighborhood neighboursStrategy, CellStateFactory stateFactory, int width, int height) {
        super(neighboursStrategy, stateFactory);
        this.width = width;
        this.height = height;
        TreeMap<CellCoordinates,CellState> cells = new TreeMap<CellCoordinates, CellState>();
        for(int y=0;y<height;y++)
            for (int x=0;x<width;x++) {
                Coords2D coords2D = new Coords2D(x,y);
                cells.put(coords2D, stateFactory.initialState(coords2D));
            }
        this.insertStructure(cells);
    }

    @Override
    protected boolean hasNextCoordinates(CellCoordinates coords) {
        if(!(coords instanceof Coords2D))
            return false; //TODO Exception for wrong dimensions;
        Coords2D coords2D = (Coords2D) coords;
        if(coords2D.x <0 || coords2D.y <0)
            return true;
        else if(coords2D.x<width-1 && coords2D.y<height-1)
            return true;
        else if (coords2D.x == width - 1)
            return (coords2D.y - (height - 1)) < 0;
        else
            return coords2D.y == height - 1 && (coords2D.x - (height - 1)) < 0;
    }

    @Override
    protected CellCoordinates initialCoordinates() {
        return new Coords2D(-1,-1);
    }

    @Override
    protected CellCoordinates nextCoordinates(CellCoordinates coords) {
        if(!hasNextCoordinates(coords))
            throw new NoSuchElementException();
        else{
            Coords2D coords2D = (Coords2D) coords;
            if(coords2D.x<0 && coords2D.y<0)
                return new Coords2D(0,0);
            else if(coords2D.x == width-1)
                return new Coords2D(0,coords2D.y+1);
            else
                return new Coords2D(coords2D.x+1, coords2D.y);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
