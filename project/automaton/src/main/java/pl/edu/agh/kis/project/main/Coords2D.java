package pl.edu.agh.kis.project.main;

/**
 * Created by Kamil on 23.11.2015.
 */
public class Coords2D implements CellCoordinates{
    public final int x;
    public final int y;

    public Coords2D(int x, int y) {
        this.x = x;
        this.y = y;
    }



    @Override
    public int compareTo(CellCoordinates o) {
        Coords2D second = (Coords2D) o;
        if(second.x==x && second.y==y)
            return 0;
        if(second.y<y)
            return 1;
        if(second.y == y && second.x<x)
            return 1;
        return -1;
    }

    @Override
    public String toString() {
        return "["+x+", " + y +"]";
    }
}
