package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
import fr.unice.polytech.startingpoint.game.mission.PandaMission;
import fr.unice.polytech.startingpoint.game.mission.ParcelMission;
import fr.unice.polytech.startingpoint.game.mission.PeasantMission;
import fr.unice.polytech.startingpoint.type.*;
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

        playerData.resetTemporaryInventory(WeatherType.NO_WEATHER);

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
        playerData.addBamboo(ColorType.GREEN);
        playerData.addBamboo(ColorType.YELLOW);
        assertArrayEquals(new int[]{1,1,1},playerData.getInventory().getInventoryBamboo());

        playerData.addMission(new ParcelMission(ColorType.RED, FormType.TRIANGLE, 3));
        playerData.addMission(new PandaMission(ColorType.RED,2));
        playerData.addMission(new PeasantMission(ColorType.RED, ImprovementType.NOTHING, 4));
        assertEquals(1,playerData.getParcelMissions().size());
        assertEquals(1,playerData.getPandaMissions().size());
        assertEquals(1,playerData.getPeasantMissions().size());
    }

    @Test
    void score(){
        assertEquals(0,playerData.getScore());
        assertEquals(0,playerData.getMissionsDone());

        playerData.addScore(3);

        assertEquals(3,playerData.getScore());
        assertEquals(1,playerData.getMissionsDone());
    }

    @Test
    void windRolledSo2SameAction(){
        Game game=new Game(new BotType[]{BotType.PANDA_BOT} );
        game.getPlayerData().botPlay(WeatherType.WIND);
        assertEquals(2, game.getPlayerData().getPandaMissions().size());//le panda va piocher 2 fois la mission panda
    }

}