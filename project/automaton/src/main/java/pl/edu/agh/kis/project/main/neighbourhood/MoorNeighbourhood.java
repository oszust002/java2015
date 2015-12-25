package pl.edu.agh.kis.project.main.neighbourhood;

import pl.edu.agh.kis.project.main.CellCoordinates;
import pl.edu.agh.kis.project.main.Coords2D;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Kamil on 07.12.2015.
 */
public class MoorNeighbourhood implements CellNeighborhood{
    public final int r, width, height;
    public final boolean wrap;

    public MoorNeighbourhood(int r, int width, int height, boolean wrap){
        this.r=r;
        this.wrap = wrap;
        this.width = width;
        this.height = height;
    }

    public MoorNeighbourhood(int width, int height, boolean wrap){
        this(1, width, height, wrap);
    }

    @Override
    public Set<CellCoordinates> cellNeighbors(CellCoordinates cell) {
        if(!(cell instanceof Coords2D))
            return null; //TODO
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

    public boolean isOutOfGrid(int x, int y){
        return (x > width || y > height) || (x < 0 || y < 0);
    }
}
