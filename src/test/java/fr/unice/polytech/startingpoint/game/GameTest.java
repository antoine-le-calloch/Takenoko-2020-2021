package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.exception.BadCoordinateException;
import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
import fr.unice.polytech.startingpoint.exception.RulesViolationException;
import fr.unice.polytech.startingpoint.type.*;

import org.junit.jupiter.api.*;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests unitaires
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

class GameTest {
    Game game;

    @BeforeEach
    void Setup() {
        game = new Game(new BotType[]{BotType.RANDOM},4);
    }

    @Test
    void botDrawCanalLessStaminaAndAddCanalToInventory() throws OutOfResourcesException, RulesViolationException {
        assertEquals(0,game.getPlayerData().getInventory().getInventoryCanal().size());
        assertEquals(27,game.getResourceSize(ResourceType.CANAL));
        assertEquals(2,game.getTemporaryInventory().getStamina());

        game.drawCanal();

        assertEquals(1,game.getPlayerData().getInventory().getInventoryCanal().size());
        assertEquals(26,game.getResourceSize(ResourceType.CANAL));
        assertEquals(1,game.getTemporaryInventory().getStamina());
    }

    @Test
    void botDrawTooMuchCanal() throws OutOfResourcesException {
        for (int i = 0; i < 27; i++) {
            game.getResource().drawCanal();
        }
        assertThrows(OutOfResourcesException.class,() -> game.drawCanal());
    }

    @Test
    void botDrawTwoCanalInTheSameRound() throws OutOfResourcesException, RulesViolationException {
        game.drawCanal();
        assertThrows(RulesViolationException.class,() -> game.drawCanal());
    }

    @Test
    void botDrawParcelMissionLessStaminaAndAddMissionToInventory() throws OutOfResourcesException, RulesViolationException {
        assertEquals(0,game.getPlayerData().getInventory().getMissions().size());
        assertEquals(0,game.getPlayerData().getInventory().getParcelMissions().size());
        assertEquals(15,game.getResourceSize(ResourceType.PARCEL_MISSION));
        assertEquals(2,game.getTemporaryInventory().getStamina());

        game.drawMission(MissionType.PARCEL);

        assertEquals(1,game.getPlayerData().getInventory().getMissions().size());
        assertEquals(1,game.getPlayerData().getInventory().getParcelMissions().size());
        assertEquals(14,game.getResourceSize(ResourceType.PARCEL_MISSION));
        assertEquals(1,game.getTemporaryInventory().getStamina());
    }

    @Test
    void botDrawTooMuchParcelMission() throws OutOfResourcesException {
        for (int i = 0; i < 15; i++) {
            game.getResource().drawMission(MissionType.PARCEL);
        }
        assertThrows(OutOfResourcesException.class,() -> game.drawMission(MissionType.PARCEL));
    }

    @Test
    void botDrawTwoParcelMissionInTheSameRound() throws OutOfResourcesException, RulesViolationException {
        game.drawMission(MissionType.PARCEL);
        assertThrows(RulesViolationException.class,() -> game.drawMission(MissionType.PARCEL));
    }

    @Test
    void botDrawPandaMissionLessStaminaAndAddMissionToInventory() throws OutOfResourcesException, RulesViolationException {
        assertEquals(0,game.getPlayerData().getInventory().getMissions().size());
        assertEquals(0,game.getPlayerData().getInventory().getPandaMissions().size());
        assertEquals(15,game.getResourceSize(ResourceType.PANDA_MISSION));
        assertEquals(2,game.getTemporaryInventory().getStamina());

        game.drawMission(MissionType.PANDA);

        assertEquals(1,game.getPlayerData().getInventory().getMissions().size());
        assertEquals(1,game.getPlayerData().getInventory().getPandaMissions().size());
        assertEquals(14,game.getResourceSize(ResourceType.PANDA_MISSION));
        assertEquals(1,game.getTemporaryInventory().getStamina());
    }

