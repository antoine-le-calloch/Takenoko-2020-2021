package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.exception.BadMoveCharacterException;
import fr.unice.polytech.startingpoint.exception.BadPlaceCanalException;
import fr.unice.polytech.startingpoint.exception.BadPlaceParcelException;
import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
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
    Game cheatGame;

    @BeforeEach
    void Setup() {
        game = new Game(new BotType[]{BotType.Random},4);
        cheatGame = new Game();
    }

    @Test
    void botDrawCanalLessStaminaAndAddCanalToInventory() throws OutOfResourcesException, IllegalAccessException {
        assertEquals(0,game.getPlayerData().getInventory().getInventoryCanal().size());
        assertEquals(27,game.getResourceSize(ResourceType.Canal));
        assertEquals(2,game.getTemporaryInventory().getStamina());

        game.drawCanal();

        assertEquals(1,game.getPlayerData().getInventory().getInventoryCanal().size());
        assertEquals(26,game.getResourceSize(ResourceType.Canal));
        assertEquals(1,game.getTemporaryInventory().getStamina());
    }

    @Test
    void botDrawTooMuchCanal() throws OutOfResourcesException, IllegalAccessException {
        for (int i = 0; i < 27; i++) {
            cheatGame.drawCanal();
        }
        assertThrows(OutOfResourcesException.class,() -> cheatGame.drawCanal());
    }

    @Test
    void botDrawTwoCanalInTheSameRound() throws OutOfResourcesException, IllegalAccessException {
        game.drawCanal();
        assertThrows(IllegalAccessException.class,() -> game.drawCanal());
    }

    @Test
    void botDrawParcelMissionLessStaminaAndAddMissionToInventory() throws OutOfResourcesException, IllegalAccessException {
        assertEquals(0,game.getPlayerData().getInventory().getMissions().size());
        assertEquals(0,game.getPlayerData().getInventory().getParcelMissions().size());
        assertEquals(15,game.getResourceSize(ResourceType.ParcelMission));
        assertEquals(2,game.getTemporaryInventory().getStamina());

        game.drawMission(MissionType.Parcel);

        assertEquals(1,game.getPlayerData().getInventory().getMissions().size());
        assertEquals(1,game.getPlayerData().getInventory().getParcelMissions().size());
        assertEquals(14,game.getResourceSize(ResourceType.ParcelMission));
        assertEquals(1,game.getTemporaryInventory().getStamina());
    }

    @Test
    void botDrawTooMuchParcelMission() throws OutOfResourcesException, IllegalAccessException {
        for (int i = 0; i < 15; i++) {
            cheatGame.drawMission(MissionType.Parcel);
        }
        assertThrows(OutOfResourcesException.class,() -> cheatGame.drawMission(MissionType.Parcel));
    }

    @Test
    void botDrawTwoParcelMissionInTheSameRound() throws OutOfResourcesException, IllegalAccessException {
        game.drawMission(MissionType.Parcel);
        assertThrows(IllegalAccessException.class,() -> game.drawMission(MissionType.Parcel));
    }

    @Test
    void botDrawPandaMissionLessStaminaAndAddMissionToInventory() throws OutOfResourcesException, IllegalAccessException {
        assertEquals(0,game.getPlayerData().getInventory().getMissions().size());
        assertEquals(0,game.getPlayerData().getInventory().getPandaMissions().size());
        assertEquals(15,game.getResourceSize(ResourceType.PandaMission));
        assertEquals(2,game.getTemporaryInventory().getStamina());

        game.drawMission(MissionType.Panda);

        assertEquals(1,game.getPlayerData().getInventory().getMissions().size());
        assertEquals(1,game.getPlayerData().getInventory().getPandaMissions().size());
        assertEquals(14,game.getResourceSize(ResourceType.PandaMission));
        assertEquals(1,game.getTemporaryInventory().getStamina());
    }

    @Test
    void botDrawTooMuchPandaMission() throws OutOfResourcesException, IllegalAccessException {
        for (int i = 0; i < 15; i++) {
            cheatGame.drawMission(MissionType.Panda);
        }
        assertThrows(OutOfResourcesException.class,() -> cheatGame.drawMission(MissionType.Panda));
    }

    @Test
    void botDrawTwoPandaMissionInTheSameRound() throws OutOfResourcesException, IllegalAccessException {
        game.drawMission(MissionType.Panda);
        assertThrows(IllegalAccessException.class,() -> game.drawMission(MissionType.Panda));
    }

    @Test
    void botDrawPeasantMissionLessStaminaAndAddMissionToInventory() throws OutOfResourcesException, IllegalAccessException {
        assertEquals(0,game.getPlayerData().getInventory().getMissions().size());
        assertEquals(0,game.getPlayerData().getInventory().getPeasantMissions().size());
        assertEquals(15,game.getResourceSize(ResourceType.PeasantMission));
        assertEquals(2,game.getTemporaryInventory().getStamina());

        game.drawMission(MissionType.Peasant);

        assertEquals(1,game.getPlayerData().getInventory().getMissions().size());
        assertEquals(1,game.getPlayerData().getInventory().getPeasantMissions().size());
        assertEquals(14,game.getResourceSize(ResourceType.PeasantMission));
        assertEquals(1,game.getTemporaryInventory().getStamina());
    }

    @Test
    void botDrawTooMuchPeasantMission() throws OutOfResourcesException, IllegalAccessException {
        for (int i = 0; i < 15; i++) {
            cheatGame.drawMission(MissionType.Peasant);
        }
        assertThrows(OutOfResourcesException.class,() -> cheatGame.drawMission(MissionType.Peasant));
    }

    @Test
    void botDrawTwoPeasantMissionInTheSameRound() throws OutOfResourcesException, IllegalAccessException {
        game.drawMission(MissionType.Peasant);
        assertThrows(IllegalAccessException.class,() -> game.drawMission(MissionType.Peasant));
    }

    @Test
    void botDrawAndPlaceParcelLessStaminaAndAddParcelToTemporaryInventoryAndBoard() throws OutOfResourcesException, IllegalAccessException, BadPlaceParcelException {
        assertDoesNotThrow(() -> game.getTemporaryInventory().hasPlayedCorrectly());

        assertEquals(0,game.getTemporaryInventory().getParcelsSaved().size());
        assertEquals(32,game.getResourceSize(ResourceType.Parcel));
        assertEquals(2,game.getTemporaryInventory().getStamina());
        assertEquals(1,game.getPlacedCoordinates().size());

        List<ColorType> parcels = game.drawParcels();

        assertThrows(NoSuchElementException.class,() -> game.getTemporaryInventory().hasPlayedCorrectly());

        assertEquals(3,game.getTemporaryInventory().getParcelsSaved().size());

        game.selectParcel(parcels.get(0));

        assertThrows(NoSuchElementException.class,() -> game.getTemporaryInventory().hasPlayedCorrectly());

        assertEquals(31,game.getResourceSize(ResourceType.Parcel));
        assertEquals(1,game.getTemporaryInventory().getStamina());

        game.placeParcel(new Coordinate(0,-1,1));

        assertDoesNotThrow(() -> game.getTemporaryInventory().hasPlayedCorrectly());

        assertEquals(2,game.getPlacedCoordinates().size());
    }

    @Test
    void botDrawTooMuchParcel() throws OutOfResourcesException, IllegalAccessException {
        for (int i = 0; i < 32; i++) {
            cheatGame.selectParcel(cheatGame.drawParcels().get(0));
        }
        System.out.println(cheatGame.getResourceSize(ResourceType.Parcel));
        assertThrows(OutOfResourcesException.class,() -> cheatGame.drawParcels());
    }

    @Test
    void botDrawTwoTimesInTheSameTurn() throws OutOfResourcesException, IllegalAccessException {
        game.drawParcels();
        assertThrows(IllegalAccessException.class,() -> game.drawParcels());
    }

    @Test
    void botSelectTwoTimesInTheSameTurn() throws OutOfResourcesException, IllegalAccessException {
        List<ColorType> drawParcels =  game.drawParcels();
        game.selectParcel(drawParcels.get(0));
        assertThrows(IllegalAccessException.class,() -> game.selectParcel(drawParcels.get(1)));
    }

    @Test
    void botPlaceTwoTimesInTheSameTurn() throws OutOfResourcesException, IllegalAccessException, BadPlaceParcelException {
        List<ColorType> drawParcels =  game.drawParcels();
        game.selectParcel(drawParcels.get(0));
        game.placeParcel(new Coordinate(0,-1,1));
        assertThrows(IllegalAccessException.class,() -> game.placeParcel(new Coordinate(0,1,-1)));
    }

    @Test
    void botPlaceWrongParcelAndThenGoodAndRetryToPlace() throws OutOfResourcesException, IllegalAccessException, BadPlaceParcelException {
        game.selectParcel(game.drawParcels().get(0));
        assertThrows(BadPlaceParcelException.class,() -> game.placeParcel(new Coordinate(0,-2,2)));
        game.placeParcel(new Coordinate(0,-1,1));
        assertThrows(IllegalAccessException.class, () -> game.placeParcel(new Coordinate(1,-1,0)));
    }

    @Test
    void botPlaceCanal() throws OutOfResourcesException, IllegalAccessException,BadPlaceCanalException {
        game.getBoard().placeParcel(new Parcel(),new Coordinate(0,-1,1));
        game.getBoard().placeParcel(new Parcel(),new Coordinate(1,-1,0));
        game.drawCanal();
        assertDoesNotThrow(() -> game.placeCanal(new Coordinate(0,-1,1),new Coordinate(1,-1,0)));
    }

    @Test
    void botPlaceWrongCanalAndThenGoodAndRetryToPlace() throws OutOfResourcesException, IllegalAccessException, BadPlaceParcelException, BadPlaceCanalException {
        game.getBoard().placeParcel(new Parcel(),new Coordinate(0,-1,1));
        game.getBoard().placeParcel(new Parcel(),new Coordinate(1,-1,0));
        game.getBoard().placeParcel(new Parcel(),new Coordinate(1,-2,1));
        game.drawCanal();
        game.getTemporaryInventory().reset(2);
        assertThrows(BadPlaceCanalException.class, () -> game.placeCanal(new Coordinate(1,-1,0),new Coordinate(1,-2,1)));
        game.placeCanal(new Coordinate(0,-1,1),new Coordinate(1,-1,0));
        assertThrows(IllegalAccessException.class, () -> game.placeCanal(new Coordinate(1,-1,0),new Coordinate(1,-2,1)));
    }

    @Test
    void botMovePanda(){
        game.getBoard().placeParcel(new Parcel(),new Coordinate(1,-1,0));
        assertDoesNotThrow(() -> game.moveCharacter(CharacterType.Panda, new Coordinate(1,-1,0)));
    }

    @Test
    void botMoveWrongPandaAndThenGoodAndRetryToMove(){
        game.getBoard().placeParcel(new Parcel(),new Coordinate(0,-1,1));
        assertThrows(BadMoveCharacterException.class, () -> game.moveCharacter(CharacterType.Panda,new Coordinate(1,-1,0)));
        assertDoesNotThrow(() -> game.moveCharacter(CharacterType.Panda,new Coordinate(0,-1,1)));
        assertThrows(IllegalAccessException.class, () -> game.moveCharacter(CharacterType.Panda,new Coordinate()));
    }

    @Test
    void botMovePeasant(){
        game.getBoard().placeParcel(new Parcel(),new Coordinate(1,-1,0));
        assertDoesNotThrow(() -> game.moveCharacter(CharacterType.Peasant, new Coordinate(1,-1,0)));
    }

    @Test
    void botMoveWrongPeasantAndThenGoodAndRetryToMove(){
        game.getBoard().placeParcel(new Parcel(),new Coordinate(0,-1,1));
        assertThrows(BadMoveCharacterException.class, () -> game.moveCharacter(CharacterType.Peasant,new Coordinate(1,-1,0)));
        assertDoesNotThrow(() -> game.moveCharacter(CharacterType.Peasant,new Coordinate(0,-1,1)));
        assertThrows(IllegalAccessException.class, () -> game.moveCharacter(CharacterType.Peasant,new Coordinate()));
    }
}