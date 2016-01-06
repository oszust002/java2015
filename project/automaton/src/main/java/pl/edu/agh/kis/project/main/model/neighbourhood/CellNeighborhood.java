package pl.edu.agh.kis.project.main.model.neighbourhood;

import pl.edu.agh.kis.project.main.model.coords.CellCoordinates;

import java.util.Set;

/**
 * Created by Kamil on 23.11.2015.
 */
public interface CellNeighborhood {
    Set<CellCoordinates> cellNeighbors(CellCoordinates cell);
    boolean getWrap();
    void setNewSize(CellCoordinates coordinates);
    void setWrap(boolean wrap);
}
