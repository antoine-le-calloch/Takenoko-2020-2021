package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.*;
import fr.unice.polytech.startingpoint.exception.*;

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

public class BoardTest {
    Game game;
    Board board;

    @BeforeEach
    public void initialize(){
        game = new Game();
        board = game.getBoard();
    }

    @Test
    void normTesting(){
        assertEquals(2,Coordinate.getNorm(new Coordinate(1,-1,0),new Coordinate(1,0,-1)));
        assertNotEquals(17,Coordinate.getNorm(new Coordinate(0,0,0),new Coordinate(3,0,-3)));
        assertEquals(0,Coordinate.getNorm(new Coordinate(0,0,0),new Coordinate(0,0,0)));
    }


    @Test
    public void parcelIncrease(){
        board.placeParcel(new Parcel(ColorType.NO_COLOR,ImprovementType.NOTHING),new Coordinate(1,-1,0));
        assertEquals(2,board.getPlacedParcels().size());
    }

    @Test void irrigationFromCentral(){
        board.placeParcel(new Parcel(ColorType.NO_COLOR,ImprovementType.NOTHING),new Coordinate(0,-1,1));
        assertTrue(board.getPlacedParcels().get(new Coordinate(0,-1,1)).getIrrigated());
    }

    @Test void noIrrigationFromCentral(){
        board.placeParcel(new Parcel(ColorType.NO_COLOR,ImprovementType.NOTHING),new Coordinate(0,-1,1));
        board.placeParcel(new Parcel(ColorType.NO_COLOR,ImprovementType.NOTHING),new Coordinate(1,-1,0));
        board.placeParcel(new Parcel(ColorType.NO_COLOR,ImprovementType.NOTHING),new Coordinate(1,-2,1));
        assertFalse(board.getPlacedParcels().get(new Coordinate(1,-2,1)).getIrrigated());
    }

    @Test void irrigationByCanals(){
        board.placeParcel(new Parcel(ColorType.NO_COLOR,ImprovementType.NOTHING),new Coordinate(0,-1,1));
        board.placeParcel(new Parcel(ColorType.NO_COLOR,ImprovementType.NOTHING),new Coordinate(1,-1,0));
        board.placeParcel(new Parcel(ColorType.NO_COLOR,ImprovementType.NOTHING),new Coordinate(1,-2,1));
        board.placeCanal(new Canal(),new Coordinate(0,-1,1),new Coordinate(1,-1,0));
        board.placeCanal(new Canal(),new Coordinate(0,-1,1),new Coordinate(1,-2,1));
        assertTrue(board.getPlacedParcels().get(new Coordinate(1,-2,1)).getIrrigated());
    }

    //le paysan fait pousser un bambou ou il est
    @Test
    void goodGrow() throws CantDeleteBambooException {
        board.placeParcel(new Parcel(ColorType.NO_COLOR,ImprovementType.NOTHING), new Coordinate(1, -1, 0));
        board.moveCharacter(CharacterType.PEASANT,new Coordinate(1, -1, 0));
        assertEquals(2,board.getPlacedParcels().get(new Coordinate(1, -1, 0)).getNbBamboo());
    }

    //4 bambous max
    @Test
    void maxGrow() throws CantDeleteBambooException {
        board.placeParcel(new Parcel(ColorType.NO_COLOR,ImprovementType.NOTHING), new Coordinate(1, -1, 0));
        for (int i = 0; i < 10; i++) {
            board.moveCharacter(CharacterType.PEASANT,new Coordinate(1,-1,0));
            board.moveCharacter(CharacterType.PEASANT,new Coordinate(0,0,0));
        }
        assertEquals(4,board.getPlacedParcels().get(new Coordinate(1,-1,0)).getNbBamboo());
    }

    //bambous pousse autour si irrigué + même couleur
    @Test
    void actionPeasantSameColorAroundAndIrrigated() throws CantDeleteBambooException {
        board.placeParcel(new Parcel(ColorType.BLUE,ImprovementType.NOTHING), new Coordinate(1, -1, 0));
        board.placeParcel(new Parcel(ColorType.BLUE,ImprovementType.NOTHING), new Coordinate(1,0,-1));
        board.moveCharacter(CharacterType.PEASANT, new Coordinate(1, -1, 0));
        assertEquals(2,board.getPlacedParcels().get(new Coordinate(1,0,-1)).getNbBamboo());
    }

    //bambous pousse pas autour si couleur diff
    @Test
    void actionPeasantDifferentColorAround() throws CantDeleteBambooException {
        board.placeParcel(new Parcel(ColorType.BLUE,ImprovementType.NOTHING), new Coordinate(1, -1, 0));
        board.placeParcel(new Parcel(ColorType.RED,ImprovementType.NOTHING), new Coordinate(1,0,-1));
        board.moveCharacter(CharacterType.PEASANT, new Coordinate(1, -1, 0));
        assertEquals(1,board.getPlacedParcels().get(new Coordinate(1,0,-1)).getNbBamboo());
    }

    //bambous pousse pas autour si non irriguée
    @Test
    void actionPeasantNotIrrigatedAround() throws CantDeleteBambooException {
        board.placeParcel(new Parcel(ColorType.BLUE,ImprovementType.NOTHING), new Coordinate(1, -1, 0));
        board.placeParcel(new Parcel(ColorType.BLUE,ImprovementType.NOTHING), new Coordinate(1, 0, -1));
        board.placeParcel(new Parcel(ColorType.RED,ImprovementType.NOTHING), new Coordinate(2,-1,-1));
        board.moveCharacter(CharacterType.PEASANT, new Coordinate(1, -1, 0));
        assertEquals(0,board.getPlacedParcels().get(new Coordinate(2,-1,-1)).getNbBamboo());
    }

    // panda mange un bambou
    @Test
    void goodEat() throws CantDeleteBambooException {
        board.placeParcel(new Parcel(ColorType.RED,ImprovementType.NOTHING), new Coordinate(1, -1, 0));
        board.moveCharacter(CharacterType.PANDA, new Coordinate(1, -1, 0));
        assertEquals(0, board.getPlacedParcels().get(new Coordinate(1, -1, 0)).getNbBamboo());
    }

    //il mange pas si 0 bambous sur la parcelle
    @Test
    void minEat() throws CantDeleteBambooException {
        board.placeParcel(new Parcel(ColorType.NO_COLOR,ImprovementType.NOTHING), new Coordinate(1, -1, 0));
        board.placeParcel(new Parcel(ColorType.NO_COLOR,ImprovementType.NOTHING), new Coordinate(1,0,-1));
        board.moveCharacter(CharacterType.PANDA, new Coordinate(1, -1, 0));
        board.moveCharacter(CharacterType.PANDA, new Coordinate(1,0,-1));

        Exception exception1 = assertThrows(CantDeleteBambooException.class, () ->
        { board.moveCharacter(CharacterType.PANDA, new Coordinate(1, -1, 0));});

        assertEquals(exception1.getMessage(),"[1,-1,0]");
    }

}
