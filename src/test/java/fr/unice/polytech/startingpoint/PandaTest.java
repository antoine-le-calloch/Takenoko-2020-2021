package fr.unice.polytech.startingpoint;

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
    void coordWhereMovePanda_FirstParcelWithoutBamboo() {
        Coordinate coordParcel1 = new Coordinate(1, -1, 0);//parcel entre 2-4h
        Coordinate coordParcel2 = new Coordinate(1, -1, 0);//parcel entre 0-2h
        Coordinate coordParcel3 = new Coordinate(1, -1, 0);//parcel a 2 éloigné de 1
        PandaBot bot1 = new PandaBot(new Resource(),board);
        board.placeParcel(parcel1,coordParcel1);//place la parcel (un bamboo pousse)
        board.placeParcel(parcel1,coordParcel2);//place la parcel (un bamboo pousse)
        board.placeParcel(parcel1,coordParcel3);//place la parcel (aucun bamboo pour car éloigné du centre donc pas irrigé)

        List<Coordinate> possibleCoordinates = new ArrayList<>();
        possibleCoordinates.add(coordParcel3);
        possibleCoordinates.add(coordParcel2);
        possibleCoordinates.add(coordParcel1);

        assertEquals(coordParcel2,bot1.strategyMovePanda(possibleCoordinates));
    }

    @Test
    void movePanda_1ParcelEtBamboo() {
        Resource resource = Mockito.mock(Resource.class);
        List<Mission> deckVide = new ArrayList<>();
        Mockito.when(resource.getDeckPandaMission()).thenReturn(deckVide);//empêche de piocher une mission

        Coordinate coordBamboo = new Coordinate(1, -1, 0);//parcel entre 2-4h
        PandaBot bot1 = new PandaBot(resource,board);

        board.placeParcel(parcel1,coordBamboo);//place la parcel (un bamboo pousse)

        assertEquals(1,board.getPlacedParcels().get(coordBamboo).getNbBamboo());
        bot1.botPlay();//fait jouer le panda(il vas manger le bamboo)
        assertEquals(0,board.getPlacedParcels().get(coordBamboo).getNbBamboo());
    }

    @Test
    void drawMission() {
        PandaBot bot1 = new PandaBot(new Resource(),board);

        assertEquals(0,bot1.getInventory().getMission().size());
        bot1.botPlay();//fait jouer le panda(il vas piocher)
        assertEquals(1,bot1.getInventory().getMission().size());
    }
}
