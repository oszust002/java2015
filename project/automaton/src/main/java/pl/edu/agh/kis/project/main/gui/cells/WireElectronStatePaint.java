package pl.edu.agh.kis.project.main.gui.cells;

import pl.edu.agh.kis.project.main.model.coords.Coords2D;
import pl.edu.agh.kis.project.main.model.states.CellState;
import pl.edu.agh.kis.project.main.model.states.WireElectronState;

import java.awt.*;

/**
 * Created by Kamil on 02.01.2016.
 */
public class WireElectronStatePaint extends CellPaint{

    public WireElectronStatePaint(Coords2D size) {
        super(size);
    }

    @Override
    public void paint(Graphics g, Coords2D coords, CellState state) {
        WireElectronState cellState = (WireElectronState) state;
        switch (cellState){
            case VOID:
                g.setColor(Color.WHITE);
                break;
            case WIRE:
                g.setColor(Color.RED);
                break;
            case ELECTRON_HEAD:
                g.setColor(Color.BLUE);
                break;
            case ELECTRON_TAIL:
                g.setColor(Color.LIGHT_GRAY);
                break;
            default:
                throw new IllegalArgumentException("Paint of unhandled state requested");
        }
        g.fillRect(coords.x, coords.y, rectSize.x, rectSize.y);
    }
}
