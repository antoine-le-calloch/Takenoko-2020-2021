package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public void isNottAround() {
        List<Coordinate> coordAround;
        coordAround = co4.coordinatesAround();
        assertFalse(coordAround.contains(new Coordinate(1, 0, -1)));
        assertFalse(coordAround.contains(new Coordinate(-1, 2, -1)));
        assertFalse(coordAround.contains(new Coordinate(0, 0, 0)));
    }

    @Test public void goodNorm(){
        assertEquals(0,Coordinate.getNorm(co1,co1));
        assertEquals(6,Coordinate.getNorm(co1,co3));
        assertEquals(14,Coordinate.getNorm(co1,co4));
    }

}
