package pl.edu.agh.kis.project.main.gui.cells;

import pl.edu.agh.kis.project.main.model.coords.Coords2D;
import pl.edu.agh.kis.project.main.model.states.BinaryState;
import pl.edu.agh.kis.project.main.model.states.CellState;

import java.awt.*;

/**
 * Created by Kamil on 02.01.2016.
 */
public class BinaryStatePaint extends CellPaint {

    public BinaryStatePaint(Coords2D size) {
        super(size);
    }

    @Override
    public void paint(Graphics g, Coords2D coords, CellState state) {
        BinaryState cellState = (BinaryState) state;
        if(cellState.isAlive())
            g.setColor(Color.BLUE);
        else
            g.setColor(Color.WHITE);
        g.fillRect(coords.x,coords.y,rectSize.x,rectSize.y);
    }
}
