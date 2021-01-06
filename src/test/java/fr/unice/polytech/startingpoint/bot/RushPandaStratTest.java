package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.game.Game;
import fr.unice.polytech.startingpoint.game.board.Board;
import fr.unice.polytech.startingpoint.game.board.Coordinate;
import fr.unice.polytech.startingpoint.game.board.Parcel;
import fr.unice.polytech.startingpoint.game.board.BoardRules;
import fr.unice.polytech.startingpoint.game.mission.PandaMission;
import fr.unice.polytech.startingpoint.type.BotType;
import fr.unice.polytech.startingpoint.type.ColorType;
import fr.unice.polytech.startingpoint.type.ImprovementType;
import fr.unice.polytech.startingpoint.type.WeatherType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static fr.unice.polytech.startingpoint.type.MissionType.PANDA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RushPandaStratTest {
    Board board;
    Parcel parcel1;
    Parcel parcel2;
    PandaBot bot;
    Coordinate coordinate1;
    Coordinate coordinate2;
    Game game;
    BoardRules boardRules;


    @BeforeEach void setUp()  {
        game = new Game(new BotType[]{BotType.PANDA_BOT});
        board = game.getBoard();
        boardRules = game.getRules();
        parcel1 = new Parcel(ColorType.GREEN);
        parcel2 = new Parcel(ColorType.GREEN);
        bot= (PandaBot) game.getPlayerData().getBot();
        coordinate1 = new Coordinate(1, -1, 0);
        coordinate2 = new Coordinate(0, -1, 1);
        game.getPlayerData().getInventory().subPandaMissions(game.getPlayerData().getPandaMissions()); //supprime la mission donner au debut
    }

    /**
     <h2><u>Strategy Move Panda</u></h2>

     */

    @Test
    void coordWhereMovePanda_0parcel() {
        assertNull(bot.getRushPandaStrat().strategyMovePanda());//Pas de parcel, il ne bouge pas
    }

    @Test
    void coordWhereMovePanda_1parcelWithBambooMissionGreenColor() {
        board.placeParcel(parcel1,coordinate1);//place la parcel (un bamboo pousse)
        game.getPlayerData().addMission(new PandaMission(ColorType.GREEN,1));
        assertEquals(coordinate1, bot.getRushPandaStrat().strategyMovePanda());//Le panda veut aller dessus
    }

    @Test
    void coordWhereMovePanda_1parcelWithBambooMissionAllColor() {
        board.placeParcel(parcel1,coordinate1);//place la parcel (un bamboo pousse)
        game.getPlayerData().addMission(new PandaMission(ColorType.ALL_COLOR,1));
        assertEquals(coordinate1, bot.getRushPandaStrat().strategyMovePanda());//Le panda veut aller dessus
    }

    @Test
    void AlreadyHaveABambooInInventory() {
        board.placeParcel(parcel1,coordinate1);//place la parcel (un bamboo pousse)
        board.placeParcel(parcel2,coordinate2);//place la parcel (un bamboo pousse)
        game.getPlayerData().addMission(new PandaMission(ColorType.ALL_COLOR,1));

        bot.movePanda(bot.getRushPandaStrat().strategyMovePanda());//ajoute un bamboo à l'inventaire
        game.getPlayerData().resetTemporaryInventory(WeatherType.NO_WEATHER);
        assertNull(bot.getRushPandaStrat().strategyMovePanda());//Le panda ne veut pas aller dessus
    }

    @Test
    void AlreadyHaveABambooInInventoryMissionOneColor() {
        board.placeParcel(parcel1,coordinate1);//place la parcel (un bamboo pousse)
        board.placeParcel(parcel2,coordinate2);//place la parcel (un bamboo pousse)
        game.getPlayerData().addMission(new PandaMission(ColorType.GREEN,1));
        bot.movePanda(bot.getRushPandaStrat().strategyMovePanda());//ajoute un bamboo à l'inventaire
        game.getPlayerData().resetTemporaryInventory(WeatherType.NO_WEATHER);
        bot.movePanda(bot.getRushPandaStrat().strategyMovePanda());//ajoute un bamboo à l'inventaire
        assertNull(bot.getRushPandaStrat().strategyMovePanda());//Le panda ne veut pas aller dessus*/
    }

    @Test
    void colorMissionDifferentFromColorParcelSoNoGoodDeplacement() {
        board.placeParcel(parcel1,coordinate1);//place la parcel (un bamboo pousse)
        game.getPlayerData().addMission(new PandaMission(ColorType.RED,1));
        assertNull(bot.getRushPandaStrat().strategyMovePanda());//Le panda ne veut pas aller dessus
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
        game.getPlayerData().addMission(new PandaMission(ColorType.GREEN,1));
        assertEquals(coordParcel1, bot.getRushPandaStrat().strategyMovePanda());
    }

    @Test
    void movePanda_1ParcelEtBamboo() {
        board.placeParcel(parcel1,coordinate1);//place la parcel (un bamboo pousse)
        assertEquals(1,board.getPlacedParcels().get(coordinate1).getNbBamboo());//1 bamboo sur la parcel
        game.getPlayerData().addMission(new PandaMission(ColorType.GREEN,1));
        bot.botPlay(WeatherType.NO_WEATHER);
        assertEquals(0,board.getPlacedParcels().get(coordinate1).getNbBamboo());//0 bamboo sur la parcel
    }

    @Test
    void movePanda_WhereNoImprovement(){
        board.placeParcel(parcel1,coordinate1);//place la parcel (un bamboo pousse)
        board.placeParcel(new Parcel(ColorType.GREEN, ImprovementType.ENCLOSURE),new Coordinate(0,-1,1));
        game.getPlayerData().addMission(new PandaMission(ColorType.GREEN,1));
        assertEquals(coordinate1,bot.getRushPandaStrat().strategyMovePanda());
    }

    @Test
    void drawMission() {
        assertEquals(0,game.getGameInteraction().getInventoryPandaMissions().size() );//0 mission dans son inventaire
        game.getPlayerData().getBot().drawMission(PANDA);//fait jouer le panda(il vas piocher)
        assertEquals(1, game.getGameInteraction().getInventoryPandaMissions().size());//1 mission dans son inventaire
    }

    @Test
    void stratThunderstormPanda(){
        Game gamePanda= new Game(new BotType[]{BotType.PANDA_BOT});
        PandaBot pandaBot= (PandaBot) gamePanda.getPlayerData().getBot();
        Parcel parcel=new Parcel(ColorType.GREEN);
        Parcel parcel2=new Parcel(ColorType.GREEN);
        parcel2.addBamboo();
        gamePanda.getBoard().placeParcel(parcel,new Coordinate(1,-1,0));
        gamePanda.getBoard().placeParcel(new Parcel(),new Coordinate(1,-1,0));
        gamePanda.getBoard().placeParcel(parcel2,new Coordinate(1,0,-1));
        gamePanda.getPlayerData().addMission(new PandaMission(ColorType.GREEN,2));
        gamePanda.getPlayerData().getInventory().subPandaMissions(game.getPlayerData().getPandaMissions());
        assertEquals(new Coordinate(1,0,-1),pandaBot.getRushPandaStrat().stratThunderstorm());
    }
}