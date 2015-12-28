package pl.edu.agh.kis.project.main.neighbourhood;

import org.junit.Test;
import pl.edu.agh.kis.project.main.coords.CellCoordinates;
import pl.edu.agh.kis.project.main.coords.Coords1D;
import pl.edu.agh.kis.project.main.coords.Coords2D;

import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Kamil on 27.12.2015.
 */
public class VonNeumannNeighbourhoodTest {


    private Set<CellCoordinates> getNeighboursCoords(int x, int y, int radius, boolean wrap){
        CellCoordinates cell = new Coords2D(x,y);
        CellNeighborhood neighborhood = new VonNeumanNeighborhood(radius,6,6,wrap);
        return neighborhood.cellNeighbors(cell);
    }

    @Test
    public void GetNeighboursOf3And3_rIs1_IsEqualToExpected(){
            Set<CellCoordinates> created = getNeighboursCoords(3,3,1,false);
            Set<CellCoordinates> expected = new TreeSet<CellCoordinates>();
            expected.add(new Coords2D(2,3));
            expected.add(new Coords2D(3,2));
            expected.add(new Coords2D(3,4));
            expected.add(new Coords2D(4,3));
            assertEquals("Von Neumann Neighborhood is wrong", expected, created);
    }

    @Test
    public void WrapTest_GetNeighboursOf0And0_rIs1_IsEqualToExpected(){
            Set<CellCoordinates> created = getNeighboursCoords(0,0,1,true);
            Set<CellCoordinates> expected = new TreeSet<CellCoordinates>();
            expected.add(new Coords2D(1,0));
            expected.add(new Coords2D(0,1));
            expected.add(new Coords2D(5,0));
            expected.add(new Coords2D(0,5));
            assertEquals(expected,created);
    }

    @Test
    public void cellNeighboursIllegalArgumentException(){
        try {
            VonNeumanNeighborhood neighborhood = new VonNeumanNeighborhood(6,6,false);
            neighborhood.cellNeighbors(new Coords1D(1));
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(),"VonNeumannNeighbourhood supports only Coords2D");
        }
    }

    @Test
    public void EqualityTest(){
        assertEquals(new VonNeumanNeighborhood(1,6,6,false),new VonNeumanNeighborhood(1,6,6,false));
    }

    @Test
    public void NotInstanceOfVonNeumannNeighbourhoodInEqualsMethod(){
        VonNeumanNeighborhood neumanNeighborhood = new VonNeumanNeighborhood(1,6,6,false);
        MoorNeighbourhood moorNeighbourhood = new MoorNeighbourhood(1,6,6,false);
        assertEquals(neumanNeighborhood.equals(moorNeighbourhood),false);
    }

}
