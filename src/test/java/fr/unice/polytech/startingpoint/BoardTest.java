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
        board.putParcel(resource.drawParcel(),new int[]{1,-1,0});
        assertEquals(2,board.getParcel().size());
    }

    @Test
    public void wrongParcelPlacementSoNoParcelIncrease(){
        board.putParcel(resource.drawParcel(),new int[]{0,0,0});
        assertEquals(1,board.getParcel().size());
    }

    @Test
    public void goodPlacementNextToCentralParcel(){
        assertTrue(board.putParcel(parcel1,new int[]{1,-1,0}));
        assertTrue(board.putParcel(parcel2,new int[]{1,0,-1}));
    }

    @Test
    public void wrongPlacementOnCentralParcel(){
        assertFalse(board.putParcel(parcel1,new int[]{0,0,0}));
    }

    @Test
    public void wrongPlacementAwayFromCentralParcel(){
        assertFalse(board.putParcel(parcel2,new int[]{3,0,-3}));
    }

    @Test
    public void goodPlacementNextToNormalParcels(){
        board.putParcel(parcel1,new int[]{1,-1,0});
        board.putParcel(parcel2,new int[]{1,0,-1});
        assertTrue(board.putParcel(parcel3,new int[]{2,-1,-1}));
    }

    @Test
    public void wrongPlacementNextToNormalParcels(){
        board.putParcel(parcel1,new int[]{1,-1,0});
        board.putParcel(parcel2,new int[]{1,0,-1});
        assertFalse(board.putParcel(parcel3,new int[]{3,0,-3}));
    }

    @Test
    void normTesting(){
        board.playableParcel(new int[]{1,-1,0});
    }
}
