package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.exception.BadCoordinateException;
import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
import fr.unice.polytech.startingpoint.exception.RulesViolationException;
import fr.unice.polytech.startingpoint.type.*;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
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

class PlayerInteractionTest {
    Game game;
    PlayerInteraction playerInteraction;

    @BeforeEach
    void Setup() {
        game = new Game();
        playerInteraction = game.getGameInteraction();
    }

    @Test
    void botDrawCanalLessStaminaAndAddCanalToInventory() {
        assertEquals(0,game.getPlayerData().getInventory().getInventoryCanal().size());
        assertEquals(27, playerInteraction.getResourceSize(ResourceType.CANAL));
        assertEquals(2,game.getTemporaryInventory().getStamina());

        playerInteraction.drawCanal();

        assertEquals(1,game.getPlayerData().getInventory().getInventoryCanal().size());
        assertEquals(26, playerInteraction.getResourceSize(ResourceType.CANAL));
        assertEquals(1,game.getTemporaryInventory().getStamina());
    }

    @Test
    void botDrawTooMuchCanal() {
        for (int i = 0; i < 27; i++) {
            game.getResource().drawCanal();
        }
        assertThrows(OutOfResourcesException.class,() -> playerInteraction.drawCanal());
    }

    @Test
    void botDrawTwoCanalInTheSameRound() {
        playerInteraction.drawCanal();
        assertThrows(RulesViolationException.class,() -> playerInteraction.drawCanal());
    }

