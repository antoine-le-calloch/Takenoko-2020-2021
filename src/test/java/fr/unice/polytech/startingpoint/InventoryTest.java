package fr.unice.polytech.startingpoint;

import fr.unice.polytech.startingpoint.Bot.Bot;
import fr.unice.polytech.startingpoint.Bot.ParcelBot;
import fr.unice.polytech.startingpoint.Game.*;
import fr.unice.polytech.startingpoint.Type.ColorType;
import fr.unice.polytech.startingpoint.Type.FormType;
import fr.unice.polytech.startingpoint.Type.MissionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {
    private Inventory inventory1;
    private Inventory inventory2;
    private Board board;
    private Parcel parcel1;
    private Resource resource;
    private ParcelMission mission;

    @BeforeEach
    public void setUp(){
        inventory1 = new Inventory();
        inventory2 = new Inventory();
        resource = new Resource();
        board = new Board();
        parcel1 = new Parcel(ColorType.BLUE);
        mission = new ParcelMission( ColorType.BLUE , 0, FormType.LINE);
    }

    @Test
    public void differentInventory(){
        assertNotEquals(inventory1,inventory2);
    }

    @Test
    public void addInventoryBamboo(){
        board.placeParcel(parcel1,new Coordinate(1,-1,0));  // parcel blue
        inventory1.addBamboo(parcel1.getColor());
        assertEquals(0,inventory1.getBamboo()[0]);
        assertEquals(1,inventory1.getBamboo()[1]);
    }

    @Test
    public void subInventoryBamboo(){
        board.placeParcel(parcel1,new Coordinate(1,-1,0));  // parcel blue
        inventory1.subBamboo(parcel1.getColor());
        assertEquals(0,inventory1.getBamboo()[0]);
        assertEquals(0,inventory1.getBamboo()[1]);
    }

    @Test
    public void addInventoryMission(){
        inventory1.addMission(mission);
        assertEquals(1,inventory1.getMission().size());
    }

    @Test
    public void subInventoryMission(){
        inventory1.subMissions(new ArrayList<>(Arrays.asList(mission)));
        assertEquals(0,inventory1.getMission().size());
    }


}
