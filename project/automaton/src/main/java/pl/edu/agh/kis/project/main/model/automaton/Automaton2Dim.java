package pl.edu.agh.kis.project.main.model.automaton;

import pl.edu.agh.kis.project.main.exceptions.InvalidCoordsException;
import pl.edu.agh.kis.project.main.model.Cell;
import pl.edu.agh.kis.project.main.model.factory.CellStateFactory;
import pl.edu.agh.kis.project.main.model.coords.CellCoordinates;
import pl.edu.agh.kis.project.main.model.coords.Coords2D;
import pl.edu.agh.kis.project.main.model.neighbourhood.CellNeighborhood;
import pl.edu.agh.kis.project.main.model.states.CellState;

import java.util.TreeMap;

/**
 * Created by Kamil on 24.11.2015.
 */
public abstract class Automaton2Dim extends Automaton {
    private int width;
    private int height;

    public Automaton2Dim(CellNeighborhood neighboursStrategy, CellStateFactory stateFactory, int width, int height) {
        super(neighboursStrategy, stateFactory);
        this.width = width;
        this.height = height;
        TreeMap<CellCoordinates,CellState> cells = new TreeMap<CellCoordinates, CellState>();
        for(int y=0;y<height;y++)
            for (int x=0;x<width;x++) {
                Coords2D coords2D = new Coords2D(x,y);
                cells.put(coords2D, stateFactory.initialState(coords2D));
            }
        this.insertStructure(cells);
    }

    @Override
    protected boolean hasNextCoordinates(CellCoordinates coords) {
        if(!(coords instanceof Coords2D))
            throw new IllegalArgumentException("Automaton2Dim supports only Coords2D");
        Coords2D coords2D = (Coords2D) coords;
        if(coords2D.x <0 || coords2D.y <0)
            return true;
        else if(coords2D.x<width-1 && coords2D.y<height-1)
            return true;
        else if (coords2D.x == width - 1)
            return (coords2D.y - (height - 1)) < 0;
        else
            return coords2D.y == height - 1 && (coords2D.x - (width - 1)) < 0;
    }

    @Override
    protected CellCoordinates initialCoordinates() {
        return new Coords2D(-1,-1);
    }

    @Override
    protected CellCoordinates nextCoordinates(CellCoordinates coords) {
            Coords2D coords2D = (Coords2D) coords;
            if(coords2D.x<0 && coords2D.y<0)
                return new Coords2D(0,0);
            else if(coords2D.x == width-1)
                return new Coords2D(0,coords2D.y+1);
            else
                return new Coords2D(coords2D.x+1, coords2D.y);
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(Cell c: this){
            Coords2D coords2D = (Coords2D) c.coords;
            if(coords2D.x == width-1 && coords2D.y != height-1)
                stringBuilder.append(c.state.toString()+"\n");
            else
                stringBuilder.append(c.state.toString()+" ");
        }
        return stringBuilder.toString();
    }

    @Override
    public void resize(int newWidth, int newHeight) {
        int biggerX = (newWidth>width) ? newWidth : width;
        int biggerY = (newHeight>height) ? newHeight : height;
        for (int y = 0; y < biggerY; y++)
            for (int x = 0; x < biggerX; x++){
                if(x>width-1 || y>height-1)
                    putNewCell(new Coords2D(x,y));
                else if(x>newWidth-1 || y>newHeight-1)
                    removeCell(new Coords2D(x,y));
            }
        width = newWidth;
        height = newHeight;
        getNeighbourhood().setNewSize(new Coords2D(newWidth,newHeight));
    }

    public void putStructure(CellState[][] structure, Coords2D start) {
        if(start.x > width || start.y > height)
            throw new InvalidCoordsException();

        for (int y = 0; y<structure.length; y++)
            for (int x = 0;x < structure[0].length; x++){
                int coordsX = start.x + x;
                int coordsY = start.y + y;
                if(getWrap()){
                    coordsX = Math.floorMod(coordsX,width);
                    coordsY = Math.floorMod(coordsY,height);
                }
                if(coordsX < width-1 && coordsY < height-1){
                    setCell(new Coords2D(coordsX,coordsY),structure[y][x]);
                }
            }
    }
}
