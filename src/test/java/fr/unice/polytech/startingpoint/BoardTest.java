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
        board.putParcel(resource.drawParcel(),new Coordinate(1,-1,0));
        assertEquals(2,board.getParcel().size());
    }

    @Test
    public void wrongParcelPlacementSoNoParcelIncrease(){
        board.putParcel(resource.drawParcel(),new Coordinate(0,0,0));
        assertEquals(1,board.getParcel().size());
    }

    @Test
    public void goodPlacementNextToCentralParcel(){
        assertTrue(board.putParcel(parcel1,new Coordinate(1,-1,0)));
        assertTrue(board.putParcel(parcel2,new Coordinate(1,0,-1)));
    }

    @Test
    public void wrongPlacementOnCentralParcel(){
        assertFalse(board.putParcel(parcel1,new Coordinate(0,0,0)));
    }

    @Test
    public void wrongPlacementAwayFromCentralParcel(){
        assertFalse(board.putParcel(parcel2,new Coordinate(3,0,-3)));
    }

    //On  vérifie le fait qu'une parcelle
    // pour être posée loin du centre,
    // doit avoir au moins 2 parcelles voisines
    @Test
    public void goodPlacementNextToNormalParcels(){
        board.putParcel(parcel1,new Coordinate(1,-1,0));
        board.putParcel(parcel2,new Coordinate(1,0,-1));
        assertTrue(board.putParcel(parcel3,new Coordinate(2,-1,-1)));
    }


    //On  vérifie le fait qu'une parcelle
    // pour être posée loin du centre,
    // doit avoir au moins 2 parcelles voisines
    @Test
    public void wrongPlacementNextToNormalParcels(){
        board.putParcel(parcel1,new Coordinate(1,-1,0));
        board.putParcel(parcel2,new Coordinate(1,0,-1));
        assertFalse(board.putParcel(parcel3,new Coordinate(3,0,-3)));
    }

    @Test
    void normTesting(){
        board.playableParcel(new Coordinate(1,-1,0));
        assertEquals(2,Coordinate.getNorm(new Coordinate(1,-1,0),new Coordinate(1,0,-1)));
        assertNotEquals(17,Coordinate.getNorm(new Coordinate(0,0,0),new Coordinate(3,0,-3)));
        assertEquals(0,Coordinate.getNorm(new Coordinate(0,0,0),new Coordinate(0,0,0)));
    }

    @Test void irrigationFromCentral(){
        board.putParcel(parcel1,new Coordinate(0,-1,1));
        assertTrue(parcel1.getIrrigated());
    }

    @Test void noIrrigationFromCentral(){
        board.putParcel(parcel1,new Coordinate(0,-1,1));
        board.putParcel(parcel2,new Coordinate(1,-1,0));
        board.putParcel(parcel3,new Coordinate(1,-2,1));
        assertFalse(parcel3.getIrrigated());
    }
}
