package fr.unice.polytech.startingpoint;

import fr.unice.polytech.startingpoint.Game.Character;
import fr.unice.polytech.startingpoint.Game.*;
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

public class PeasantTest {
    Character peasant1;
    Board board;
    Parcel parcel1;

    @BeforeEach void setUp(){
        peasant1 = new Character(CharacterType.PEASANT);
        board = new Board();
        parcel1 = new Parcel(ColorType.RED);
    }

    @Test
    void goodGrow() {
        board.placeParcel(parcel1, new Coordinate(1, -1, 0));
        board.moveCharacter(peasant1,parcel1.getCoordinates());
        assertEquals(2,parcel1.getNbBamboo());
    }

    @Test
    void maxGrow() {
        board.placeParcel(parcel1, new Coordinate(1, -1, 0));
        for (int i = 0; i < 10; i++) {
            board.moveCharacter(peasant1,parcel1.getCoordinates());
            board.moveCharacter(peasant1,new Coordinate(0,0,0));
        }
        assertEquals(4,parcel1.getNbBamboo());
    }
}
