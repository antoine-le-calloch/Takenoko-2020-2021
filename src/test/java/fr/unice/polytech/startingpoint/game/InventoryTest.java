package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
import fr.unice.polytech.startingpoint.game.mission.ParcelMission;
import fr.unice.polytech.startingpoint.type.ColorType;
import fr.unice.polytech.startingpoint.type.FormType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    private Inventory inventory;
    private ParcelMission mission;

    @BeforeEach
    void setUp(){
        inventory = new Inventory();
        mission = new ParcelMission(ColorType.GREEN, FormType.LINE, 0);
    }

    @Test
    void inventoryCanal() throws OutOfResourcesException {
        inventory.addCanal(new Canal());
        inventory.pickCanal();
        assertThrows(OutOfResourcesException.class, () -> inventory.pickCanal());
    }

    @Test
    void addBambooInventory() {
        inventory.addBamboo(ColorType.GREEN);
        assertEquals(0, inventory.getInventoryBamboo()[0]);
        assertEquals(1, inventory.getInventoryBamboo()[1]);
        assertEquals(0, inventory.getInventoryBamboo()[2]);
    }

    @Test
    void deleteTwoBambooInventory() {
        inventory.addBamboo(ColorType.GREEN);
        inventory.addBamboo(ColorType.GREEN);
        inventory.subTwoBamboos(ColorType.GREEN);
        assertEquals(0, inventory.getInventoryBamboo()[1]);
    }

    @Test
    void deleteOneBambooPerColoInventory() {
        inventory.addBamboo(ColorType.GREEN);
        inventory.addBamboo(ColorType.YELLOW);
        inventory.addBamboo(ColorType.RED);
        inventory.subOneBambooPerColor();
        assertEquals(0, inventory.getInventoryBamboo()[0]);
        assertEquals(0, inventory.getInventoryBamboo()[1]);
        assertEquals(0, inventory.getInventoryBamboo()[2]);
    }

    @Test
    void deleteOneBambooPerColoInventoryNotEnoughBamboo() {
        inventory.addBamboo(ColorType.GREEN);
        inventory.addBamboo(ColorType.YELLOW);
        inventory.subOneBambooPerColor();
        assertEquals(0, inventory.getInventoryBamboo()[0]);
        assertEquals(1, inventory.getInventoryBamboo()[1]);
        assertEquals(1, inventory.getInventoryBamboo()[2]);
    }

    @Test
    void deleteTwoBambooInventoryNotEnoughBamboo() {
        inventory.addBamboo(ColorType.GREEN);
        inventory.subTwoBamboos(ColorType.GREEN);
        assertEquals(0, inventory.getInventoryBamboo()[0]);
        assertEquals(1, inventory.getInventoryBamboo()[1]);
    }
}