    @Test
    void botDrawParcelMissionLessStaminaAndAddMissionToInventory() {
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
    void botDrawTooMuchParcelMission() {
        for (int i = 0; i < 15; i++) {
            game.getResource().drawMission(MissionType.PARCEL);
        }
        assertThrows(OutOfResourcesException.class,() -> playerInteraction.drawMission(MissionType.PARCEL));
    }

    @Test
    void botDrawTwoParcelMissionInTheSameRound() {
        playerInteraction.drawMission(MissionType.PARCEL);
        assertThrows(RulesViolationException.class,() -> playerInteraction.drawMission(MissionType.PARCEL));
    }

    @Test
    void botDrawPandaMissionLessStaminaAndAddMissionToInventory() {
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
    void botDrawTooMuchPandaMission() {
        for (int i = 0; i < 15; i++) {
            game.getResource().drawMission(MissionType.PANDA);
        }
        assertThrows(OutOfResourcesException.class,() -> playerInteraction.drawMission(MissionType.PANDA));
    }

    @Test
    void botDrawTwoPandaMissionInTheSameRound() {
        playerInteraction.drawMission(MissionType.PANDA);
        assertThrows(RulesViolationException.class,() -> playerInteraction.drawMission(MissionType.PANDA));
    }

    @Test
    void botDrawPeasantMissionLessStaminaAndAddMissionToInventory() {
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
    void botDrawTooMuchPeasantMission() {
        for (int i = 0; i < 15; i++) {
            game.getResource().drawMission(MissionType.PEASANT);
        }
        assertThrows(OutOfResourcesException.class,() -> playerInteraction.drawMission(MissionType.PEASANT));
    }

    @Test
    void botDrawTwoPeasantMissionInTheSameRound() {
        playerInteraction.drawMission(MissionType.PEASANT);
        assertThrows(RulesViolationException.class,() -> playerInteraction.drawMission(MissionType.PEASANT));
    }

    @Test
    void botDrawAndPlaceParcelLessStaminaAndAddParcelToTemporaryInventoryAndBoard() {
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
    void botDrawTooMuchParcel() {
        for (int i = 0; i < 32; i++) {
            game.getResource().selectParcel(game.getResource().drawParcels().get(0));
        }
        assertThrows(OutOfResourcesException.class,() -> playerInteraction.drawParcels());
    }

    @Test
    void botDrawTwoTimesInTheSameTurn() {
        playerInteraction.drawParcels();
        assertThrows(RulesViolationException.class,() -> playerInteraction.drawParcels());
    }

    @Test
    void botSelectWithoutDrawing() {
        assertThrows(RulesViolationException.class,() -> playerInteraction.selectParcel(new ParcelInformation()));
    }

    @Test
    void botDrawWrongParcelInformation() {
        playerInteraction.drawParcels();
        assertThrows(RulesViolationException.class,() -> playerInteraction.selectParcel(new ParcelInformation()));
    }

    @Test
    void botSelectTwoTimesInTheSameTurn() {
        List<ParcelInformation> drawParcels =  playerInteraction.drawParcels();
        playerInteraction.selectParcel(drawParcels.get(0));
        assertThrows(RulesViolationException.class,() -> playerInteraction.selectParcel(drawParcels.get(1)));
    }

    @Test
    void botPlaceWithoutDrawing() {
        assertThrows(RulesViolationException.class,() -> playerInteraction.placeParcel(new Coordinate(1,-1,0)));
    }

    @Test
    void botPlaceWithoutSelecting() {
        playerInteraction.drawParcels();
        assertThrows(RulesViolationException.class,() -> playerInteraction.placeParcel(new Coordinate(1,-1,0)));
    }

    @Test
    void botPlaceTwoTimesInTheSameTurn() {
        List<ParcelInformation> drawParcels =  playerInteraction.drawParcels();
        playerInteraction.selectParcel(drawParcels.get(0));
        playerInteraction.placeParcel(new Coordinate(0,-1,1));
        assertThrows(RulesViolationException.class,() -> playerInteraction.placeParcel(new Coordinate(0,1,-1)));
    }

    @Test
    void botPlaceWrongParcelAndThenGoodAndRetryToPlace() {
        playerInteraction.selectParcel(playerInteraction.drawParcels().get(0));
        assertThrows(BadCoordinateException.class,() -> playerInteraction.placeParcel(new Coordinate(0,-2,2)));
        playerInteraction.placeParcel(new Coordinate(0,-1,1));
        assertThrows(RulesViolationException.class, () -> playerInteraction.placeParcel(new Coordinate(1,-1,0)));
    }

    @Test
    void botPlaceCanal() {
        game.getBoard().placeParcel(new Parcel(),new Coordinate(0,-1,1));
        game.getBoard().placeParcel(new Parcel(),new Coordinate(1,-1,0));
        playerInteraction.drawCanal();
        assertDoesNotThrow(() -> playerInteraction.placeCanal(new Coordinate(0,-1,1),new Coordinate(1,-1,0)));
    }

    @Test
    void botPlaceWrongCanalAndThenGoodAndRetryToPlace() {
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

    @Test
    void placedParcel(){
        game.getBoard().placeParcel(new Parcel(),new Coordinate(1,-1,0));
        game.getBoard().placeParcel(new Parcel(),new Coordinate(1,-2,1));
        assertTrue(playerInteraction.isPlacedParcel(new Coordinate()));
        assertTrue(playerInteraction.isPlacedParcel(new Coordinate(1,-1,0)));
        assertTrue(playerInteraction.isPlacedParcel(new Coordinate(1,-2,1)));
    }

    @Test
    void notPlacedParcel(){
        assertFalse(playerInteraction.isPlacedParcel(new Coordinate(1,-1,0)));
        assertFalse(playerInteraction.isPlacedParcel(new Coordinate(1,-2,1)));
    }

    @Test
    void irrigatedAndPlacedParcel(){
        game.getBoard().placeParcel(new Parcel(),new Coordinate(1,-1,0));
        game.getBoard().placeParcel(new Parcel(),new Coordinate(0,-1,1));
        game.getBoard().placeParcel(new Parcel(),new Coordinate(1,-2,1));
        game.getBoard().placeCanal(new Canal(),new Coordinate(1,-1,0),new Coordinate(0,-1,1));
        game.getBoard().placeCanal(new Canal(),new Coordinate(1,-2,1),new Coordinate(0,-1,1));
        assertTrue(playerInteraction.isIrrigatedParcel(new Coordinate(1,-1,0)));
        assertTrue(playerInteraction.isIrrigatedParcel(new Coordinate(0,-1,1)));
        assertTrue(playerInteraction.isIrrigatedParcel(new Coordinate(1,-2,1)));
    }

    @Test
    void notIrrigatedAndPlacedParcel(){
        game.getBoard().placeParcel(new Parcel(),new Coordinate(1,-2,1));
        assertFalse(playerInteraction.isIrrigatedParcel(new Coordinate(1,-1,0)));
        assertFalse(playerInteraction.isIrrigatedParcel(new Coordinate(1,-2,1)));
    }

    @Test
    void placedParcelInformation(){
        game.getBoard().placeParcel(new Parcel(ImprovementType.WATERSHED),new Coordinate(1,-1,0));
        game.getBoard().placeParcel(new Parcel(ColorType.RED),new Coordinate(0,-1,1));
        game.getBoard().placeParcel(new Parcel(ColorType.BLUE,ImprovementType.ENCLOSURE),new Coordinate(1,-2,1));
        assertEquals(new ParcelInformation(),playerInteraction.getPlacedParcelInformation(new Coordinate()));
        assertEquals(new ParcelInformation(ImprovementType.WATERSHED),playerInteraction.getPlacedParcelInformation(new Coordinate(1,-1,0)));
        assertEquals(new ParcelInformation(ColorType.RED),playerInteraction.getPlacedParcelInformation(new Coordinate(0,-1,1)));
        assertEquals(new ParcelInformation(ColorType.BLUE,ImprovementType.ENCLOSURE),playerInteraction.getPlacedParcelInformation(new Coordinate(1,-2,1)));
    }

    @Test
    void testGetPlacedCoordinatesByColor(){
        game.getBoard().placeParcel(new Parcel(ColorType.RED),new Coordinate(0,-1,1));
        game.getBoard().placeParcel(new Parcel(ColorType.BLUE),new Coordinate(1,-1,0));
        ArrayList<Coordinate> placedCoordinatesByRedColor= (ArrayList<Coordinate>) playerInteraction.getPlacedCoordinatesByColor(ColorType.RED);
        ArrayList<Coordinate> placedCoordinatesByBlueColor= (ArrayList<Coordinate>) playerInteraction.getPlacedCoordinatesByColor(ColorType.BLUE);
        assertEquals(new Coordinate(0,-1,1),placedCoordinatesByRedColor.get(0));
        assertEquals(new Coordinate(1,-1,0),placedCoordinatesByBlueColor.get(0));
        assertNotEquals(new Coordinate(1,-1,0),placedCoordinatesByRedColor.get(0));
        assertNotEquals(new Coordinate(0,-1,1),placedCoordinatesByBlueColor.get(0));
    }
}