    @Test
    void botDrawTooMuchPandaMission() throws OutOfResourcesException {
        for (int i = 0; i < 15; i++) {
            game.getResource().drawMission(MissionType.PANDA);
        }
        assertThrows(OutOfResourcesException.class,() -> game.drawMission(MissionType.PANDA));
    }

    @Test
    void botDrawTwoPandaMissionInTheSameRound() throws OutOfResourcesException, RulesViolationException {
        game.drawMission(MissionType.PANDA);
        assertThrows(RulesViolationException.class,() -> game.drawMission(MissionType.PANDA));
    }

    @Test
    void botDrawPeasantMissionLessStaminaAndAddMissionToInventory() throws OutOfResourcesException, RulesViolationException {
        assertEquals(0,game.getPlayerData().getInventory().getMissions().size());
        assertEquals(0,game.getPlayerData().getInventory().getPeasantMissions().size());
        assertEquals(15,game.getResourceSize(ResourceType.PEASANT_MISSION));
        assertEquals(2,game.getTemporaryInventory().getStamina());

        game.drawMission(MissionType.PEASANT);

        assertEquals(1,game.getPlayerData().getInventory().getMissions().size());
        assertEquals(1,game.getPlayerData().getInventory().getPeasantMissions().size());
        assertEquals(14,game.getResourceSize(ResourceType.PEASANT_MISSION));
        assertEquals(1,game.getTemporaryInventory().getStamina());
    }

    @Test
    void botDrawTooMuchPeasantMission() throws OutOfResourcesException {
        for (int i = 0; i < 15; i++) {
            game.getResource().drawMission(MissionType.PEASANT);
        }
        assertThrows(OutOfResourcesException.class,() -> game.drawMission(MissionType.PEASANT));
    }

    @Test
    void botDrawTwoPeasantMissionInTheSameRound() throws OutOfResourcesException, RulesViolationException {
        game.drawMission(MissionType.PEASANT);
        assertThrows(RulesViolationException.class,() -> game.drawMission(MissionType.PEASANT));
    }

    @Test
    void botDrawAndPlaceParcelLessStaminaAndAddParcelToTemporaryInventoryAndBoard() throws OutOfResourcesException, BadCoordinateException, RulesViolationException {
        assertDoesNotThrow(() -> game.getTemporaryInventory().hasPlayedCorrectly());

        assertEquals(0,game.getTemporaryInventory().getParcelsSaved().size());
        assertEquals(32,game.getResourceSize(ResourceType.PARCEL));
        assertEquals(2,game.getTemporaryInventory().getStamina());
        assertEquals(1,game.getPlacedCoordinates().size());

        List<ColorType> parcels = game.drawParcels();

        assertThrows(NoSuchElementException.class,() -> game.getTemporaryInventory().hasPlayedCorrectly());

        assertEquals(3,game.getTemporaryInventory().getParcelsSaved().size());

        game.selectParcel(parcels.get(0));

        assertThrows(NoSuchElementException.class,() -> game.getTemporaryInventory().hasPlayedCorrectly());

        assertEquals(31,game.getResourceSize(ResourceType.PARCEL));
        assertEquals(1,game.getTemporaryInventory().getStamina());

        game.placeParcel(new Coordinate(0,-1,1));

        assertDoesNotThrow(() -> game.getTemporaryInventory().hasPlayedCorrectly());

        assertEquals(2,game.getPlacedCoordinates().size());
    }

    @Test
    void botDrawTooMuchParcel() throws OutOfResourcesException {
        for (int i = 0; i < 32; i++) {
            game.getResource().selectParcel(game.getResource().drawParcels().get(0));
        }
        assertThrows(OutOfResourcesException.class,() -> game.drawParcels());
    }

    @Test
    void botDrawTwoTimesInTheSameTurn() throws OutOfResourcesException, RulesViolationException {
        game.drawParcels();
        assertThrows(RulesViolationException.class,() -> game.drawParcels());
    }

    @Test
    void botSelectTwoTimesInTheSameTurn() throws OutOfResourcesException, RulesViolationException {
        List<ColorType> drawParcels =  game.drawParcels();
        game.selectParcel(drawParcels.get(0));
        assertThrows(RulesViolationException.class,() -> game.selectParcel(drawParcels.get(1)));
    }

