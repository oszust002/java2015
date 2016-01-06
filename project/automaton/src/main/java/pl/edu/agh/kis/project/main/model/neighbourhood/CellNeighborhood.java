package pl.edu.agh.kis.project.main.model.neighbourhood;

import pl.edu.agh.kis.project.main.model.coords.CellCoordinates;

import java.util.Set;

/**
 * @author Kamil Osuch
 * @version 1.0
 */
public interface CellNeighborhood {
    /**
     * Calculates coordinates which belongs to neighbourhood of specified {@link CellCoordinates}
     * @param cell {@link CellCoordinates} for which neighbours coordinates are calculated
     * @return set of neighbours {@link CellCoordinates}
     */
    Set<CellCoordinates> cellNeighbors(CellCoordinates cell);

    /**
     * Gets information about if neighbours are calculated with board wrapping or not
     * @return True if neighbours are calculated with board wrapping, False otherwise
     */
    boolean getWrap();

    /**
     * Sets new size of board in which neighbours are searched
     * @param coordinates new size of checked board({@link pl.edu.agh.kis.project.main.model.coords.Coords1D} if
     *                    {@link Cell1DimNeighbourhood}, {@link pl.edu.agh.kis.project.main.model.coords.Coords2D}
     *                    if {@link Cell2DimNeighbourhood})
     */
    void setNewSize(CellCoordinates coordinates);

    /**
     * Sets if neighbours should be calculated with or without board wrapping
     * @param wrap True if wrap board, False otherwise
     */
    void setWrap(boolean wrap);
}
