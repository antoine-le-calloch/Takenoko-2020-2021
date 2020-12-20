

package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.bot.PandaBot;
import fr.unice.polytech.startingpoint.type.ColorType;
import fr.unice.polytech.startingpoint.type.ImprovementType;
import fr.unice.polytech.startingpoint.type.MissionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        parcel1 = new Parcel(ColorType.NO_COLOR, ImprovementType.NOTHING);
        bot = new PandaBot(game, rules);
        coordinate1 = new Coordinate(1, -1, 0);
    }



    /**
   <h2><u>Strategy Move Panda</u></h2>

     */

    @Test
    void coordWhereMovePanda_0parcel() {
        assertNull(bot.strategyMovePanda(bot.possibleCoordinatesPanda()));//Pas de parcel, il ne bouge pas
    }


    @Test
    void coordWhereMovePanda_1parcelWithBamboo() {
        board.placeParcel(parcel1,coordinate1);//place la parcel (un bamboo pousse)
        assertEquals(coordinate1, bot.strategyMovePanda(bot.possibleCoordinatesPanda()));//Le panda veut aller dessus
    }

    @Test
    void coordWhereMovePanda_FirstParcelWithBamboo(){
        Coordinate coordParcel1 = new Coordinate(1, -1, 0);//parcel entre 2-4h
        Coordinate coordParcel2 = new Coordinate(0, -1, 1);//parcel entre 4-6h
        Coordinate coordParcel3 = new Coordinate(1, -2, 1);//parcel a 4h éloigné de 1
        Coordinate coordParcel4 = new Coordinate(0, -2, 2);//parcel a 5h éloigné de 1
        board.placeParcel(parcel1,coordParcel1);//place la parcel (un bamboo pousse)
        board.placeParcel(new Parcel(ColorType.NO_COLOR,ImprovementType.NOTHING),coordParcel2);//place la parcel (un bamboo pousse)
        board.placeParcel(new Parcel(ColorType.NO_COLOR,ImprovementType.NOTHING),coordParcel3);//place la parcel (aucun bamboo pour car éloigné du centre donc pas irrigé)
        board.placeParcel(new Parcel(ColorType.NO_COLOR,ImprovementType.NOTHING),coordParcel4);//place la parcel (aucun bamboo pour car éloigné du centre donc pas irrigé)
        List<Coordinate> possibleCoordinates = bot.possibleCoordinatesPanda();
        assertEquals(coordParcel1, bot.strategyMovePanda(possibleCoordinates));
    }



    @Test
    void movePanda_1ParcelEtBamboo() {
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
