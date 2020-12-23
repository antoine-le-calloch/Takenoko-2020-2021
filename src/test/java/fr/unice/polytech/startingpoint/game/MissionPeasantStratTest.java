package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.bot.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MissionPeasantStratTest {
    Board board;
    Parcel parcel1;
    Bot peasantBot;
    Coordinate coordinate1;
    Game game;
    Bot bot;
    Rules rules;
    MissionPeasantStrat stratMissionPeasant;

    @BeforeEach
    void setUp() {
        game = new Game();
        board = game.getBoard();
        rules = game.getRules();
        parcel1 = new Parcel();
        bot = new PeasantBot(game.getGameInteraction(), rules);
        coordinate1 = new Coordinate(1, -1, 0);
        stratMissionPeasant = new MissionPeasantStrat(bot, rules);
    }
    /**
     <h2><u>Strategy Move Peasant</u></h2>

     */

    @Test
    void coordWhereMovePeasant_0parcel() {
        assertNull(stratMissionPeasant.strategyMovePeasant(stratMissionPeasant.possibleCoordinatesPanda()));//Pas de parcel, il ne bouge pas
    }

    @Test
    void coordWhereMovePeasant_1parcelWith1Bamboo() {
        board.placeParcel(parcel1, coordinate1);//place la parcel (un bamboo pousse)
        assertNull(stratMissionPeasant.strategyMovePeasant(stratMissionPeasant.possibleCoordinatesPanda()));
    }

    @Test
    void coordWhereMovePeasant_1parcelWith2Bamboo() {
        Parcel parcel2Bamboo = new Parcel(); //créée une parcel
        parcel2Bamboo.addBamboo();
        parcel2Bamboo.addBamboo(); //ajoute 2 bamboo
        board.placeParcel(parcel2Bamboo,coordinate1);// ;//ajoute la parcel avec 2 bamboo a la liste des parcels posée
        assertEquals(coordinate1,stratMissionPeasant.strategyMovePeasant(stratMissionPeasant.possibleCoordinatesPeasant()));
    }

    @Test
    void movePeasant_1Parcel2Bamboo()  {

        Resource resource = Mockito.mock(Resource.class);
        List<Mission> deckVide = new ArrayList<>();
        Mockito.when(resource.getDeckPandaMission()).thenReturn(deckVide);//empêche de piocher une mission

        Parcel parcel1Bamboo = new Parcel(); //créée la parcel
        parcel1Bamboo.addBamboo(); //ajoute 1 bamboo
        board.placeParcel(parcel1Bamboo, coordinate1);//pose la parcel (cela ajoute un autre bamboo)

        assertEquals(2,board.getPlacedParcels().get(coordinate1).getNbBamboo());//2 bamboo sur la parcel
        stratMissionPeasant.stratOneTurn();//deplace le paysan sur une parcel avec plus de 1 bamboo (parcel1Bamboo), cela ajoute un bamboo

        assertEquals(3,board.getPlacedParcels().get(coordinate1).getNbBamboo());//3 bamboo sur la parcel
    }

    @Test
    void drawMission() {
        assertEquals(0, game.getGameInteraction().getInventoryPeasantMissions().size());//0 mission dans son inventaire
        stratMissionPeasant.stratOneTurn();//fait jouer le paysan(il vas piocher)
        //assertEquals(1, game.getGameInteraction().getInventoryPeasantMissions().size());//1 mission dans son inventaire
    }
}