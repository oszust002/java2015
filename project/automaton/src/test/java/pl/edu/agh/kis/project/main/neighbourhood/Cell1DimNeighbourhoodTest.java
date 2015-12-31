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
 * Created by Kamil on 29.12.2015.
 */
public class Cell1DimNeighbourhoodTest {

    private Set<CellCoordinates> getNeighbours(int x, boolean wrap){
        Coords1D coords1D = new Coords1D(x);
        Cell1DimNeighbourhood neighbourhood = new Cell1DimNeighbourhood(5,wrap);
        return neighbourhood.cellNeighbors(coords1D);
    }

    @Test
    public void GetNeighboursOf2_IsEqualToExpected(){
        Set<CellCoordinates> neighbours = getNeighbours(2,false);
        Set<CellCoordinates> expected = new TreeSet<CellCoordinates>();
        expected.add(new Coords1D(3));
        expected.add(new Coords1D(1));
        assertEquals(expected,neighbours);
    }

    @Test
    public void GetNeighboursOf0WithoutWrap_EqualToExpected(){
        Set<CellCoordinates> neighbours = getNeighbours(0,false);
        Set<CellCoordinates> expected = new TreeSet<CellCoordinates>();
        expected.add(new Coords1D(1));
        assertEquals(expected,neighbours);
    }

    @Test
    public void GetNeighboursOf0WithWrap_EqualToExpected(){
        Set<CellCoordinates> neighbours = getNeighbours(0,true);
        Set<CellCoordinates> expected = new TreeSet<CellCoordinates>();
        expected.add(new Coords1D(1));
        expected.add(new Coords1D(4));
        assertEquals(expected,neighbours);
    }

    @Test
    public void cellNeighboursThrowsIllegalArgumentException_MessageIsEqualToExpected(){
        try{
            Cell1DimNeighbourhood neighbourhood = new Cell1DimNeighbourhood(5,false);
            neighbourhood.cellNeighbors(new Coords2D(2,1));
            fail("Expected IllegalArgumentException to be thrown");
        }
        catch (IllegalArgumentException e){
            assertEquals(e.getMessage(),"Cell1DimNeighbourhood supports only Coords1D");
        }
    }

    @Test
    public void EqualityTest(){
        assertEquals(new Cell1DimNeighbourhood(5,false),new Cell1DimNeighbourhood(5,false));
    }

    @Test
    public void NotInstanceOfCell1DimNeighbourhoodInEqualsMethod(){
        Cell1DimNeighbourhood cell1DimNeighbourhood = new Cell1DimNeighbourhood(5,false);
        MoorNeighbourhood moorNeighbourhood = new MoorNeighbourhood(1,6,6,false);
        assertEquals(cell1DimNeighbourhood.equals(moorNeighbourhood),false);
    }
}
