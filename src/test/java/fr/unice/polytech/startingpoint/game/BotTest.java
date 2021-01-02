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
    private ParcelBot bot1;
    private Board board;
    private Strategie strategie;

    @BeforeEach
    public void setUp(){
        game = new Game();
        parcel1 = new Parcel(ColorType.GREEN);
        bot1 = new ParcelBot(game.getGameInteraction());
        board = game.getBoard();
        strategie = new RandomStrat(bot1);
    }



    @Test
    public void movePanda(){
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        bot1.movePanda(strategie.possibleCoordinatesPanda().get(0));
        assertEquals(1, game.getPlayerData().getInventory().getBamboo(ColorType.GREEN));
        assertEquals(0,parcel1.getNbBamboo());
    }

    @Test
    public void movePeasant(){
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        bot1.movePeasant(strategie.possibleCoordinatesPanda().get(0));
        assertEquals(2,parcel1.getNbBamboo());
    }

}