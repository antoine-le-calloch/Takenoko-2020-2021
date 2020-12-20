package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
import fr.unice.polytech.startingpoint.type.ColorType;
import fr.unice.polytech.startingpoint.type.FormType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {
    private Inventory inventory;
    private Board board;
    private Parcel parcel;
    private ParcelMission mission;

    @BeforeEach
    public void setUp(){
        inventory = new Inventory();
        board = new Board();
        parcel = new Parcel(ColorType.BLUE);
        mission = new ParcelMission( ColorType.BLUE, 0, FormType.LINE);
    }

    @Test
    public void inventoryCanal() throws OutOfResourcesException {
        inventory.addCanal(new Canal());
        inventory.pickCanal();
        assertThrows(OutOfResourcesException.class, () -> inventory.pickCanal());
    }

    @Test
    public void inventoryBamboo() {
        board.placeParcel(parcel,new Coordinate(1,-1,0));  // parcel blue
        inventory.addBamboo(parcel.getColor());
        assertEquals(0, inventory.getBamboo()[0]);
        assertEquals(1, inventory.getBamboo()[1]);
        inventory.subBamboo(parcel.getColor());
        assertEquals(0, inventory.getBamboo()[0]);
        assertEquals(0, inventory.getBamboo()[1]);
    }

    @Test
    public void inventoryMission(){
        inventory.addMission(mission);
        assertEquals(1, inventory.getMissions().size());
        inventory.subMissions(new ArrayList<>(Collections.singletonList(mission)));
        assertEquals(0, inventory.getMissions().size());
    }
}