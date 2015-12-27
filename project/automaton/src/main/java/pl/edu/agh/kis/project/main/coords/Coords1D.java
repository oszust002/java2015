package pl.edu.agh.kis.project.main.coords;

/**
 * Created by Kamil on 23.11.2015.
 */
public class Coords1D implements CellCoordinates {
    public final int x;

    public Coords1D(int x) {
        this.x = x;
    }

    @Override
    public int compareTo(CellCoordinates o) {
        Coords2D second = (Coords2D) o;
        return x-second.x;
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Coords1D))
            return false;
        Coords1D second = (Coords1D) obj;
        return compareTo(second) == 0;
    }
}
