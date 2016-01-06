package pl.edu.agh.kis.project.main.gui.cells;

import pl.edu.agh.kis.project.main.model.coords.CellCoordinates;
import pl.edu.agh.kis.project.main.model.coords.Coords2D;
import pl.edu.agh.kis.project.main.model.states.CellState;

import java.awt.*;

/**
 * Created by Kamil on 02.01.2016.
 */
public abstract class CellPaint {
    protected final Coords2D rectSize;

    public CellPaint(CellCoordinates rectSize){
        this.rectSize = (Coords2D) rectSize;
    }

    public abstract void paint(Graphics g, Coords2D coords, CellState state);
}
