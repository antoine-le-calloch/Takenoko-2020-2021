package fr.unice.polytech.startingpoint;

import fr.unice.polytech.startingpoint.Bot.Bot;
import fr.unice.polytech.startingpoint.Bot.PandaBot;
import fr.unice.polytech.startingpoint.Bot.PeasantBot;
import fr.unice.polytech.startingpoint.Game.Character;
import fr.unice.polytech.startingpoint.Game.*;
import fr.unice.polytech.startingpoint.Type.*;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    void coordWhereMovePeasant_0parcel() {
        PeasantBot bot1 = new PeasantBot(new Resource(),board);

        assertNull(bot1.strategyMovePeasant(bot1.possibleCoordinatesPanda()));
    }

    @Test
    void coordWhereMovePeasant_1parcelWith1Bamboo() {
        Coordinate coordParcel = new Coordinate(1, -1, 0);//parcel entre 2-4h
        PeasantBot bot1 = new PeasantBot(new Resource(),board);
        board.placeParcel(parcel1,coordParcel);//place la parcel (un bamboo pousse)

        assertNull(bot1.strategyMovePeasant(bot1.possibleCoordinatesPanda()));
    }

    @Test
    void coordWhereMovePeasant_1parcelWith2Bamboo() {
        Board boardMock = Mockito.mock(Board.class);
        Map<Coordinate,Parcel> placedParcel = new HashMap<>();//simule une parcel avec 2 bamboo
        Coordinate coordParcel = new Coordinate(1, -1, 0);//parcel entre 2-4h
        Parcel parcel2Bamboo = new Parcel(ColorType.NO_COLOR); //créée la parcel
        parcel2Bamboo.addBamboo(); parcel2Bamboo.addBamboo(); //ajoute les 2 bamboo
        placedParcel.put(coordParcel,parcel2Bamboo);//ajoute la parcel avec 2 bamboo a la liste des parcels placées

        Mockito.when(boardMock.getPlacedParcels()).thenReturn(placedParcel);//donne une position pour déplacer le paysan

        PeasantBot bot1 = new PeasantBot(new Resource(),boardMock);

        List<Coordinate> coordsParcels = new ArrayList<>();
        coordsParcels.add(coordParcel);
        assertEquals(coordParcel,bot1.strategyMovePeasant(coordsParcels));
    }

    @Test
    void movePeasant_1Parcel2Bamboo() {
        Resource resource = Mockito.mock(Resource.class);
        List<Mission> deckVide = new ArrayList<>();
        Mockito.when(resource.getDeckPandaMission()).thenReturn(deckVide);//empêche de piocher une mission

        Coordinate coordParcel = new Coordinate(1, -1, 0);//parcel entre 2-4h
        Parcel parcel1Bamboo = new Parcel(ColorType.NO_COLOR); //créée la parcel
        parcel1Bamboo.addBamboo(); //ajoute 1 bamboo
        board.placeParcel(parcel1Bamboo,coordParcel);//pose la parcel (cela ajoute un autre bamboo)

        PeasantBot bot1 = new PeasantBot(resource,board);

        assertEquals(2,board.getPlacedParcels().get(coordParcel).getNbBamboo());
        bot1.botPlay();//deplace le paysan sur une parcel avec plus de 1 bamboo (parcel1Bamboo)
        assertEquals(3,board.getPlacedParcels().get(coordParcel).getNbBamboo());
    }

    @Test
    void drawMission() {
        PeasantBot bot1 = new PeasantBot(new Resource(),board);

        assertEquals(0,bot1.getInventory().getMission().size());
        bot1.botPlay();//fait jouer le paysan(il vas piocher)
        assertEquals(1,bot1.getInventory().getMission().size());
    }
}
