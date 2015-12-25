package pl.edu.agh.kis.project.main;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import pl.edu.agh.kis.project.main.neighbourhood.CellNeighborhood;
import pl.edu.agh.kis.project.main.neighbourhood.MoorNeighbourhood;
import pl.edu.agh.kis.project.main.neighbourhood.VonNeumanNeighborhood;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Kamil on 07.12.2015.
 */
public class NeighbourhoodTest2D {
    CellCoordinates cell;
    CellNeighborhood neighborhood;

    @Before
    public void setCell(){
        cell = new Coords2D(3,3);
    }

    @Test
    public void checkVonNeuman_rIs1(){
        neighborhood = new VonNeumanNeighborhood(1,6,6,false);
        Set<CellCoordinates> created = neighborhood.cellNeighbors(cell);
        Set<CellCoordinates> expected = new TreeSet<CellCoordinates>();
        expected.add(new Coords2D(2,3));
        expected.add(new Coords2D(3,2));
        expected.add(new Coords2D(3,4));
        expected.add(new Coords2D(4,3));
        assertEquals("Von Neumann Neighborhood is wrong", expected, created);
    }

    @Test
    public void checkMoor_rIs1(){
        neighborhood = new MoorNeighbourhood(1,6,6,false);
        Set<CellCoordinates> created = neighborhood.cellNeighbors(cell);
        Set<CellCoordinates> expected = new TreeSet<CellCoordinates>();
        expected.add(new Coords2D(2,2));
        expected.add(new Coords2D(2,3));
        expected.add(new Coords2D(2,4));
        expected.add(new Coords2D(3,2));
        expected.add(new Coords2D(3,4));
        expected.add(new Coords2D(4,3));
        expected.add(new Coords2D(4,4));
        expected.add(new Coords2D(4,2));
        assertEquals("Moor Neighborhood is wrong", expected, created);
    }
}
