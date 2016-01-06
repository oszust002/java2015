package pl.edu.agh.kis.project.main.gui.cells;

import pl.edu.agh.kis.project.main.model.coords.Coords2D;
import pl.edu.agh.kis.project.main.model.states.CellState;
import pl.edu.agh.kis.project.main.model.states.QuadState;

import java.awt.*;

/**
 * Created by Kamil on 02.01.2016.
 */
public class QuadStatePaint extends CellPaint {
    public QuadStatePaint(Coords2D size) {
        super(size);
    }

    @Override
    public void paint(Graphics g, Coords2D coords, CellState state) {
        QuadState cellState = (QuadState) state;
        switch (cellState){
            case DEAD:
                g.setColor(Color.WHITE);
                break;
            case YELLOW:
                g.setColor(Color.YELLOW);
                break;
            case RED:
                g.setColor(Color.RED);
                break;
            case GREEN:
                g.setColor(Color.GREEN);
                break;
            case BLUE:
                g.setColor(Color.BLUE);
                break;
            default:
                throw new IllegalArgumentException("Paint of unhandled state requested");
        }
        g.fillRect(coords.x,coords.y,rectSize.x,rectSize.y);
    }
}
