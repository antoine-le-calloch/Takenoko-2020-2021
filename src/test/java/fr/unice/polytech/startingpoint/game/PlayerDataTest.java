package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
import fr.unice.polytech.startingpoint.type.ActionType;
import fr.unice.polytech.startingpoint.type.ColorType;
import fr.unice.polytech.startingpoint.type.FormType;
import fr.unice.polytech.startingpoint.type.ImprovementType;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerDataTest {
    Game game;
    PlayerData playerData;

    @BeforeEach
    void Setup() {
        game = new Game();
        playerData = game.getPlayerData();
    }

    @Test
    void temporaryInventory() {
        playerData.looseStamina();
        playerData.looseStamina();
        assertThrows(OutOfResourcesException.class,() -> playerData.looseStamina());

        playerData.add(ActionType.DRAW_PARCELS);
        playerData.add(ActionType.SELECT_PARCEL);
        assertThrows(NoSuchElementException.class,() -> playerData.hasPlayedCorrectly());

        playerData.add(ActionType.PLACE_PARCEL);
        assertTrue(playerData.contains(ActionType.PLACE_PARCEL));
        assertDoesNotThrow(() -> playerData.hasPlayedCorrectly());

        playerData.remove(ActionType.PLACE_PARCEL);
        assertFalse(playerData.contains(ActionType.PLACE_PARCEL));
        assertThrows(NoSuchElementException.class,() -> playerData.hasPlayedCorrectly());

        playerData.saveParcels(new ArrayList<>(Arrays.asList(new Parcel(),new Parcel(),new Parcel())));
        assertEquals(3,playerData.getParcelsSaved().size());

        playerData.saveParcel(new Parcel());
        assertNotNull(playerData.getParcel());

        playerData.resetTemporaryInventory();

        assertNull(playerData.getParcel());
        assertEquals(0,playerData.getParcelsSaved().size());
        assertEquals(0,playerData.getTemporaryInventory().getActionTypeList().size());
        assertEquals(2,playerData.getTemporaryInventory().getStamina());
    }

    @Test
    void inventory(){
        playerData.addCanal(new Canal());
        assertEquals(1,playerData.getInventory().getInventoryCanal().size());
        playerData.pickCanal();
        assertEquals(0,playerData.getInventory().getInventoryCanal().size());
        assertThrows(OutOfResourcesException.class,() -> playerData.pickCanal());

        playerData.addBamboo(ColorType.RED);
        playerData.addBamboo(ColorType.BLUE);
        assertArrayEquals(new int[]{1,1},playerData.getInventory().getBamboo());

        playerData.addMission(new ParcelMission(game.getBoard(), ColorType.RED,3, FormType.TRIANGLE));
        playerData.addMission(new PandaMission(game.getBoard(), ColorType.RED,2));
        playerData.addMission(new PeasantMission(game.getBoard(), ColorType.RED,4, ImprovementType.NOTHING));
        assertEquals(3,playerData.getMissions().size());
        assertEquals(1,playerData.getParcelMissions().size());
        assertEquals(1,playerData.getPandaMissions().size());
        assertEquals(1,playerData.getPeasantMissions().size());
        playerData.subMissions(playerData.getMissions());
        assertEquals(0,playerData.getMissions().size());
    }

    @Test
    void score(){
        assertEquals(0,playerData.getScore());
        assertEquals(0,playerData.getMissionsDone());

        playerData.addScore(3);

        assertEquals(3,playerData.getScore());
        assertEquals(1,playerData.getMissionsDone());
    }
}