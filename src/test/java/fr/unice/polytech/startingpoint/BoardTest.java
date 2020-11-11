package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    Resource resource;
    Board board;
    Parcel parcel1;
    Parcel parcel2;
    Parcel parcel3;

    @BeforeEach
    public void initialize(){
        board = new Board();
        resource = new Resource();
        parcel1 = new Parcel();
        parcel2 = new Parcel();
        parcel3 = new Parcel();
    }

    @Test
    public void goodParcelPlacementSoParcelIncrease(){
        board.putParcel(resource.drawParcel(),1,0,0);
        assertEquals(2,board.getParcel().size());
    }

    @Test
    public void wrongParcelPlacementSoNoParcelIncrease(){
        board.putParcel(resource.drawParcel(),0,0,0);
        assertEquals(1,board.getParcel().size());
    }

    @Test
    public void goodPlacementNextToCentralParcel(){
        assertTrue(board.putParcel(parcel1,1,0,0));
        assertTrue(board.putParcel(parcel2,0,-1,0));
    }

    @Test
    public void wrongPlacementOnCentralParcel(){
        assertFalse(board.putParcel(parcel1,0,0,0));
    }

    @Test
    public void wrongPlacementAwayFromCentralParcel(){
        assertFalse(board.putParcel(parcel2,-1,1,0));
    }

    @Test
    public void goodPlacementNextToNormalParcels(){
        board.putParcel(parcel1,1,0,0);
        board.putParcel(parcel2,0,1,0);
        assertTrue(board.putParcel(parcel3,1,1,0));
    }

    @Test
    public void wrongPlacementNextToNormalParcels(){
        board.putParcel(parcel1,1,0,0);
        board.putParcel(parcel2,0,1,0);
        assertFalse(board.putParcel(parcel3,0,1,1));
    }
}
