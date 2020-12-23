package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.bot.ParcelBot;

import fr.unice.polytech.startingpoint.bot.RandomStrat;
import fr.unice.polytech.startingpoint.bot.Strategie;
import fr.unice.polytech.startingpoint.type.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

class BotTest {
    private Game game;
    private Parcel parcel1;
    private Parcel parcel2;
    private Parcel parcel3;
    private ParcelBot bot1;
    private Board board;
    private Rules rules;
    private Strategie strategie;

    @BeforeEach
    public void setUp(){
        game = new Game();
        parcel1 = new Parcel(ColorType.BLUE);
        parcel2 = new Parcel();
        parcel3 = new Parcel();
        bot1 = new ParcelBot(game.getGameInteraction(),game.getRules());
        board = game.getBoard();
        rules = game.getRules();
        strategie = new RandomStrat(bot1, rules);
    }



    @Test
    public void movePanda(){
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        bot1.movePanda(strategie.possibleCoordinatesPanda().get(0));
        assertEquals(1, game.getPlayerData().getInventory().getBamboo(ColorType.BLUE));
        assertEquals(0,parcel1.getNbBamboo());
    }

    @Test
    public void movePeasant(){
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        bot1.movePeasant(strategie.possibleCoordinatesPanda().get(0));
        assertEquals(2,parcel1.getNbBamboo());
    }

}