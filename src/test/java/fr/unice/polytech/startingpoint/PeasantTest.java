package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PeasantTest {
    Peasant peasant1;
    Board board;
    Parcel parcel1;

    @BeforeEach void setUp(){
        peasant1 = new Peasant();
        board = new Board();
        parcel1 = new Parcel("red");
    }

    @Test
    void goodGrow() {
        board.placeParcel(parcel1, new Coordinate(1, -1, 0));
        peasant1.action(parcel1.getCoordinates(),board);
        assertEquals(2,parcel1.getNbBamboo());
    }

    @Test
    void maxGrow() {
        board.placeParcel(parcel1, new Coordinate(1, -1, 0));
        for (int i = 0; i < 10; i++) {
            peasant1.action(parcel1.getCoordinates(),board);
        }
        assertEquals(4,parcel1.getNbBamboo());
    }
}
