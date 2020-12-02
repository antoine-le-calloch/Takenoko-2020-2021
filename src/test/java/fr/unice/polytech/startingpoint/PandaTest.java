package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PandaTest {
    Panda panda;
    Board board;
    Parcel parcel1;

    @BeforeEach void setUp(){
        panda = new Panda();
        board = new Board();
        parcel1 = new Parcel("red");
    }

    @Test
    void goodEat() {
        board.placedParcel(parcel1, new Coordinate(1, -1, 0));
        panda.action(parcel1.getCoordinates(),board);
        assertEquals(0,parcel1.getNbBamboo());
    }

    @Test
    void minEat() {
        board.placedParcel(parcel1, new Coordinate(1, -1, 0));
        for (int i = 0; i < 10; i++) {
            panda.action(parcel1.getCoordinates(),board);
        }
        assertEquals(0,parcel1.getNbBamboo());
    }
}