    @Test
    void botPlaceTwoTimesInTheSameTurn() throws OutOfResourcesException, BadCoordinateException, RulesViolationException {
        List<ColorType> drawParcels =  game.drawParcels();
        game.selectParcel(drawParcels.get(0));
        game.placeParcel(new Coordinate(0,-1,1));
        assertThrows(RulesViolationException.class,() -> game.placeParcel(new Coordinate(0,1,-1)));
    }

    @Test
    void botPlaceWrongParcelAndThenGoodAndRetryToPlace() throws OutOfResourcesException, BadCoordinateException, RulesViolationException {
        game.selectParcel(game.drawParcels().get(0));
        assertThrows(BadCoordinateException.class,() -> game.placeParcel(new Coordinate(0,-2,2)));
        game.placeParcel(new Coordinate(0,-1,1));
        assertThrows(RulesViolationException.class, () -> game.placeParcel(new Coordinate(1,-1,0)));
    }

    @Test
    void botPlaceCanal() throws OutOfResourcesException, RulesViolationException {
        game.getBoard().placeParcel(new Parcel(),new Coordinate(0,-1,1));
        game.getBoard().placeParcel(new Parcel(),new Coordinate(1,-1,0));
        game.drawCanal();
        assertDoesNotThrow(() -> game.placeCanal(new Coordinate(0,-1,1),new Coordinate(1,-1,0)));
    }

    @Test
    void botPlaceWrongCanalAndThenGoodAndRetryToPlace() throws OutOfResourcesException, BadCoordinateException, RulesViolationException {
        game.getBoard().placeParcel(new Parcel(),new Coordinate(0,-1,1));
        game.getBoard().placeParcel(new Parcel(),new Coordinate(1,-1,0));
        game.getBoard().placeParcel(new Parcel(),new Coordinate(1,-2,1));
        game.drawCanal();
        game.getTemporaryInventory().reset(2);
        assertThrows(BadCoordinateException.class, () -> game.placeCanal(new Coordinate(1,-1,0),new Coordinate(1,-2,1)));
        game.placeCanal(new Coordinate(0,-1,1),new Coordinate(1,-1,0));
        assertThrows(RulesViolationException.class, () -> game.placeCanal(new Coordinate(1,-1,0),new Coordinate(1,-2,1)));
    }

    @Test
    void botMovePanda(){
        game.getBoard().placeParcel(new Parcel(),new Coordinate(1,-1,0));
        assertDoesNotThrow(() -> game.moveCharacter(CharacterType.PANDA, new Coordinate(1,-1,0)));
    }

    @Test
    void botMoveWrongPandaAndThenGoodAndRetryToMove(){
        game.getBoard().placeParcel(new Parcel(),new Coordinate(0,-1,1));
        assertThrows(BadCoordinateException.class, () -> game.moveCharacter(CharacterType.PANDA,new Coordinate(1,-1,0)));
        assertDoesNotThrow(() -> game.moveCharacter(CharacterType.PANDA,new Coordinate(0,-1,1)));
        assertThrows(RulesViolationException.class, () -> game.moveCharacter(CharacterType.PANDA,new Coordinate()));
    }

    @Test
    void botMovePeasant(){
        game.getBoard().placeParcel(new Parcel(),new Coordinate(1,-1,0));
        assertDoesNotThrow(() -> game.moveCharacter(CharacterType.PEASANT, new Coordinate(1,-1,0)));
    }

    @Test
    void botMoveWrongPeasantAndThenGoodAndRetryToMove(){
        game.getBoard().placeParcel(new Parcel(),new Coordinate(0,-1,1));
        assertThrows(BadCoordinateException.class, () -> game.moveCharacter(CharacterType.PEASANT,new Coordinate(1,-1,0)));
        assertDoesNotThrow(() -> game.moveCharacter(CharacterType.PEASANT,new Coordinate(0,-1,1)));
        assertThrows(RulesViolationException.class, () -> game.moveCharacter(CharacterType.PEASANT,new Coordinate()));
    }
}