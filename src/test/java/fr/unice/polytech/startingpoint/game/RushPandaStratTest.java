package fr.unice.polytech.startingpoint.game;


import fr.unice.polytech.startingpoint.bot.Bot;
import fr.unice.polytech.startingpoint.bot.PandaBot;
import fr.unice.polytech.startingpoint.bot.RushPandaStrat;
import fr.unice.polytech.startingpoint.type.MissionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RushPandaStratTest {
    Board board;
    Parcel parcel1;
    Bot bot;
    Coordinate coordinate1;
    Game game;
    Rules rules;
    RushPandaStrat rushPandaStrat;

    @BeforeEach void setUp() {
        game = new Game();
        board = game.getBoard();
        rules = game.getRules();
        parcel1 = new Parcel();
        bot = new PandaBot(game.getGameInteraction(), rules);
        coordinate1 = new Coordinate(1, -1, 0);
        rushPandaStrat = new RushPandaStrat(bot, rules);
    }

    /**
     <h2><u>Strategy Move Panda</u></h2>

     */

    @Test
    void coordWhereMovePanda_0parcel() {
        assertNull(rushPandaStrat.strategyMovePanda(rushPandaStrat.possibleCoordinatesPanda()));//Pas de parcel, il ne bouge pas
    }


    @Test
    void coordWhereMovePanda_1parcelWithBamboo() {
        board.placeParcel(parcel1,coordinate1);//place la parcel (un bamboo pousse)
        assertEquals(coordinate1, rushPandaStrat.strategyMovePanda(rushPandaStrat.possibleCoordinatesPanda()));//Le panda veut aller dessus
    }

    @Test
    void coordWhereMovePanda_FirstParcelWithBamboo(){
        Coordinate coordParcel1 = new Coordinate(1, -1, 0);//parcel entre 2-4h
        Coordinate coordParcel2 = new Coordinate(0, -1, 1);//parcel entre 4-6h
        Coordinate coordParcel3 = new Coordinate(1, -2, 1);//parcel a 4h éloigné de 1
        Coordinate coordParcel4 = new Coordinate(0, -2, 2);//parcel a 5h éloigné de 1
        board.placeParcel(parcel1,coordParcel1);//place la parcel (un bamboo pousse)
        board.placeParcel(new Parcel(),coordParcel2);//place la parcel (un bamboo pousse)
        board.placeParcel(new Parcel(),coordParcel3);//place la parcel (aucun bamboo pour car éloigné du centre donc pas irrigé)
        board.placeParcel(new Parcel(),coordParcel4);//place la parcel (aucun bamboo pour car éloigné du centre donc pas irrigé)
        List<Coordinate> possibleCoordinates = rushPandaStrat.possibleCoordinatesPanda();
        assertEquals(coordParcel1, rushPandaStrat.strategyMovePanda(possibleCoordinates));
    }

    @Test
    void movePanda_1ParcelEtBamboo() {
        board.placeParcel(parcel1,coordinate1);//place la parcel (un bamboo pousse)
        assertEquals(1,board.getPlacedParcels().get(coordinate1).getNbBamboo());//1 bamboo sur la parcel
        bot.botPlay();//fait jouer le panda(il va manger le bamboo)
        assertEquals(0,board.getPlacedParcels().get(coordinate1).getNbBamboo());//0 bamboo sur la parcel
    }

    @Test
    void drawMission() {
        assertEquals(0,game.getGameInteraction().getInventoryPandaMissions().size() );//0 mission dans son inventaire
        game.getPlayerData().getBot().drawMission(MissionType.PANDA);//fait jouer le panda(il vas piocher)
        assertEquals(1, game.getGameInteraction().getInventoryPandaMissions().size());//1 mission dans son inventaire
    }
}