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

class GameInteractionTest {
    Game game;
    GameInteraction gameInteraction;

    @BeforeEach
    void Setup() {
        game = new Game();
        gameInteraction = game.getGameInteraction();
    }

    @Test
    void botDrawCanalLessStaminaAndAddCanalToInventory() {
        assertEquals(0,game.getPlayerData().getInventory().getInventoryCanal().size());
        assertEquals(27, gameInteraction.getResourceSize(ResourceType.CANAL));
        assertEquals(2,game.getTemporaryInventory().getStamina());

        gameInteraction.drawCanal();

        assertEquals(1,game.getPlayerData().getInventory().getInventoryCanal().size());
        assertEquals(26, gameInteraction.getResourceSize(ResourceType.CANAL));
        assertEquals(1,game.getTemporaryInventory().getStamina());
    }

    @Test
    void botDrawTooMuchCanal() {
        for (int i = 0; i < 27; i++) {
            game.getResource().drawCanal();
        }
        assertThrows(OutOfResourcesException.class,() -> gameInteraction.drawCanal());
    }

    @Test
    void botDrawTwoCanalInTheSameRound() {
        gameInteraction.drawCanal();
        assertThrows(RulesViolationException.class,() -> gameInteraction.drawCanal());
    }

    @Test
    void botDrawParcelMissionLessStaminaAndAddMissionToInventory() {
        assertEquals(0, gameInteraction.getInventoryParcelMissions().size());
        assertEquals(15, gameInteraction.getResourceSize(ResourceType.PARCEL_MISSION));
        assertEquals(2,game.getTemporaryInventory().getStamina());

        gameInteraction.drawMission(MissionType.PARCEL);

        assertEquals(1, gameInteraction.getInventoryParcelMissions().size());
        assertEquals(14, gameInteraction.getResourceSize(ResourceType.PARCEL_MISSION));
        assertEquals(1,game.getTemporaryInventory().getStamina());
    }

    @Test
    void botDrawTooMuchParcelMission() {
        for (int i = 0; i < 15; i++) {
            game.getResource().drawParcelMission();
        }
        assertThrows(OutOfResourcesException.class,() -> gameInteraction.drawMission(MissionType.PARCEL));
    }

    @Test
    void botDrawTwoParcelMissionInTheSameRound() {
        gameInteraction.drawMission(MissionType.PARCEL);
        assertThrows(RulesViolationException.class,() -> gameInteraction.drawMission(MissionType.PARCEL));
    }

    @Test
    void botDrawPandaMissionLessStaminaAndAddMissionToInventory() {
        assertEquals(0, gameInteraction.getInventoryPandaMissions().size());
        assertEquals(15, gameInteraction.getResourceSize(ResourceType.PANDA_MISSION));
        assertEquals(2,game.getTemporaryInventory().getStamina());

        gameInteraction.drawMission(MissionType.PANDA);

        assertEquals(1, gameInteraction.getInventoryPandaMissions().size());
        assertEquals(14, gameInteraction.getResourceSize(ResourceType.PANDA_MISSION));
        assertEquals(1,game.getTemporaryInventory().getStamina());
    }

    @Test
    void botDrawTooMuchPandaMission() {
        for (int i = 0; i < 15; i++) {
            game.getResource().drawPandaMission();
        }
        assertThrows(OutOfResourcesException.class,() -> gameInteraction.drawMission(MissionType.PANDA));
    }

    @Test
    void botDrawTwoPandaMissionInTheSameRound() {
        gameInteraction.drawMission(MissionType.PANDA);
        assertThrows(RulesViolationException.class,() -> gameInteraction.drawMission(MissionType.PANDA));
    }

    @Test
    void botDrawPeasantMissionLessStaminaAndAddMissionToInventory() {
        assertEquals(0, gameInteraction.getInventoryPeasantMissions().size());
        assertEquals(15, gameInteraction.getResourceSize(ResourceType.PEASANT_MISSION));
        assertEquals(2,game.getTemporaryInventory().getStamina());

        gameInteraction.drawMission(MissionType.PEASANT);

        assertEquals(1, gameInteraction.getInventoryPeasantMissions().size());
        assertEquals(14, gameInteraction.getResourceSize(ResourceType.PEASANT_MISSION));
        assertEquals(1,game.getTemporaryInventory().getStamina());
    }

