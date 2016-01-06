package pl.edu.agh.kis.project.main.model.neighbourhood;

import pl.edu.agh.kis.project.main.model.coords.CellCoordinates;
import pl.edu.agh.kis.project.main.model.coords.Coords2D;

import java.util.Set;
import java.util.TreeSet;

/**
 * Implementation of {@link CellNeighborhood} in which neighbours coordinates are calculated with specified radius and
 * wrapping in terms of Moore Neighbourhood
 * @author Kamil Osuch
 * @version 1.0
 */
public class MoorNeighbourhood implements Cell2DimNeighbourhood{
    private int width, height, r;
    private boolean wrap;

    /**
     * Creates instance which calculates neighbours coordinates with specified radius, width, height and wrap
     * @param r radius on which neighbours coordinates will be calculated
     * @param width width of the board
     * @param height height of the board
     * @param wrap True if should wrap board, False otherwise
     */
    public MoorNeighbourhood(int r, int width, int height, boolean wrap){
        this.r=r;
        this.wrap = wrap;
        this.width = width;
        this.height = height;
    }

    /**
     * Creates instance which calculates neighbours coordinates with specified width, height and wrap, where radius is 1
     * @param width width of the board
     * @param height height of the board
     * @param wrap True if should wrap board, False otherwise
     */
    public MoorNeighbourhood(int width, int height, boolean wrap){
        this(1, width, height, wrap);
    }

    /**
     *Implementation that supports only {@link Coords2D}
     * @see CellNeighborhood#cellNeighbors(CellCoordinates)
     */
    @Override
    public Set<CellCoordinates> cellNeighbors(CellCoordinates cell){
        if(!(cell instanceof Coords2D))
            throw new IllegalArgumentException("MooreNeighbourhood supports only Coords2D");
        Coords2D cell2D = (Coords2D) cell;
        Set<CellCoordinates> neighbours = new TreeSet<CellCoordinates>();
        for(int x=cell2D.x-r;x<=cell2D.x+r;x++)
            for (int y=cell2D.y-r;y<=cell2D.y+r;y++)
                if(!(x==cell2D.x && y==cell2D.y)) {
                    if (isOutOfGrid(x, y) && wrap) {
                        int x_changed = Math.floorMod(x, width);
                        int y_changed = Math.floorMod(y, height);
                        neighbours.add(new Coords2D(x_changed, y_changed));
                    }
                    else if(!isOutOfGrid(x,y)){
                        neighbours.add(new Coords2D(x,y));
                    }
                }
        return neighbours;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public boolean getWrap() {
        return wrap;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public boolean isOutOfGrid(int x, int y){
        return (x > width-1 || y > height-1) || (x < 0 || y < 0);
    }

    /**
     * Checks equality of neighbourhoods
     * @param obj second neighbourhood
     * @return True if equal, False otherwise
     */
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof MoorNeighbourhood))
            return false;
        MoorNeighbourhood second = (MoorNeighbourhood) obj;
        return r == second.r && wrap == second.wrap && width == second.width && height == second.height;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void setNewSize(CellCoordinates coordinates) {
        Coords2D newSize = (Coords2D) coordinates;
        width = newSize.x;
        height = newSize.y;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void setWrap(boolean wrap) {
        this.wrap = wrap;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void setRadius(int r) {
        this.r = r;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public int getRadius() {
        return r;
    }

    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 83;
    }
}
