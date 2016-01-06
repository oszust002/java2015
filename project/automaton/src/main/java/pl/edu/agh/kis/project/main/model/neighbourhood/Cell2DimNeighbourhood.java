package pl.edu.agh.kis.project.main.model.neighbourhood;

/**
 * @author Kamil Osuch
 * @version 1.0
 */
public interface Cell2DimNeighbourhood extends CellNeighborhood{
    /**
     * Set radius on which neighbours coordinates will be calculated
     * @param r new radius
     */
    void setRadius(int r);

    /**
     * Get radius on which neighbours coordinates are be calculated
     * @return radius on which neighbours coordinates are be calculated
     */
    int getRadius();

    /**
     * Checks if coordinates are out of board
     * @param x coordinates horizontal value
     * @param y coordinates vertical calue
     * @return True if are out of board, False otherwise
     */
    boolean isOutOfGrid(int x, int y);
}
