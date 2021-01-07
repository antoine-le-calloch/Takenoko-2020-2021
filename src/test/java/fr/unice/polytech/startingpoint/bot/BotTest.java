package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.bot.strategie.RandomStrat;
import fr.unice.polytech.startingpoint.bot.strategie.Strategie;
import fr.unice.polytech.startingpoint.game.Game;
import fr.unice.polytech.startingpoint.game.board.Board;
import fr.unice.polytech.startingpoint.game.board.Coordinate;
import fr.unice.polytech.startingpoint.game.board.Parcel;
import fr.unice.polytech.startingpoint.type.ActionType;
import fr.unice.polytech.startingpoint.type.ColorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(1, game.getPlayerData().getInventory().getInventoryBamboo(ColorType.GREEN));
        assertEquals(0,parcel1.getNbBamboo());
    }

    @Test
    public void movePeasant(){
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        bot1.movePeasant(strategie.possibleCoordinatesPanda().get(0));
        assertEquals(2,parcel1.getNbBamboo());
    }
}