package pl.edu.agh.kis.project.main;

/**
 * Created by Kamil on 23.11.2015.
 */
public class Coords1D implements CellCoordinates{
    public final int x;

    public Coords1D(int x) {
        this.x = x;
    }

    @Override
    public int compareTo(CellCoordinates o) {
        Coords2D second = (Coords2D) o;
        return x-second.x;
    }
}
