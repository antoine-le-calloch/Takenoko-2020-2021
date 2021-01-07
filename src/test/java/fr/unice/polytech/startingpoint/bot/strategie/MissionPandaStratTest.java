package fr.unice.polytech.startingpoint.bot.strategie;


import fr.unice.polytech.startingpoint.game.Game;
import fr.unice.polytech.startingpoint.game.board.Coordinate;
import fr.unice.polytech.startingpoint.game.board.Parcel;
import fr.unice.polytech.startingpoint.game.mission.Mission;
import fr.unice.polytech.startingpoint.game.mission.PandaMission;
import fr.unice.polytech.startingpoint.type.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class MissionPandaStratTest {
    Game game;
    MissionPandaStrat missionPandaStrat;
    Mission missionGreenOneColor;
    Mission missionAllColor;

    @BeforeEach
    void setUp() {
        game = new Game(); // bot random
        missionPandaStrat = new MissionPandaStrat(game.getGameInteraction());
        missionGreenOneColor = new PandaMission(ColorType.GREEN, 0);
        missionAllColor = new PandaMission(ColorType.ALL_COLOR, 0);

    }

    /**
     * <h1><u>strategieMissionOneColor</u></h1>
     */


    @Test
    void strategieMissionOneColor() {
        Coordinate coordinate = new Coordinate(1, -1, 0);
        Parcel parcel = new Parcel(ColorType.GREEN, ImprovementType.NOTHING);
        game.getBoard().placeParcel(parcel, coordinate);
        assertEquals(coordinate, missionPandaStrat.strategyMovePanda(ColorType.GREEN));
    }

    @Test
    void strategieMissionOneColorNoParcel() {
        assertNull(missionPandaStrat.strategyMovePanda(ColorType.GREEN));
    }

    @Test
    void strategieMissionOneColorNoParcelWithNoBamboo() {
        Coordinate coordinate = new Coordinate(1, -1, 0);
        Parcel parcel = new Parcel(ColorType.GREEN, ImprovementType.NOTHING);
        game.getBoard().placeParcel(parcel, coordinate);
        game.getBoard().moveCharacter(CharacterType.PANDA, coordinate);
        assertNull(missionPandaStrat.strategyMovePanda(ColorType.GREEN));
    }

    @Test
    void strategieMissionOneColorNoParcelWithoutImprovementInclosure() {
        Coordinate coordinate = new Coordinate(1, -1, 0);
        Parcel parcel = new Parcel(ColorType.GREEN, ImprovementType.ENCLOSURE);
        game.getBoard().placeParcel(parcel, coordinate);
        assertNull(missionPandaStrat.strategyMovePanda(ColorType.GREEN));
    }

    @Test
    void strategieMissionOneColorNoParcelWithGoodColor() {
        Coordinate coordinate = new Coordinate(1, -1, 0);
        Parcel parcel = new Parcel(ColorType.RED, ImprovementType.NOTHING);
        game.getBoard().placeParcel(parcel, coordinate);
        assertNull(missionPandaStrat.strategyMovePanda(ColorType.GREEN));
    }

    /**
     * <h1><u>strategieMissionAllColor</u></h1>
     */

    @Test
    void strategyMissionAllColor() {
        Coordinate coordinate = new Coordinate(1, -1, 0);
        Parcel parcel = new Parcel(ColorType.GREEN, ImprovementType.NOTHING);
        game.getBoard().placeParcel(parcel, coordinate);
        assertEquals(coordinate, missionPandaStrat.strategyMovePanda(ColorType.ALL_COLOR));
    }

    @Test
    void strategyMissionAllColorNoParcel() {
        assertNull(missionPandaStrat.strategyMovePanda(ColorType.ALL_COLOR));
    }

    @Test
    void strategyMissionAllColorNoParcelWithBamboo() {
        Coordinate coordinate = new Coordinate(1, -1, 0);
        Parcel parcel = new Parcel(ColorType.GREEN, ImprovementType.NOTHING);
        game.getBoard().placeParcel(parcel, coordinate);
        game.getBoard().moveCharacter(CharacterType.PANDA, coordinate);
        assertNull(missionPandaStrat.strategyMovePanda(ColorType.ALL_COLOR));

    }

    @Test
    void strategyMissionAllColorNoParcelWithoutEnclosure() {
        Coordinate coordinate = new Coordinate(1, -1, 0);
        Parcel parcel = new Parcel(ColorType.GREEN, ImprovementType.ENCLOSURE);
        game.getBoard().placeParcel(parcel, coordinate);
        assertNull(missionPandaStrat.strategyMovePanda(ColorType.ALL_COLOR));

    }

    @Test
    void strategyMissionAllColorNoParcelWithColorThatBotDontHave() {
        game.getPlayerData().addBamboo(ColorType.GREEN);
        Coordinate coordinate = new Coordinate(1, -1, 0);
        Parcel parcel = new Parcel(ColorType.GREEN, ImprovementType.NOTHING);
        game.getBoard().placeParcel(parcel, coordinate);
        assertNull(missionPandaStrat.strategyMovePanda(ColorType.ALL_COLOR));
    }

    @Test
    void coordWhereMovePanda_FirstParcelWithBamboo() {
        Coordinate coordParcel1 = new Coordinate(1, -1, 0);//parcel entre 2-4h
        Coordinate coordParcel2 = new Coordinate(0, -1, 1);//parcel entre 4-6h
        Coordinate coordParcel3 = new Coordinate(1, -2, 1);//parcel a 4h éloigné de 1
        Coordinate coordParcel4 = new Coordinate(0, -2, 2);//parcel a 5h éloigné de 1
        game.getBoard().placeParcel(new Parcel(ColorType.GREEN), coordParcel1);//place la parcel (un bamboo pousse)
        game.getBoard().placeParcel(new Parcel(ColorType.GREEN), coordParcel2);//place la parcel (un bamboo pousse)
        game.getBoard().placeParcel(new Parcel(ColorType.GREEN), coordParcel3);//place la parcel (aucun bamboo pour car éloigné du centre donc pas irrigé)
        game.getBoard().placeParcel(new Parcel(ColorType.GREEN), coordParcel4);//place la parcel (aucun bamboo pour car éloigné du centre donc pas irrigé)
        game.getPlayerData().addMission(new PandaMission(ColorType.GREEN, 1));
        assertEquals(coordParcel1, missionPandaStrat.strategyMovePanda(ColorType.ALL_COLOR));
    }


    /**
     * <h1><u>nbMoveOneColor</u></h1>
     */

    @Test
    void nbMoveOneColorColorInInvetoryAndParcelPlaced() {
        game.getPlayerData().addBamboo(ColorType.GREEN);

        Coordinate coordinate = new Coordinate(1, -1, 0);
        Parcel parcel = new Parcel(ColorType.GREEN, ImprovementType.NOTHING);
        game.getBoard().placeParcel(parcel, coordinate); //place une parcelle

        assertEquals(1, missionPandaStrat.howManyMoveToDoMission(missionGreenOneColor));
    }

    @Test
    void nbMoveOneColorColorInInvetory() {
        game.getPlayerData().addBamboo(ColorType.GREEN);
        assertEquals(-1, missionPandaStrat.howManyMoveToDoMission(missionGreenOneColor));
    }

    @Test
    void nbMoveOneColorColorParcelPlaced() {
        Coordinate coordinate = new Coordinate(1, -1, 0);
        Parcel parcel = new Parcel(ColorType.GREEN, ImprovementType.NOTHING);
        game.getBoard().placeParcel(parcel, coordinate); //place une parcelle

        assertEquals(3, missionPandaStrat.howManyMoveToDoMission(missionGreenOneColor));
    }

    @Test
    void nbMoveOneColorColorNoBambooInInventoryNoParcelPlaced() {
        assertEquals(-1, missionPandaStrat.howManyMoveToDoMission(missionGreenOneColor));
    }

    /**
     * <h1><u>nbMoveAllColor</u></h1>
     */

    @Test
    void nbMoveAllColorOneBambooInInventory() {
        game.getPlayerData().addBamboo(ColorType.GREEN);
        assertEquals(-1, missionPandaStrat.howManyMoveToDoMission(missionAllColor));
    }

    @Test
    void nbMoveAllColorTwoBambooInInventory() {
        game.getPlayerData().addBamboo(ColorType.GREEN);
        game.getPlayerData().addBamboo(ColorType.RED);
        assertEquals(-1, missionPandaStrat.howManyMoveToDoMission(missionAllColor));
    }

    @Test
    void nbMoveAllColorOneBambooInInventoryOneParcelPlaced() {
        game.getPlayerData().addBamboo(ColorType.GREEN);

        Coordinate coordinate = new Coordinate(1, -1, 0);
        Parcel parcel = new Parcel(ColorType.RED, ImprovementType.NOTHING);
        game.getBoard().placeParcel(parcel, coordinate); //place une parcelle

        assertEquals(4, missionPandaStrat.howManyMoveToDoMission(missionAllColor));
    }

    @Test
    void nbMoveAllColorOneBambooInInventoryOneParcelPlacedSameColor() {
        game.getPlayerData().addBamboo(ColorType.GREEN);

        Coordinate coordinate = new Coordinate(1, -1, 0);
        Parcel parcel = new Parcel(ColorType.GREEN, ImprovementType.NOTHING);
        game.getBoard().placeParcel(parcel, coordinate); //place une parcelle

        assertEquals(-1, missionPandaStrat.howManyMoveToDoMission(missionAllColor));
    }

    /**
     * <h1><u>nbMoveAllColor</u></h1>
     */

    @Test
    void isAlreadyFinishedIsFinishMissionOneColor() {
        game.getPlayerData().addBamboo(ColorType.GREEN);
        game.getPlayerData().addBamboo(ColorType.GREEN);
        assertTrue(missionPandaStrat.isAlreadyFinished(missionGreenOneColor));
    }

    @Test
    void isAlreadyFinishedTooManyBambooMissionOneColor() {
        game.getPlayerData().addBamboo(ColorType.GREEN);
        game.getPlayerData().addBamboo(ColorType.GREEN);
        game.getPlayerData().addBamboo(ColorType.GREEN);
        assertTrue(missionPandaStrat.isAlreadyFinished(missionGreenOneColor));
    }

    @Test
    void isAlreadyFinishedNotEnoughBambooMissionOneColor() {
        game.getPlayerData().addBamboo(ColorType.GREEN);
        assertFalse(missionPandaStrat.isAlreadyFinished(missionGreenOneColor));
    }

    @Test
    void isAlreadyFinishedNotGoodColorMissionOneColor() {
        game.getPlayerData().addBamboo(ColorType.RED);
        game.getPlayerData().addBamboo(ColorType.RED);
        assertFalse(missionPandaStrat.isAlreadyFinished(missionGreenOneColor));
    }

    @Test
    void isAlreadyFinishedIsFinishMissionAllColor() {
        game.getPlayerData().addBamboo(ColorType.GREEN);
        game.getPlayerData().addBamboo(ColorType.RED);
        game.getPlayerData().addBamboo(ColorType.YELLOW);
        assertTrue(missionPandaStrat.isAlreadyFinished(missionAllColor));
    }

    @Test
    void isAlreadyFinishedTooManyBambooMissionAllColor() {
        game.getPlayerData().addBamboo(ColorType.GREEN);
        game.getPlayerData().addBamboo(ColorType.GREEN);
        game.getPlayerData().addBamboo(ColorType.RED);
        game.getPlayerData().addBamboo(ColorType.YELLOW);
        assertTrue(missionPandaStrat.isAlreadyFinished(missionAllColor));
    }

    @Test
    void isAlreadyFinishedNotEnoughBambooMissionAllColor() {
        game.getPlayerData().addBamboo(ColorType.GREEN);
        game.getPlayerData().addBamboo(ColorType.RED);
        assertFalse(missionPandaStrat.isAlreadyFinished(missionAllColor));
    }


    /**
     * <h1><u>isFinishedInOneTurn</u></h1>
     */

    @Test
    void isFinishedInOneTurnOneBambooInInventoryPlacedParcelWithGoodColor() {
        game.getPlayerData().addBamboo(ColorType.GREEN);

        Coordinate coordinate = new Coordinate(1, -1, 0);
        Parcel parcel = new Parcel(ColorType.GREEN, ImprovementType.NOTHING);
        game.getBoard().placeParcel(parcel, coordinate); //place une parcelle

        assertTrue(missionPandaStrat.isFinishedInOneTurn(missionGreenOneColor));
    }

    @Test
    void isFinishedInOneTurnOneBambooInInventoryPlacedParcelWithAllColor() {
        game.getPlayerData().addBamboo(ColorType.YELLOW);
        game.getPlayerData().addBamboo(ColorType.RED);

        Coordinate coordinate = new Coordinate(1, -1, 0);
        Parcel parcel = new Parcel(ColorType.GREEN, ImprovementType.NOTHING);
        game.getBoard().placeParcel(parcel, coordinate); //place une parcelle

        assertTrue(missionPandaStrat.isFinishedInOneTurn(missionAllColor));
    }

    @Test
    void isFinishedInOneTurnNoParcel() {
        assertFalse(missionPandaStrat.isFinishedInOneTurn(missionGreenOneColor));
    }

    @Test
    void isFinishedInOneBambooNoParcel() {
        game.getPlayerData().addBamboo(ColorType.GREEN);
        assertFalse(missionPandaStrat.isFinishedInOneTurn(missionGreenOneColor));
    }

    @Test
    void isFinishedInOneTurnAlreadyFinishMissionOneColor() {
        game.getPlayerData().addBamboo(ColorType.GREEN);
        game.getPlayerData().addBamboo(ColorType.GREEN);
        assertFalse(missionPandaStrat.isFinishedInOneTurn(missionGreenOneColor));
    }

    @Test
    void isFinishedInOneTurnAlreadyFinishMissionAllColor() {
        game.getPlayerData().addBamboo(ColorType.GREEN);
        game.getPlayerData().addBamboo(ColorType.RED);
        game.getPlayerData().addBamboo(ColorType.YELLOW);
        assertFalse(missionPandaStrat.isFinishedInOneTurn(missionAllColor));
    }


    /**
     * <h1><u>strategyMovePeasant</u></h1>
     */

    @Test
    void strategyMovePeasantGoodColor(){
        Coordinate coordinate = new Coordinate(1, -1, 0);
        Parcel parcel = new Parcel(ColorType.GREEN, ImprovementType.NOTHING);
        game.getBoard().placeParcel(parcel, coordinate);

        assertEquals(coordinate,missionPandaStrat.strategyMovePeasant(ColorType.GREEN));
    }

    @Test
    void strategyMovePeasantNotGoodColorButOneParcel(){
        Coordinate coordinate = new Coordinate(1, -1, 0);
        Parcel parcel = new Parcel(ColorType.RED, ImprovementType.NOTHING);
        game.getBoard().placeParcel(parcel, coordinate);

        assertEquals(coordinate,missionPandaStrat.strategyMovePeasant(ColorType.GREEN));
    }

    @Test
    void strategyMovePeasantTwoColors(){
        Coordinate coordinate = new Coordinate(1, -1, 0);
        Parcel parcel = new Parcel(ColorType.GREEN, ImprovementType.NOTHING);
        game.getBoard().placeParcel(parcel, coordinate);

        Coordinate coordinate2 = new Coordinate(1, -1, 0);
        Parcel parcel2 = new Parcel(ColorType.RED, ImprovementType.NOTHING);
        game.getBoard().placeParcel(parcel2, coordinate2);

        assertEquals(coordinate2,missionPandaStrat.strategyMovePeasant(ColorType.GREEN));
    }


    /**
     * <h1><u>strategyPlaceCanal</u></h1>
     */

    @Test
    void strategyPlaceCanal(){


    }

/*
    public void strategyPlaceCanal() {
        gameInteraction.drawCanal();
        if(!possibleCoordinatesCanal().isEmpty()){
            Coordinate[] coordinates = possibleCoordinatesCanal().get(0);
            gameInteraction.placeCanal(coordinates[0],coordinates[1]);
        }
    }
*/

}



