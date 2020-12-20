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
    PlayerInteraction playerInteraction;

    @BeforeEach
    void Setup() {
        game = new Game();
        playerInteraction = game.getGameInteraction();
    }

    @Test
    void botDrawCanalLessStaminaAndAddCanalToInventory() throws OutOfResourcesException, RulesViolationException {
        assertEquals(0,game.getPlayerData().getInventory().getInventoryCanal().size());
        assertEquals(27, playerInteraction.getResourceSize(ResourceType.CANAL));
        assertEquals(2,game.getTemporaryInventory().getStamina());

        playerInteraction.drawCanal();

        assertEquals(1,game.getPlayerData().getInventory().getInventoryCanal().size());
        assertEquals(26, playerInteraction.getResourceSize(ResourceType.CANAL));
        assertEquals(1,game.getTemporaryInventory().getStamina());
    }

    @Test
    void botDrawTooMuchCanal() throws OutOfResourcesException {
        for (int i = 0; i < 27; i++) {
            game.getResource().drawCanal();
        }
        assertThrows(OutOfResourcesException.class,() -> playerInteraction.drawCanal());
    }

    @Test
    void botDrawTwoCanalInTheSameRound() throws OutOfResourcesException, RulesViolationException {
        playerInteraction.drawCanal();
        assertThrows(RulesViolationException.class,() -> playerInteraction.drawCanal());
    }

    @Test
    void botDrawParcelMissionLessStaminaAndAddMissionToInventory() throws OutOfResourcesException, RulesViolationException {
        assertEquals(0, playerInteraction.getInventoryMissions().size());
        assertEquals(0, playerInteraction.getInventoryParcelMissions().size());
        assertEquals(15, playerInteraction.getResourceSize(ResourceType.PARCEL_MISSION));
        assertEquals(2,game.getTemporaryInventory().getStamina());

        playerInteraction.drawMission(MissionType.PARCEL);

        assertEquals(1, playerInteraction.getInventoryMissions().size());
        assertEquals(1, playerInteraction.getInventoryParcelMissions().size());
        assertEquals(14, playerInteraction.getResourceSize(ResourceType.PARCEL_MISSION));
        assertEquals(1,game.getTemporaryInventory().getStamina());
    }

    @Test
    void botDrawTooMuchParcelMission() throws OutOfResourcesException {
        for (int i = 0; i < 15; i++) {
            game.getResource().drawMission(MissionType.PARCEL);
        }
        assertThrows(OutOfResourcesException.class,() -> playerInteraction.drawMission(MissionType.PARCEL));
    }

    @Test
    void botDrawTwoParcelMissionInTheSameRound() throws OutOfResourcesException, RulesViolationException {
        playerInteraction.drawMission(MissionType.PARCEL);
        assertThrows(RulesViolationException.class,() -> playerInteraction.drawMission(MissionType.PARCEL));
    }

    @Test
    void botDrawPandaMissionLessStaminaAndAddMissionToInventory() throws OutOfResourcesException, RulesViolationException {
        assertEquals(0, playerInteraction.getInventoryMissions().size());
        assertEquals(0, playerInteraction.getInventoryPandaMissions().size());
        assertEquals(15, playerInteraction.getResourceSize(ResourceType.PANDA_MISSION));
        assertEquals(2,game.getTemporaryInventory().getStamina());

        playerInteraction.drawMission(MissionType.PANDA);

        assertEquals(1, playerInteraction.getInventoryMissions().size());
        assertEquals(1, playerInteraction.getInventoryPandaMissions().size());
        assertEquals(14, playerInteraction.getResourceSize(ResourceType.PANDA_MISSION));
        assertEquals(1,game.getTemporaryInventory().getStamina());
    }

    @Test
    void botDrawTooMuchPandaMission() throws OutOfResourcesException {
        for (int i = 0; i < 15; i++) {
            game.getResource().drawMission(MissionType.PANDA);
        }
        assertThrows(OutOfResourcesException.class,() -> playerInteraction.drawMission(MissionType.PANDA));
    }

    @Test
    void botDrawTwoPandaMissionInTheSameRound() throws OutOfResourcesException, RulesViolationException {
        playerInteraction.drawMission(MissionType.PANDA);
        assertThrows(RulesViolationException.class,() -> playerInteraction.drawMission(MissionType.PANDA));
    }

    @Test
    void botDrawPeasantMissionLessStaminaAndAddMissionToInventory() throws OutOfResourcesException, RulesViolationException {
        assertEquals(0, playerInteraction.getInventoryMissions().size());
        assertEquals(0, playerInteraction.getInventoryPeasantMissions().size());
        assertEquals(15, playerInteraction.getResourceSize(ResourceType.PEASANT_MISSION));
        assertEquals(2,game.getTemporaryInventory().getStamina());

        playerInteraction.drawMission(MissionType.PEASANT);

        assertEquals(1, playerInteraction.getInventoryMissions().size());
        assertEquals(1, playerInteraction.getInventoryPeasantMissions().size());
        assertEquals(14, playerInteraction.getResourceSize(ResourceType.PEASANT_MISSION));
        assertEquals(1,game.getTemporaryInventory().getStamina());
    }

    @Test
    void botDrawTooMuchPeasantMission() throws OutOfResourcesException {
        for (int i = 0; i < 15; i++) {
            game.getResource().drawMission(MissionType.PEASANT);
        }
        assertThrows(OutOfResourcesException.class,() -> playerInteraction.drawMission(MissionType.PEASANT));
    }

    @Test
    void botDrawTwoPeasantMissionInTheSameRound() throws OutOfResourcesException, RulesViolationException {
        playerInteraction.drawMission(MissionType.PEASANT);
        assertThrows(RulesViolationException.class,() -> playerInteraction.drawMission(MissionType.PEASANT));
    }

    @Test
    void botDrawAndPlaceParcelLessStaminaAndAddParcelToTemporaryInventoryAndBoard() throws OutOfResourcesException, BadCoordinateException, RulesViolationException {
        assertDoesNotThrow(() -> game.getTemporaryInventory().hasPlayedCorrectly());

        assertEquals(0,game.getTemporaryInventory().getParcelsSaved().size());
        assertEquals(32, playerInteraction.getResourceSize(ResourceType.PARCEL));
        assertEquals(2,game.getTemporaryInventory().getStamina());
        assertEquals(1, playerInteraction.getPlacedCoordinates().size());

        List<ParcelInformation> parcels = playerInteraction.drawParcels();

        assertThrows(NoSuchElementException.class,() -> game.getTemporaryInventory().hasPlayedCorrectly());

        assertEquals(3,game.getTemporaryInventory().getParcelsSaved().size());

        playerInteraction.selectParcel(parcels.get(0));

        assertThrows(NoSuchElementException.class,() -> game.getTemporaryInventory().hasPlayedCorrectly());

        assertEquals(31, playerInteraction.getResourceSize(ResourceType.PARCEL));
        assertEquals(1,game.getTemporaryInventory().getStamina());

        playerInteraction.placeParcel(new Coordinate(0,-1,1));

        assertDoesNotThrow(() -> game.getTemporaryInventory().hasPlayedCorrectly());

        assertEquals(2, playerInteraction.getPlacedCoordinates().size());
    }

    @Test
    void botDrawTooMuchParcel() throws OutOfResourcesException {
        for (int i = 0; i < 32; i++) {
            game.getResource().selectParcel(game.getResource().drawParcels().get(0));
        }
        assertThrows(OutOfResourcesException.class,() -> playerInteraction.drawParcels());
    }

    @Test
    void botDrawTwoTimesInTheSameTurn() throws OutOfResourcesException, RulesViolationException {
        playerInteraction.drawParcels();
        assertThrows(RulesViolationException.class,() -> playerInteraction.drawParcels());
    }

    @Test
    void botSelectTwoTimesInTheSameTurn() throws OutOfResourcesException, RulesViolationException {
        List<ParcelInformation> drawParcels =  playerInteraction.drawParcels();
        playerInteraction.selectParcel(drawParcels.get(0));
        assertThrows(RulesViolationException.class,() -> playerInteraction.selectParcel(drawParcels.get(1)));
    }

    @Test
    void botPlaceTwoTimesInTheSameTurn() throws OutOfResourcesException, BadCoordinateException, RulesViolationException {
        List<ParcelInformation> drawParcels =  playerInteraction.drawParcels();
        playerInteraction.selectParcel(drawParcels.get(0));
        playerInteraction.placeParcel(new Coordinate(0,-1,1));
        assertThrows(RulesViolationException.class,() -> playerInteraction.placeParcel(new Coordinate(0,1,-1)));
    }

    @Test
    void botPlaceWrongParcelAndThenGoodAndRetryToPlace() throws OutOfResourcesException, BadCoordinateException, RulesViolationException {
        playerInteraction.selectParcel(playerInteraction.drawParcels().get(0));
        assertThrows(BadCoordinateException.class,() -> playerInteraction.placeParcel(new Coordinate(0,-2,2)));
        playerInteraction.placeParcel(new Coordinate(0,-1,1));
        assertThrows(RulesViolationException.class, () -> playerInteraction.placeParcel(new Coordinate(1,-1,0)));
    }

    @Test
    void botPlaceCanal() throws OutOfResourcesException, RulesViolationException {
        game.getBoard().placeParcel(new Parcel(),new Coordinate(0,-1,1));
        game.getBoard().placeParcel(new Parcel(),new Coordinate(1,-1,0));
        playerInteraction.drawCanal();
        assertDoesNotThrow(() -> playerInteraction.placeCanal(new Coordinate(0,-1,1),new Coordinate(1,-1,0)));
    }

    @Test
    void botPlaceWrongCanalAndThenGoodAndRetryToPlace() throws OutOfResourcesException, BadCoordinateException, RulesViolationException {
        game.getBoard().placeParcel(new Parcel(),new Coordinate(0,-1,1));
        game.getBoard().placeParcel(new Parcel(),new Coordinate(1,-1,0));
        game.getBoard().placeParcel(new Parcel(),new Coordinate(1,-2,1));
        playerInteraction.drawCanal();
        game.getPlayerData().resetTemporaryInventory();
        assertThrows(BadCoordinateException.class, () -> playerInteraction.placeCanal(new Coordinate(1,-1,0),new Coordinate(1,-2,1)));
        playerInteraction.placeCanal(new Coordinate(0,-1,1),new Coordinate(1,-1,0));
        assertThrows(RulesViolationException.class, () -> playerInteraction.placeCanal(new Coordinate(1,-1,0),new Coordinate(1,-2,1)));
    }

    @Test
    void botMovePanda(){
        game.getBoard().placeParcel(new Parcel(),new Coordinate(1,-1,0));
        assertDoesNotThrow(() -> playerInteraction.moveCharacter(CharacterType.PANDA, new Coordinate(1,-1,0)));
    }

    @Test
    void botMoveWrongPandaAndThenGoodAndRetryToMove(){
        game.getBoard().placeParcel(new Parcel(),new Coordinate(0,-1,1));
        assertThrows(BadCoordinateException.class, () -> playerInteraction.moveCharacter(CharacterType.PANDA,new Coordinate(1,-1,0)));
        assertDoesNotThrow(() -> playerInteraction.moveCharacter(CharacterType.PANDA,new Coordinate(0,-1,1)));
        assertThrows(RulesViolationException.class, () -> playerInteraction.moveCharacter(CharacterType.PANDA,new Coordinate()));
    }

    @Test
    void botMovePeasant(){
        game.getBoard().placeParcel(new Parcel(),new Coordinate(1,-1,0));
        assertDoesNotThrow(() -> playerInteraction.moveCharacter(CharacterType.PEASANT, new Coordinate(1,-1,0)));
    }

    @Test
    void botMoveWrongPeasantAndThenGoodAndRetryToMove(){
        game.getBoard().placeParcel(new Parcel(),new Coordinate(0,-1,1));
        assertThrows(BadCoordinateException.class, () -> playerInteraction.moveCharacter(CharacterType.PEASANT,new Coordinate(1,-1,0)));
        assertDoesNotThrow(() -> playerInteraction.moveCharacter(CharacterType.PEASANT,new Coordinate(0,-1,1)));
        assertThrows(RulesViolationException.class, () -> playerInteraction.moveCharacter(CharacterType.PEASANT,new Coordinate()));
    }
}