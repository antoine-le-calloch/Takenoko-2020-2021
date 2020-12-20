package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
import fr.unice.polytech.startingpoint.type.ActionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TemporaryInventoryTest {
    TemporaryInventory temporaryInventory;

    @BeforeEach
    public void setUp(){
        temporaryInventory = new TemporaryInventory(2);
    }

    @Test
    public void stamina() throws OutOfResourcesException {
        assertEquals(2,temporaryInventory.getStamina());

        temporaryInventory.looseStamina();
        assertEquals(1,temporaryInventory.getStamina());

        temporaryInventory.looseStamina();
        assertEquals(0,temporaryInventory.getStamina());

        assertThrows(OutOfResourcesException.class, () -> temporaryInventory.looseStamina());

        temporaryInventory.reset();
        assertEquals(2,temporaryInventory.getStamina());
    }

    @Test
    public void parcel() throws OutOfResourcesException {
        assertNull(temporaryInventory.getParcel());

        temporaryInventory.saveParcel(new Parcel());
        assertEquals(new Parcel().getParcelInformation(),temporaryInventory.getParcel().getParcelInformation());

        temporaryInventory.reset();
        assertNull(temporaryInventory.getParcel());
    }

    @Test
    public void saveParcels(){
        assertTrue(temporaryInventory.getParcelsSaved().isEmpty());

        temporaryInventory.saveParcels(new ArrayList<>(Arrays.asList(new Parcel(),new Parcel(),new Parcel())));
        assertEquals(3,temporaryInventory.getParcelsSaved().size());

        temporaryInventory.reset();
        assertTrue(temporaryInventory.getParcelsSaved().isEmpty());
    }

    @Test
    public void actionTypeList(){
        assertTrue(temporaryInventory.getActionTypeList().isEmpty());

        temporaryInventory.add(ActionType.DRAW_PARCELS);
        assertEquals(1,temporaryInventory.getActionTypeList().size());
        assertThrows(NoSuchElementException.class,() -> temporaryInventory.hasPlayedCorrectly());

        temporaryInventory.add(ActionType.SELECT_PARCEL);
        assertEquals(2,temporaryInventory.getActionTypeList().size());
        assertThrows(NoSuchElementException.class,() -> temporaryInventory.hasPlayedCorrectly());

        temporaryInventory.add(ActionType.PLACE_PARCEL);
        assertEquals(3,temporaryInventory.getActionTypeList().size());
        assertDoesNotThrow(() -> temporaryInventory.hasPlayedCorrectly());

        temporaryInventory.remove(ActionType.SELECT_PARCEL);
        assertEquals(2,temporaryInventory.getActionTypeList().size());
        assertThrows(NoSuchElementException.class,() -> temporaryInventory.hasPlayedCorrectly());

        temporaryInventory.reset();
        assertTrue(temporaryInventory.getActionTypeList().isEmpty());
    }
}