package pl.edu.agh.kis.project.main.model.coords;

/**
 * Created by Kamil on 23.11.2015.
 */
public class Coords2D implements CellCoordinates {
    public final int x;
    public final int y;

    public Coords2D(int x, int y) {
        this.x = x;
        this.y = y;
    }



    @Override
    public int compareTo(CellCoordinates o) {
        Coords2D second = (Coords2D) o;
        if(y>second.y)
            return 1;
        else if(y < second.y)
            return -1;
        else
            return x-second.x;
    }

    @Override
    public String toString() {
        return "["+x+", " + y +"]";
    }

    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof Coords2D))
            return false;
        Coords2D second = (Coords2D) obj;
        return compareTo(second) == 0;
    }

    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 18;
    }
}
