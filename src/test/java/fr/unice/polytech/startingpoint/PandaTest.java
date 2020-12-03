package fr.unice.polytech.startingpoint;

import fr.unice.polytech.startingpoint.Game.Board.Board;
import fr.unice.polytech.startingpoint.Game.Board.Coordinate.Coordinate;
import fr.unice.polytech.startingpoint.Game.Board.Object.Character;
import fr.unice.polytech.startingpoint.Game.Board.Object.Parcel;
import fr.unice.polytech.startingpoint.Type.CharacterType;
import fr.unice.polytech.startingpoint.Type.ColorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
