package fr.unice.polytech.startingpoint;

import fr.unice.polytech.startingpoint.Bot.RandomBot;
import fr.unice.polytech.startingpoint.Game.Board;
import fr.unice.polytech.startingpoint.Game.Parcel;
import fr.unice.polytech.startingpoint.Game.Resource;
import fr.unice.polytech.startingpoint.Type.ColorType;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

public class RandomBotTest {
    private RandomBot rdmBot1;
    private Board board;
    Parcel parcel1;
    Resource resource;

    @BeforeEach
    public void setUp() {
        board = new Board();
        parcel1 = new Parcel(ColorType.NO_COLOR);
        rdmBot1 = new RandomBot(new Resource(),board);
        resource = new Resource();
    }

    @Test
    public void parcelIncrease(){
        rdmBot1.placeRandomParcelFromAList(rdmBot1.possibleCoordinatesParcel(), resource.drawParcel());
        assertEquals(2,board.getPlacedParcels().size());
    }

}
