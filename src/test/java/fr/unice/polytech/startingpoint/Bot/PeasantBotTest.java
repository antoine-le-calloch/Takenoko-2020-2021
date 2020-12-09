package fr.unice.polytech.startingpoint.Bot;

import fr.unice.polytech.startingpoint.Game.*;
import fr.unice.polytech.startingpoint.Game.Character;
import fr.unice.polytech.startingpoint.Type.CharacterType;
import fr.unice.polytech.startingpoint.Type.ColorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PeasantBotTest {
    Character peasant1;
    Board board;
    Parcel parcel1;
    PeasantBot peasantBot;
    Coordinate coordinate1;

    @BeforeEach
    void setUp(){
        peasant1 = new Character(CharacterType.PEASANT);
        board = new Board();
        parcel1 = new Parcel(ColorType.RED);
        peasantBot = new PeasantBot(new Resource(),board);
        coordinate1 = new Coordinate(1, -1, 0);
    }

    @Test
    void coordWhereMovePeasant_0parcel() {
        assertNull(peasantBot.strategyMovePeasant(peasantBot.possibleCoordinatesPanda()));//Pas de parcel, il ne bouge pas
    }

    @Test
    void coordWhereMovePeasant_1parcelWith1Bamboo() {
        board.placeParcel(parcel1, coordinate1);//place la parcel (un bamboo pousse)

        assertNull(peasantBot.strategyMovePeasant(peasantBot.possibleCoordinatesPanda()));
    }

    @Test
    void coordWhereMovePeasant_1parcelWith2Bamboo() {
        Board boardMock = Mockito.mock(Board.class);
        Map<Coordinate,Parcel> placedParcel = new HashMap<>();//Liste des parcels posées
        Parcel parcel2Bamboo = new Parcel(ColorType.NO_COLOR); //créée une parcel
        parcel2Bamboo.addBamboo(); parcel2Bamboo.addBamboo(); //ajoute 2 bamboo
        placedParcel.put(coordinate1,parcel2Bamboo);//ajoute la parcel avec 2 bamboo a la liste des parcels posées

        Mockito.when(boardMock.getPlacedParcels()).thenReturn(placedParcel);//simule la liste des parcels posées

        PeasantBot bot1 = new PeasantBot(new Resource(),boardMock);

        List<Coordinate> coordsParcels = new ArrayList<>();
        coordsParcels.add(coordinate1);
        assertEquals(coordinate1,bot1.strategyMovePeasant(coordsParcels));
    }

    @Test
    void movePeasant_1Parcel2Bamboo() {
        Resource resource = Mockito.mock(Resource.class);
        List<Mission> deckVide = new ArrayList<>();
        Mockito.when(resource.getDeckPandaMission()).thenReturn(deckVide);//empêche de piocher une mission

        Parcel parcel1Bamboo = new Parcel(ColorType.NO_COLOR); //créée la parcel
        parcel1Bamboo.addBamboo(); //ajoute 1 bamboo
        board.placeParcel(parcel1Bamboo, coordinate1);//pose la parcel (cela ajoute un autre bamboo)

        PeasantBot bot1 = new PeasantBot(resource,board);

        assertEquals(2,board.getPlacedParcels().get(coordinate1).getNbBamboo());//2 bamboo sur la parcel
        bot1.botPlay();//deplace le paysan sur une parcel avec plus de 1 bamboo (parcel1Bamboo), cela ajoute un bamboo
        assertEquals(3,board.getPlacedParcels().get(coordinate1).getNbBamboo());//3 bamboo sur la parcel
    }

    @Test
    void drawMission() {
        assertEquals(0, peasantBot.getInventory().getMissions().size());//0 mission dans son inventaire
        peasantBot.botPlay();//fait jouer le paysan(il vas piocher)
        assertEquals(1, peasantBot.getInventory().getMissions().size());//1 mission dans son inventaire
    }
}