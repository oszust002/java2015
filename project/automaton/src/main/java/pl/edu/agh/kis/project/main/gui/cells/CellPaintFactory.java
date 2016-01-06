package pl.edu.agh.kis.project.main.gui.cells;

import pl.edu.agh.kis.project.main.model.coords.Coords2D;
import pl.edu.agh.kis.project.main.model.states.*;

import java.io.IOException;

/**
 * Created by Kamil on 02.01.2016.
 */
public class CellPaintFactory {

    public static CellPaint getCellPaint(Class<? extends CellState> cellState, Coords2D size) throws IOException {
        if (cellState.equals(BinaryState.class))
            return new BinaryStatePaint(size);
        else if (cellState.equals(QuadState.class))
            return new QuadStatePaint(size);
        else if (cellState.equals(WireElectronState.class))
            return new WireElectronStatePaint(size);
        else if(cellState.equals(LangtonCell.class))
            return new LangtonCellPaint(size);
            throw new IllegalArgumentException();
    }
}
