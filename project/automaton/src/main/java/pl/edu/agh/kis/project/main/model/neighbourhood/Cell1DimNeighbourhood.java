package pl.edu.agh.kis.project.main.model.neighbourhood;

import pl.edu.agh.kis.project.main.model.coords.CellCoordinates;
import pl.edu.agh.kis.project.main.model.coords.Coords1D;

import java.util.Set;
import java.util.TreeSet;

/**
 * Implementation of {@link CellNeighborhood} for {@link pl.edu.agh.kis.project.main.model.automaton.Automaton1Dim}
 * @author Kamil Osuch
 * @version 1.0
 */
public class Cell1DimNeighbourhood implements CellNeighborhood{
    private int width;
    private boolean wrap;

    /**
     * Creates instance which calculate neighbours with specified wrapping and width of the board
     * @param width width of the board
     * @param wrap True if should wrap board, False otherwise
     */
    public Cell1DimNeighbourhood(int width, boolean wrap){
        this.width = width;
        this.wrap = wrap;
    }

    /**
     *Implementation that supports only {@link Coords1D}
     * @see CellNeighborhood#cellNeighbors(CellCoordinates)
     */
    @Override
    public Set<CellCoordinates> cellNeighbors(CellCoordinates cell) {
        if (!(cell instanceof Coords1D))
            throw new IllegalArgumentException("Cell1DimNeighbourhood supports only Coords1D");
        Coords1D coords1D = (Coords1D) cell;
        Set<CellCoordinates> neighbours = new TreeSet<CellCoordinates>();
        if(wrap) {
            neighbours.add(new Coords1D(Math.floorMod(coords1D.x - 1, width)));
            neighbours.add(new Coords1D(Math.floorMod(coords1D.x + 1, width)));
        }
        else{
            if(!isOutOfGrid(coords1D.x-1))
                neighbours.add(new Coords1D(coords1D.x-1));
            if (!isOutOfGrid(coords1D.x+1)){
                neighbours.add(new Coords1D(coords1D.x+1));
            }
        }
        return neighbours;
    }

    @Override
    public boolean getWrap() {
        return wrap;
    }

    @Override
    public void setNewSize(CellCoordinates coordinates) {
        Coords1D coords1D = (Coords1D) coordinates;
        width = coords1D.x;
    }

    @Override
    public void setWrap(boolean wrap) {
        this.wrap = wrap;
    }

    public boolean isOutOfGrid(int x){
        return x > width-1 || x<0;
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Cell1DimNeighbourhood))
            return false;
        Cell1DimNeighbourhood second = (Cell1DimNeighbourhood) obj;
        return wrap==second.wrap && width==second.width;
    }

    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 88;
    }
}
