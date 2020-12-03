package fr.unice.polytech.startingpoint;

import fr.unice.polytech.startingpoint.Bot.RandomBot;
import fr.unice.polytech.startingpoint.Game.Board;
import fr.unice.polytech.startingpoint.Game.Parcel;
import fr.unice.polytech.startingpoint.Game.Resource;
import fr.unice.polytech.startingpoint.Type.ColorType;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class RandomBotTest {
    private RandomBot rdmBot1;
    private Board board;
    Parcel parcel1;

    @BeforeEach
    public void setUp() {
        board = new Board();
        parcel1 = new Parcel(ColorType.NO_COLOR);
        rdmBot1 = new RandomBot(new Resource(),board);
    }
/*
    @Test
    public void parcelIncrease(){
        rdmBot1.placeRandomParcel(board.getFreePlaces());
        assertEquals(2,board.getPlacedParcels().size());
    }
*/

}
