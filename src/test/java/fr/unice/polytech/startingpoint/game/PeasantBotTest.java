package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.bot.PeasantBot;
import fr.unice.polytech.startingpoint.exception.BadPlaceParcelException;
import fr.unice.polytech.startingpoint.type.BotType;
import fr.unice.polytech.startingpoint.type.ColorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PeasantBotTest {
    Board board;
    Parcel parcel1;
    PeasantBot peasantBot;
    Coordinate coordinate1;
    Game game;

    @BeforeEach
    void setUp(){
        game=new Game(new BotType[] {BotType.PEASANTBOT},3);
        peasantBot = (PeasantBot) game.getPlayerData().getBot();
        board = game.getBoard();
        parcel1 = new Parcel(ColorType.RED);
        coordinate1 = new Coordinate(1, -1, 0);
    }

    @Test
    void coordWhereMovePeasant_0parcel() {
        assertNull(peasantBot.strategyMovePeasant(peasantBot.possibleCoordinatesPanda()));//Pas de parcel, il ne bouge pas
    }

    @Test
    void coordWhereMovePeasant_1parcelWith1Bamboo() throws BadPlaceParcelException {
        board.placeParcel(parcel1, coordinate1);//place la parcel (un bamboo pousse)
        assertNull(peasantBot.strategyMovePeasant(peasantBot.possibleCoordinatesPanda()));
    }

    @Test
    void coordWhereMovePeasant_1parcelWith2Bamboo() {
        Parcel parcel2Bamboo = new Parcel(ColorType.NO_COLOR); //créée une parcel
        parcel2Bamboo.addBamboo(); parcel2Bamboo.addBamboo(); //ajoute 2 bamboo
        board.placeParcel(parcel2Bamboo,coordinate1);// ;//ajoute la parcel avec 2 bamboo a la liste des parcels posée
        assertEquals(coordinate1,peasantBot.strategyMovePeasant(peasantBot.possibleCoordinatesPeasant()));
    }

    @Test
    void movePeasant_1Parcel2Bamboo() throws BadPlaceParcelException {

        Resource resource = Mockito.mock(Resource.class);
        List<Mission> deckVide = new ArrayList<>();
        Mockito.when(resource.getDeckPandaMission()).thenReturn(deckVide);//empêche de piocher une mission

        Parcel parcel1Bamboo = new Parcel(ColorType.NO_COLOR); //créée la parcel
        parcel1Bamboo.addBamboo(); //ajoute 1 bamboo
        board.placeParcel(parcel1Bamboo, coordinate1);//pose la parcel (cela ajoute un autre bamboo)

        assertEquals(2,board.getPlacedParcels().get(coordinate1).getNbBamboo());//2 bamboo sur la parcel
        peasantBot.botPlay();//deplace le paysan sur une parcel avec plus de 1 bamboo (parcel1Bamboo), cela ajoute un bamboo

        assertEquals(3,board.getPlacedParcels().get(coordinate1).getNbBamboo());//3 bamboo sur la parcel
    }

    @Test
    void drawMission() {
        assertEquals(0, game.getPlayerData().getPeasantMissions().size());//0 mission dans son inventaire
        peasantBot.botPlay();//fait jouer le paysan(il vas piocher)
        assertEquals(1, game.getPlayerData().getPeasantMissions().size());//1 mission dans son inventaire
    }
}