    @Test
    void botDrawTooMuchPeasantMission() {
        for (int i = 0; i < 15; i++) {
            game.getResource().drawPeasantMission();
        }
        assertThrows(OutOfResourcesException.class,() -> gameInteraction.drawMission(MissionType.PEASANT));
    }

    @Test
    void botDrawTwoPeasantMissionInTheSameRound() {
        gameInteraction.drawMission(MissionType.PEASANT);
        assertThrows(RulesViolationException.class,() -> gameInteraction.drawMission(MissionType.PEASANT));
    }

    @Test
    void botDrawAndPlaceParcelLessStaminaAndAddParcelToTemporaryInventoryAndBoard() {
        assertDoesNotThrow(() -> game.getTemporaryInventory().hasPlayedCorrectly());

        assertEquals(0,game.getTemporaryInventory().getParcelsSaved().size());
        assertEquals(27, gameInteraction.getResourceSize(ResourceType.PARCEL));
        assertEquals(2,game.getTemporaryInventory().getStamina());
        assertEquals(1, gameInteraction.getPlacedCoordinates().size());

        List<ParcelInformation> parcels = gameInteraction.drawParcels();

        assertThrows(NoSuchElementException.class,() -> game.getTemporaryInventory().hasPlayedCorrectly());

        assertEquals(3,game.getTemporaryInventory().getParcelsSaved().size());

        gameInteraction.selectParcel(parcels.get(0));

        assertThrows(NoSuchElementException.class,() -> game.getTemporaryInventory().hasPlayedCorrectly());

        assertEquals(26, gameInteraction.getResourceSize(ResourceType.PARCEL));
        assertEquals(1,game.getTemporaryInventory().getStamina());

        gameInteraction.placeParcel(new Coordinate(0,-1,1));

        assertDoesNotThrow(() -> game.getTemporaryInventory().hasPlayedCorrectly());

        assertEquals(2, gameInteraction.getPlacedCoordinates().size());
    }

    @Test
    void botDrawTooMuchParcel() {
        for (int i = 0; i < 27; i++) {
            game.getResource().selectParcel(game.getResource().drawParcels().get(0));
        }
        assertThrows(OutOfResourcesException.class,() -> gameInteraction.drawParcels());
    }

    @Test
    void botDrawTwoTimesInTheSameTurn() {
        gameInteraction.drawParcels();
        assertThrows(RulesViolationException.class,() -> gameInteraction.drawParcels());
    }

    @Test
    void botSelectWithoutDrawing() {
        assertThrows(RulesViolationException.class,() -> gameInteraction.selectParcel(new ParcelInformation()));
    }

    @Test
    void botDrawWrongParcelInformation() {
        gameInteraction.drawParcels();
        assertThrows(RulesViolationException.class,() -> gameInteraction.selectParcel(new ParcelInformation()));
    }

    @Test
    void botSelectTwoTimesInTheSameTurn() {
        List<ParcelInformation> drawParcels =  gameInteraction.drawParcels();
        gameInteraction.selectParcel(drawParcels.get(0));
        assertThrows(RulesViolationException.class,() -> gameInteraction.selectParcel(drawParcels.get(1)));
    }

    @Test
    void botPlaceWithoutDrawing() {
        assertThrows(RulesViolationException.class,() -> gameInteraction.placeParcel(new Coordinate(1,-1,0)));
    }

    @Test
    void botPlaceWithoutSelecting() {
        gameInteraction.drawParcels();
        assertThrows(RulesViolationException.class,() -> gameInteraction.placeParcel(new Coordinate(1,-1,0)));
    }

