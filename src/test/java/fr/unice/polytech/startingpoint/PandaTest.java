package fr.unice.polytech.startingpoint;

import fr.unice.polytech.startingpoint.Game.*;
import fr.unice.polytech.startingpoint.Game.Character;
import fr.unice.polytech.startingpoint.Type.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

public class PandaTest {
    Character panda;
    Board board;
    Parcel parcel1;

    @BeforeEach void setUp(){
        panda = new Character(CharacterType.PANDA);
        board = new Board();
        parcel1 = new Parcel(ColorType.RED);
    }

    @Test
    void goodEat() {
        board.placeParcel(parcel1, new Coordinate(1, -1, 0));
        panda.action(parcel1.getCoordinates(),board);
        assertEquals(0,parcel1.getNbBamboo());
    }

    @Test
    void minEat() {
        board.placeParcel(parcel1, new Coordinate(1, -1, 0));
        for (int i = 0; i < 10; i++) {
            panda.action(parcel1.getCoordinates(),board);
        }
        assertEquals(0,parcel1.getNbBamboo());
    }
}
