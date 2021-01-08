package fr.unice.polytech.startingpoint.game.playerdata;

import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
import fr.unice.polytech.startingpoint.game.board.Parcel;
import fr.unice.polytech.startingpoint.type.ActionType;
import fr.unice.polytech.startingpoint.type.WeatherType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class TemporaryInventoryTest {
    private TemporaryInventory temporaryInventory;

    @BeforeEach
    void setUp(){
        temporaryInventory = new TemporaryInventory();
    }

    @Test
    void stamina() throws OutOfResourcesException {
        assertEquals(2,temporaryInventory.getStamina());

        temporaryInventory.looseStamina();
        assertEquals(1,temporaryInventory.getStamina());

        temporaryInventory.looseStamina();
        assertEquals(0,temporaryInventory.getStamina());

        assertThrows(OutOfResourcesException.class, () -> temporaryInventory.looseStamina());

        temporaryInventory.reset(WeatherType.NO_WEATHER);
        assertEquals(2,temporaryInventory.getStamina());
    }

    @Test
    void parcel() throws OutOfResourcesException {
        assertNull(temporaryInventory.getParcel());

        temporaryInventory.saveParcel(new Parcel());
        assertEquals(new Parcel().getParcelInformation(),temporaryInventory.getParcel().getParcelInformation());

        temporaryInventory.reset(WeatherType.NO_WEATHER);
        assertNull(temporaryInventory.getParcel());
    }

    @Test
    void saveParcels(){
        assertTrue(temporaryInventory.getParcelsSaved().isEmpty());

        temporaryInventory.saveParcels(new ArrayList<>(Arrays.asList(new Parcel(),new Parcel(),new Parcel())));
        assertEquals(3,temporaryInventory.getParcelsSaved().size());

        temporaryInventory.reset(WeatherType.NO_WEATHER);
        assertTrue(temporaryInventory.getParcelsSaved().isEmpty());
    }

    @Test
    void actionTypeList(){
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

        temporaryInventory.reset(WeatherType.NO_WEATHER);
        assertTrue(temporaryInventory.getActionTypeList().isEmpty());
    }

    @Test void weatherDifferentFromSunSo2Stamina(){
        //de base no_weather
        assertEquals(2,temporaryInventory.getStamina());
        temporaryInventory.reset(WeatherType.NO_WEATHER);
        assertEquals(2,temporaryInventory.getStamina());
    }

    @Test void sunWeatherSo3Stamina(){
        temporaryInventory.reset(WeatherType.SUN);
        assertEquals(3,temporaryInventory.getStamina());
    }

    @Test void noWindSoNoDoubleAction(){
        assertFalse(temporaryInventory.isActionCouldBeDoneTwice());
        temporaryInventory.setWeatherType(WeatherType.SUN);
        assertFalse(temporaryInventory.isActionCouldBeDoneTwice());
    }

    @Test void windWeather(){
        temporaryInventory.reset(WeatherType.WIND);
        assertTrue(temporaryInventory.isActionCouldBeDoneTwice());
    }


}