package fr.unice.polytech.startingpoint.Game;

import fr.unice.polytech.startingpoint.Bot.Bot;
import fr.unice.polytech.startingpoint.Bot.PandaBot;
import fr.unice.polytech.startingpoint.Game.*;
import fr.unice.polytech.startingpoint.Game.Character;
import fr.unice.polytech.startingpoint.Type.*;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

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

    @BeforeEach void setUp() {
        panda = new Character(CharacterType.PANDA);
        board = new Board();
        parcel1 = new Parcel(ColorType.NO_COLOR);
    }

    @Test
    void coordWhereMovePanda_0parcel() {
        PandaBot bot1 = new PandaBot(new Resource(),board);

        assertNull(bot1.strategyMovePanda(bot1.possibleCoordinatesPanda()));
    }

    @Test
    void coordWhereMovePanda_1parcelWithBamboo() {
        Coordinate coordParcel = new Coordinate(1, -1, 0);//parcel entre 2-4h
        PandaBot bot1 = new PandaBot(new Resource(),board);
        board.placeParcel(parcel1,coordParcel);//place la parcel (un bamboo pousse)

        assertEquals(coordParcel,bot1.strategyMovePanda(bot1.possibleCoordinatesPanda()));
    }

    @Test
    void goodEat() throws MoveCharacterException {
        board.placeParcel(parcel1, new Coordinate(1, -1, 0));
        board.moveCharacter(panda, parcel1.getCoordinates());
        assertEquals(0, parcel1.getNbBamboo());
    }

    @Test
    void minEat() throws MoveCharacterException {
        board.placeParcel(parcel1, new Coordinate(1, -1, 0));
        for (int i = 0; i < 10; i++) {
            board.moveCharacter(panda, parcel1.getCoordinates());
        }
        assertEquals(0, parcel1.getNbBamboo());
    }
}