    @Test
    void botPlaceTwoTimesInTheSameTurn() {
        List<ParcelInformation> drawParcels =  gameInteraction.drawParcels();
        gameInteraction.selectParcel(drawParcels.get(0));
        gameInteraction.placeParcel(new Coordinate(0,-1,1));
        assertThrows(RulesViolationException.class,() -> gameInteraction.placeParcel(new Coordinate(0,1,-1)));
    }

    @Test
    void botPlaceWrongParcelAndThenGoodAndRetryToPlace() {
        gameInteraction.selectParcel(gameInteraction.drawParcels().get(0));
        assertThrows(BadCoordinateException.class,() -> gameInteraction.placeParcel(new Coordinate(0,-2,2)));
        gameInteraction.placeParcel(new Coordinate(0,-1,1));
        assertThrows(RulesViolationException.class, () -> gameInteraction.placeParcel(new Coordinate(1,-1,0)));
    }

    @Test
    void botPlaceCanal() {
        game.getBoard().placeParcel(new Parcel(),new Coordinate(0,-1,1));
        game.getBoard().placeParcel(new Parcel(),new Coordinate(1,-1,0));
        gameInteraction.drawCanal();
        assertDoesNotThrow(() -> gameInteraction.placeCanal(new Coordinate(0,-1,1),new Coordinate(1,-1,0)));
    }

    @Test
    void botPlaceWrongCanalAndThenGoodAndRetryToPlace() {
        game.getBoard().placeParcel(new Parcel(),new Coordinate(0,-1,1));
        game.getBoard().placeParcel(new Parcel(),new Coordinate(1,-1,0));
        game.getBoard().placeParcel(new Parcel(),new Coordinate(1,-2,1));
        gameInteraction.drawCanal();
        game.getPlayerData().resetTemporaryInventory(WeatherType.NO_WEATHER);
        assertThrows(BadCoordinateException.class, () -> gameInteraction.placeCanal(new Coordinate(1,-1,0),new Coordinate(1,-2,1)));
        gameInteraction.placeCanal(new Coordinate(0,-1,1),new Coordinate(1,-1,0));
        assertThrows(RulesViolationException.class, () -> gameInteraction.placeCanal(new Coordinate(1,-1,0),new Coordinate(1,-2,1)));
    }

    @Test
    void botMovePanda(){
        game.getBoard().placeParcel(new Parcel(),new Coordinate(1,-1,0));
        assertDoesNotThrow(() -> gameInteraction.moveCharacter(CharacterType.PANDA, new Coordinate(1,-1,0)));
    }

    @Test
    void botMoveWrongPandaAndThenGoodAndRetryToMove(){
        game.getBoard().placeParcel(new Parcel(),new Coordinate(0,-1,1));
        assertThrows(BadCoordinateException.class, () -> gameInteraction.moveCharacter(CharacterType.PANDA,new Coordinate(1,-1,0)));
        assertDoesNotThrow(() -> gameInteraction.moveCharacter(CharacterType.PANDA,new Coordinate(0,-1,1)));
        assertThrows(RulesViolationException.class, () -> gameInteraction.moveCharacter(CharacterType.PANDA,new Coordinate()));
    }

    @Test
    void botMovePeasant(){
        game.getBoard().placeParcel(new Parcel(),new Coordinate(1,-1,0));
        assertDoesNotThrow(() -> gameInteraction.moveCharacter(CharacterType.PEASANT, new Coordinate(1,-1,0)));
    }

    @Test
    void botMoveWrongPeasantAndThenGoodAndRetryToMove(){
        game.getBoard().placeParcel(new Parcel(),new Coordinate(0,-1,1));
        assertThrows(BadCoordinateException.class, () -> gameInteraction.moveCharacter(CharacterType.PEASANT,new Coordinate(1,-1,0)));
        assertDoesNotThrow(() -> gameInteraction.moveCharacter(CharacterType.PEASANT,new Coordinate(0,-1,1)));
        assertThrows(RulesViolationException.class, () -> gameInteraction.moveCharacter(CharacterType.PEASANT,new Coordinate()));
    }

