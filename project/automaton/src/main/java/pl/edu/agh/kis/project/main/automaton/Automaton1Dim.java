package pl.edu.agh.kis.project.main.automaton;

import pl.edu.agh.kis.project.main.CellStateFactory;
import pl.edu.agh.kis.project.main.coords.CellCoordinates;
import pl.edu.agh.kis.project.main.coords.Coords1D;
import pl.edu.agh.kis.project.main.neighbourhood.CellNeighborhood;

import java.util.NoSuchElementException;

/**
 * Created by Kamil on 24.11.2015.
 */
public abstract class Automaton1Dim extends Automaton {
    private int size;

    public Automaton1Dim(CellNeighborhood neighboursStrategy, CellStateFactory stateFactory, int size) {
        super(neighboursStrategy, stateFactory);
        this.size=size;
    }


    @Override
    protected boolean hasNextCoordinates(CellCoordinates coords) {
        if (!(coords instanceof Coords1D))
            throw new IllegalArgumentException("Automaton1Dim supports only Coords1D");
        Coords1D coords1D = (Coords1D) coords;
        return coords1D.x < 0 || coords1D.x < size - 1;
    }

    @Override
    protected CellCoordinates initialCoordinates() {
        return new Coords1D(-1);
    }

    @Override
    protected CellCoordinates nextCoordinates(CellCoordinates coords) {
        if(!hasNextCoordinates(coords))
            throw new NoSuchElementException();
        else {
            Coords1D coords1D = (Coords1D) coords;
            return new Coords1D(coords1D.x + 1);
        }
    }

    public int getSize() {
        return size;
    }
}
