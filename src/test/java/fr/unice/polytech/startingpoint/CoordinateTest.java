package fr.unice.polytech.startingpoint;

import fr.unice.polytech.startingpoint.Game.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class CoordinateTest {
    private Coordinate co1;
    private Coordinate co2;
    private Coordinate co3;
    private Coordinate co4;

    @BeforeEach public void setUp(){
        co1 = new Coordinate(1,-1,0);
        co2 = new Coordinate(1,-1,0);
        co3 = new Coordinate(0,1,-1);
        co4 = new Coordinate(-2,0,2);
    }

    @Test
    public void coorEquals(){ //new equals
        assertEquals(co2, co1);
        assertNotEquals(co3, co2);
        assertNotEquals(null, co1);
    }

    @Test
    public void addCoInSet(){ //new hashCode
        Set<Coordinate> freeCoordinate = new HashSet<>();
        assertTrue(freeCoordinate.add(co1));
        assertTrue(freeCoordinate.add(co3));
        assertFalse(freeCoordinate.add(co2));
        assertFalse(freeCoordinate.add(co3));
    }

    @Test
    public void isAround(){
        List<Coordinate> coordAround;
        coordAround = co4.coordinatesAround();
        assertTrue(coordAround.contains(new Coordinate(-1,-1,2)));
        assertTrue(coordAround.contains(new Coordinate(-1,0,1)));
        assertTrue(coordAround.contains(new Coordinate(-2,1,1)));
        assertTrue(coordAround.contains(new Coordinate(-3,1,2)));
        assertTrue(coordAround.contains(new Coordinate(-3,0,3)));
        assertTrue(coordAround.contains(new Coordinate(-2,-1,3)));
    }

    @Test
    public void isNotAround() {
        List<Coordinate> coordAround;
        coordAround = co4.coordinatesAround();
        assertFalse(coordAround.contains(new Coordinate(1, 0, -1)));
        assertFalse(coordAround.contains(new Coordinate(-1, 2, -1)));
        assertFalse(coordAround.contains(new Coordinate(0, 0, 0)));
    }

    @Test
    public void goodNorm(){
        assertEquals(0,Coordinate.getNorm(co1,co1));
        assertEquals(6,Coordinate.getNorm(co1,co3));
        assertEquals(14,Coordinate.getNorm(co1,co4));
    }

    @Test
    public void onTheSameLine(){
        assertTrue(new Coordinate(0,0,0).isOnTheSameLine(new Coordinate(1,-1,0)));
        assertTrue(new Coordinate(0,0,0).isOnTheSameLine(new Coordinate(0,2,-2)));
        assertTrue(new Coordinate(3,-1,-2).isOnTheSameLine(new Coordinate(-2,-1,3)));
    }

    @Test
    public void notOnTheSameLine(){
        assertFalse(new Coordinate(0,0,0).isOnTheSameLine(new Coordinate(1,-2,1)));
        assertFalse(new Coordinate(1,-1,0).isOnTheSameLine(new Coordinate(0,2,-2)));
        assertFalse(new Coordinate(-2,-1,3).isOnTheSameLine(new Coordinate(2,1,-3)));
    }


    @Test
    public void goodUnitVector(){
        assertEquals(new Coordinate(1,-1,0),Coordinate.getUnitVector(new Coordinate(0,0,0),new Coordinate(5,-5,0)));
        assertEquals(new Coordinate(1,-1,0),Coordinate.getUnitVector(new Coordinate(-3,2,1),new Coordinate(2,-3,1)));
        assertEquals(new Coordinate(0,1,-1),Coordinate.getUnitVector(new Coordinate(2,-3,1),new Coordinate(2,1,-3)));
    }

    @Test
    public void wrongUnitVector(){
        assertNotEquals(new Coordinate(-1,1,0),Coordinate.getUnitVector(new Coordinate(0,0,0),new Coordinate(5,-5,0)));
        assertNotEquals(new Coordinate(-1,1,0),Coordinate.getUnitVector(new Coordinate(-3,2,1),new Coordinate(2,-3,1)));
        assertNotEquals(new Coordinate(0,-1,1),Coordinate.getUnitVector(new Coordinate(2,-3,1),new Coordinate(2,1,-3)));
    }

    @Test
    public void goodVector(){
        assertEquals(new Coordinate(5,-5,0),Coordinate.getVector(new Coordinate(0,0,0),new Coordinate(5,-5,0)));
        assertEquals(new Coordinate(5,-5,0),Coordinate.getVector(new Coordinate(-3,2,1),new Coordinate(2,-3,1)));
        assertEquals(new Coordinate(0,4,-4),Coordinate.getVector(new Coordinate(2,-3,1),new Coordinate(2,1,-3)));
    }

    @Test
    public void wrongVector(){
        assertNotEquals(new Coordinate(4,-4,0),Coordinate.getVector(new Coordinate(0,0,0),new Coordinate(5,-5,0)));
        assertNotEquals(new Coordinate(1,-1,0),Coordinate.getVector(new Coordinate(-3,2,1),new Coordinate(2,-3,1)));
        assertNotEquals(new Coordinate(0,5,-5),Coordinate.getVector(new Coordinate(2,-3,1),new Coordinate(2,1,-3)));
    }

    @Test
    public void coordinateBetween(){
        assertEquals(new ArrayList<>(Collections.singletonList(new Coordinate(1, -1, 0))), Coordinate.getAllCoordinatesBetween(new Coordinate(0,0,0),new Coordinate(2,-2,0)));
        assertEquals(new ArrayList<>(Collections.singletonList(new Coordinate(-1, -1, 2))), Coordinate.getAllCoordinatesBetween(new Coordinate(-2,0,2),new Coordinate(0,-2,2)));
        assertEquals(new ArrayList<>(Collections.singletonList(new Coordinate(-3, 1, 2))), Coordinate.getAllCoordinatesBetween(new Coordinate(-3,2,1),new Coordinate(-3,0,3)));
    }
}
