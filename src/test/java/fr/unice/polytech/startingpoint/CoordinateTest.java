package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CoordinateTest {
    private Coordinate co1;
    private Coordinate co2;
    private Coordinate co3;

    @BeforeEach public void setUp(){
        co1 = new Coordinate(1,-1,0);
        co2 = new Coordinate(1,-1,0);
        co3 = new Coordinate(0,1,-1);
    }

    @Test
    public void coorEquals(){
        assertEquals(co2, co1);
        assertNotEquals(co3, co2);
    }

    @Test
    public void addCoInSet(){
        Set<Coordinate> freeCoordinate = new HashSet<>();
        assertTrue(freeCoordinate.add(co1));
        assertTrue(freeCoordinate.add(co3));
        assertFalse(freeCoordinate.add(co2));
        assertFalse(freeCoordinate.add(co3));
    }

}
