

package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.bot.PandaBot;
import fr.unice.polytech.startingpoint.exception.BadPlaceParcelException;
import fr.unice.polytech.startingpoint.type.ColorType;
import fr.unice.polytech.startingpoint.type.MissionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PandaBotTest {
    Board board;
    Parcel parcel1;
    PandaBot bot;
    Coordinate coordinate1;
    Game game;
    Rules rules;

    @BeforeEach void setUp() {
        game=new Game();
        board = game.getBoard();
        rules = game.getRules();
        parcel1 = new Parcel(ColorType.NO_COLOR);
        bot = new PandaBot(game, rules);
        coordinate1 = new Coordinate(1, -1, 0);
    }

    @Test
    void coordWhereMovePanda_0parcel() {
        assertNull(bot.strategyMovePanda(bot.possibleCoordinatesPanda()));//Pas de parcel, il ne bouge pas
    }

    @Test
    void coordWhereMovePanda_1parcelWithBamboo() throws BadPlaceParcelException {
        board.placeParcel(parcel1,coordinate1);//place la parcel (un bamboo pousse)

        assertEquals(coordinate1, bot.strategyMovePanda(bot.possibleCoordinatesPanda()));//Le panda veut aller dessus
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

        assertEquals(coordParcel2, bot.strategyMovePanda(possibleCoordinates));
    }

    @Test
    void movePanda_1ParcelEtBamboo() throws BadPlaceParcelException {
        board.placeParcel(parcel1,coordinate1);//place la parcel (un bamboo pousse)
        assertEquals(1,board.getPlacedParcels().get(coordinate1).getNbBamboo());//1 bamboo sur la parcel
        bot.botPlay();//fait jouer le panda(il vas manger le bamboo)
        assertEquals(0,board.getPlacedParcels().get(coordinate1).getNbBamboo());//0 bamboo sur la parcel
    }

    @Test
    void drawMission() {
        assertEquals(0,game.getPlayerData().getPandaMissions().size() );//0 mission dans son inventaire
        game.getPlayerData().getBot().drawMission(MissionType.PANDA);//fait jouer le panda(il vas piocher)
        assertEquals(1, game.getPlayerData().getPandaMissions().size());//1 mission dans son inventaire
    }
}