    @Test
    void placedParcel(){
        game.getBoard().placeParcel(new Parcel(),new Coordinate(1,-1,0));
        game.getBoard().placeParcel(new Parcel(),new Coordinate(1,-2,1));
        assertTrue(gameInteraction.isPlacedParcel(new Coordinate()));
        assertTrue(gameInteraction.isPlacedParcel(new Coordinate(1,-1,0)));
        assertTrue(gameInteraction.isPlacedParcel(new Coordinate(1,-2,1)));
    }

    @Test
    void notPlacedParcel(){
        assertFalse(gameInteraction.isPlacedParcel(new Coordinate(1,-1,0)));
        assertFalse(gameInteraction.isPlacedParcel(new Coordinate(1,-2,1)));
    }

    @Test
    void irrigatedAndPlacedParcel(){
        game.getBoard().placeParcel(new Parcel(),new Coordinate(1,-1,0));
        game.getBoard().placeParcel(new Parcel(),new Coordinate(0,-1,1));
        game.getBoard().placeParcel(new Parcel(),new Coordinate(1,-2,1));
        game.getBoard().placeCanal(new Canal(),new Coordinate(1,-1,0),new Coordinate(0,-1,1));
        game.getBoard().placeCanal(new Canal(),new Coordinate(1,-2,1),new Coordinate(0,-1,1));
        assertTrue(gameInteraction.isIrrigatedParcel(new Coordinate(1,-1,0)));
        assertTrue(gameInteraction.isIrrigatedParcel(new Coordinate(0,-1,1)));
        assertTrue(gameInteraction.isIrrigatedParcel(new Coordinate(1,-2,1)));
    }

    @Test
    void notIrrigatedAndPlacedParcel(){
        game.getBoard().placeParcel(new Parcel(),new Coordinate(1,-2,1));
        assertFalse(gameInteraction.isIrrigatedParcel(new Coordinate(1,-1,0)));
        assertFalse(gameInteraction.isIrrigatedParcel(new Coordinate(1,-2,1)));
    }

    @Test
    void placedParcelInformation(){
        game.getBoard().placeParcel(new Parcel(ImprovementType.WATERSHED),new Coordinate(1,-1,0));
        game.getBoard().placeParcel(new Parcel(ColorType.RED),new Coordinate(0,-1,1));
        game.getBoard().placeParcel(new Parcel(ColorType.GREEN,ImprovementType.ENCLOSURE),new Coordinate(1,-2,1));
        assertEquals(new ParcelInformation(), gameInteraction.getPlacedParcelInformation(new Coordinate()));
        assertEquals(new ParcelInformation(ImprovementType.WATERSHED), gameInteraction.getPlacedParcelInformation(new Coordinate(1,-1,0)));
        assertEquals(new ParcelInformation(ColorType.RED), gameInteraction.getPlacedParcelInformation(new Coordinate(0,-1,1)));
        assertEquals(new ParcelInformation(ColorType.GREEN,ImprovementType.ENCLOSURE), gameInteraction.getPlacedParcelInformation(new Coordinate(1,-2,1)));
    }

    @Test
    void testGetPlacedCoordinatesByColor(){
        game.getBoard().placeParcel(new Parcel(ColorType.RED),new Coordinate(0,-1,1));
        game.getBoard().placeParcel(new Parcel(ColorType.GREEN),new Coordinate(1,-1,0));
        ArrayList<Coordinate> placedCoordinatesByRedColor= (ArrayList<Coordinate>) gameInteraction.getPlacedCoordinatesByColor(ColorType.RED);
        ArrayList<Coordinate> placedCoordinatesByBlueColor= (ArrayList<Coordinate>) gameInteraction.getPlacedCoordinatesByColor(ColorType.GREEN);
        assertEquals(new Coordinate(0,-1,1),placedCoordinatesByRedColor.get(0));
        assertEquals(new Coordinate(1,-1,0),placedCoordinatesByBlueColor.get(0));
        assertNotEquals(new Coordinate(1,-1,0),placedCoordinatesByRedColor.get(0));
        assertNotEquals(new Coordinate(0,-1,1),placedCoordinatesByBlueColor.get(0));
    }
}