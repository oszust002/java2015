package pl.edu.agh.kis.project.main.neighbourhood;

import pl.edu.agh.kis.project.main.coords.CellCoordinates;
import pl.edu.agh.kis.project.main.coords.Coords1D;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Kamil on 29.12.2015.
 */
public class Cell1DimNeighbourhood implements CellNeighborhood{
    public final int width;
    public final boolean wrap;

    public Cell1DimNeighbourhood(int width, boolean wrap){
        this.width = width;
        this.wrap = wrap;
    }
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
}
