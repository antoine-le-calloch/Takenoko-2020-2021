package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.bot.*;
import fr.unice.polytech.startingpoint.type.ActionType;
import fr.unice.polytech.startingpoint.type.ColorType;
import fr.unice.polytech.startingpoint.type.ImprovementType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MissionPeasantStratTest {
    Board board;
    Parcel parcel1;
    Coordinate coordinate1;
    Game game;
    Bot bot;
    MissionPeasantStrat stratMissionPeasant;

    @BeforeEach
    void setUp() {
        game = new Game();
        board = game.getBoard();
        parcel1 = new Parcel(ColorType.GREEN);
        bot = new PeasantBot(game.getGameInteraction());
        coordinate1 = new Coordinate(1, -1, 0);
        stratMissionPeasant = new MissionPeasantStrat(bot);
    }
    /**
     <h2><u>Strategy Move Peasant</u></h2>

     */

    @Test
    void coordWhereMovePeasant_0parcel() {
        assertNull(stratMissionPeasant.strategyMovePeasant());//Pas de parcel, il ne bouge pas
    }

    @Test
    void noMissionSoPeasentNotMove() {
        board.placeParcel(parcel1, coordinate1);//place la parcel (un bamboo pousse)
        assertNull(stratMissionPeasant.strategyMovePeasant());
    }
    @Test
    void possibleParcelColorDifferentFromTheActualMission() {
        board.placeParcel(parcel1, coordinate1);
        game.getPlayerData().addMission(new PeasantMission(board,ColorType.RED,1, ImprovementType.NOTHING));
        assertNull(stratMissionPeasant.strategyMovePeasant());
    }

    @Test
    void missionColorSameAsPossibleParcel() {

        board.placeParcel(parcel1,coordinate1);// ;//ajoute la parcel avec 2 bamboo a la liste des parcels posée
        game.getPlayerData().addMission(new PeasantMission(board,ColorType.GREEN,1, ImprovementType.NOTHING));
        assertEquals(coordinate1,stratMissionPeasant.strategyMovePeasant());
    }

    @Test
    void movePeasant_1Parcel2Bamboo()  {
        game.getTemporaryInventory().add(ActionType.DRAW_MISSION);//empêche de piocher une mission
        board.placeParcel(parcel1, coordinate1);//pose la parcel (cela ajoute un autre bamboo)
        game.getPlayerData().addMission(new PeasantMission(board,ColorType.GREEN,1, ImprovementType.NOTHING));
        assertEquals(1,board.getPlacedParcels().get(coordinate1).getNbBamboo());
        stratMissionPeasant.stratOneTurn();//deplace le paysan sur une parcel avec plus de 1 bamboo (parcel1Bamboo), cela ajoute un bamboo
        assertEquals(2,board.getPlacedParcels().get(coordinate1).getNbBamboo());//3 bamboo sur la parcel
    }



    @Test
    void drawMission() {
        assertEquals(0, game.getGameInteraction().getInventoryPeasantMissions().size());//0 mission dans son inventaire
        stratMissionPeasant.stratOneTurn();//fait jouer le paysan(il vas piocher)
        //assertEquals(1, game.getGameInteraction().getInventoryPeasantMissions().size());//1 mission dans son inventaire
    }
}
