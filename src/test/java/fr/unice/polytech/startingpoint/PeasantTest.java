package fr.unice.polytech.startingpoint;

import fr.unice.polytech.startingpoint.Game.Board;
import fr.unice.polytech.startingpoint.Game.Coordinate;
import fr.unice.polytech.startingpoint.Game.Character;
import fr.unice.polytech.startingpoint.Game.Parcel;
import fr.unice.polytech.startingpoint.Type.CharacterType;
import fr.unice.polytech.startingpoint.Type.ColorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
