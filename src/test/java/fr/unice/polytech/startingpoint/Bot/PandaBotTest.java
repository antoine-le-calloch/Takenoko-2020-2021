package fr.unice.polytech.startingpoint.Bot;

import fr.unice.polytech.startingpoint.Game.*;
import fr.unice.polytech.startingpoint.Game.Character;
import fr.unice.polytech.startingpoint.Type.CharacterType;
import fr.unice.polytech.startingpoint.Type.ColorType;
import fr.unice.polytech.startingpoint.exception.BadPlaceParcelException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PandaBotTest {
    Character panda;
    Board board;
    Parcel parcel1;
    PandaBot pandaBot;
    Coordinate coordinate1;

    @BeforeEach void setUp() {
        panda = new Character(CharacterType.PANDA);
        board = new Board();
        parcel1 = new Parcel(ColorType.NO_COLOR);
        pandaBot = new PandaBot(new Resource(), board);
        coordinate1 = new Coordinate(1, -1, 0);
    }

    @Test
    void coordWhereMovePanda_0parcel() {
        assertNull(pandaBot.strategyMovePanda(pandaBot.possibleCoordinatesPanda()));//Pas de parcel, il ne bouge pas
    }

    @Test
    void coordWhereMovePanda_1parcelWithBamboo() throws BadPlaceParcelException {
        board.placeParcel(parcel1,coordinate1);//place la parcel (un bamboo pousse)

        assertEquals(coordinate1, pandaBot.strategyMovePanda(pandaBot.possibleCoordinatesPanda()));//Le panda veut aller dessus
    }

    @Test
    void coordWhereMovePanda_FirstParcelWithoutBamboo() throws BadPlaceParcelException {
        Coordinate coordParcel1 = new Coordinate(1, -1, 0);//parcel entre 2-4h
        Coordinate coordParcel2 = new Coordinate(1, -1, 0);//parcel entre 0-2h
        Coordinate coordParcel3 = new Coordinate(1, -1, 0);//parcel a 2 éloigné de 1
        board.placeParcel(parcel1,coordParcel1);//place la parcel (un bamboo pousse)
        board.placeParcel(parcel1,coordParcel2);//place la parcel (un bamboo pousse)
        board.placeParcel(parcel1,coordParcel3);//place la parcel (aucun bamboo pour car éloigné du centre donc pas irrigé)

        List<Coordinate> possibleCoordinates = new ArrayList<>();
        possibleCoordinates.add(coordParcel3);
        possibleCoordinates.add(coordParcel2);
        possibleCoordinates.add(coordParcel1);

        assertEquals(coordParcel2, pandaBot.strategyMovePanda(possibleCoordinates));
    }

    @Test
    void movePanda_1ParcelEtBamboo() throws BadPlaceParcelException {
        Resource resource = Mockito.mock(Resource.class);
        List<Mission> deckVide = new ArrayList<>();
        Mockito.when(resource.getDeckPandaMission()).thenReturn(deckVide);//empêche de piocher une mission

        PandaBot bot1 = new PandaBot(resource,board);

        board.placeParcel(parcel1,coordinate1);//place la parcel (un bamboo pousse)

        assertEquals(1,board.getPlacedParcels().get(coordinate1).getNbBamboo());//1 bamboo sur la parcel
        bot1.botPlay();//fait jouer le panda(il vas manger le bamboo)
        assertEquals(0,board.getPlacedParcels().get(coordinate1).getNbBamboo());//0 bamboo sur la parcel
    }

    @Test
    void drawMission() {
        assertEquals(0, pandaBot.getInventory().getMission().size());//0 mission dans son inventaire
        pandaBot.botPlay();//fait jouer le panda(il vas piocher)
        assertEquals(1, pandaBot.getInventory().getMission().size());//1 mission dans son inventaire
    }


}