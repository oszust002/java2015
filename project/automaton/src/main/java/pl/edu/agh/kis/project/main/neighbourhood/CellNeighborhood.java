package pl.edu.agh.kis.project.main.neighbourhood;

import pl.edu.agh.kis.project.main.coords.CellCoordinates;

import java.util.Set;

/**
 * Created by Kamil on 23.11.2015.
 */
public interface CellNeighborhood {
    Set<CellCoordinates> cellNeighbors(CellCoordinates cell);
    boolean getWrap();
}
