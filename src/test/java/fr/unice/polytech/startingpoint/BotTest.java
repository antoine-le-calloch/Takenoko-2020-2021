package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class BotTest {
    private Bot bot1;
    private Bot bot2;
    private Board board;
    private Resource resource;
    private Parcel parcel1;
    private Parcel parcel2;
    private Canal canal;

    @BeforeEach
    public void setUp(){
        resource = new Resource();
        board = new Board();
        parcel1 = new Parcel(Color.BLUE);
        parcel2 = new Parcel(Color.NO_COLOR);
        canal = new Canal();
        bot1 = new IntelligentBot(resource,board);
        bot2 = new IntelligentBot(resource,board);
    }

    @Test
    public void testEquals(){
        assertEquals(bot1,bot1);
        assertNotEquals(bot1, null);
        assertNotEquals(bot1,bot2);
    }

    @Test
    public void missionIncrease(){
        bot1.drawMission();
        assertEquals(1,bot1.getInventoryMission().size());
    }

    @Test
    public void missionDecrease(){
        bot1.drawMission();
        Mission todelete = bot1.getInventoryMission().get(0);
        bot1.deleteMission(todelete);
        assertEquals(0,bot1.getInventoryMission().size());
        bot1.deleteMission(todelete);
        assertNotEquals(-1,bot1.getInventoryMission().size());
    }

    @Test
    public void initializeNextCoordinatesNextToCentral(){
        List<Coordinate> nextTocentral = board.getPlayablePlaces();
        assertEquals(6,nextTocentral.size());
        Coordinate randomco=nextTocentral.get(0);
        assertEquals(2,    Coordinate.getNorm(new Coordinate(0,0,0),randomco));
        int[] tabco = randomco.getCoordinate();
        int sumco=tabco[0]+tabco[1]+tabco[2];
        assertEquals(0,sumco);
    }

    @Test
    public void initializeNextCoordinatesAwayFromCentral(){
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        List<Coordinate> awayFromCentral = board.getAllPlaces();
        Collections.shuffle(awayFromCentral);
        Coordinate randomCo=awayFromCentral.get(0);
        int [] tabco= randomCo.getCoordinate();
        int sumco=tabco[0]+tabco[1]+tabco[2];
        assertTrue(Coordinate.getNorm(new Coordinate(1,-1,0),randomCo)<19);
        assertTrue(Coordinate.getNorm(new Coordinate(1,-1,0),randomCo)>=0);
        assertEquals(0,sumco);
    }

    @Test
    public void possibleCoordinatesParcelTest(){
        List<Coordinate> possibleCo = board.getPlayablePlaces();
        Collections.shuffle(possibleCo);
        assertTrue(board.isPlayableParcel(possibleCo.get(0)));
    }

    @Test
    public void notPossibleCoordinatesCanal(){
        List<Coordinate[]> possibleCanals = bot1.possibleCoordinatesCanal();
        assertEquals(possibleCanals.size(),0);
        board.placeParcel(parcel1,new Coordinate(2,-2,0));
        board.placeCanal(canal,new Coordinate(0,0,0),new Coordinate(1,-1,0));
        List<Coordinate[]>possibleCanals2 = bot1.possibleCoordinatesCanal();
        assertEquals(possibleCanals2.size(),0);
    }

    @Test
    public void possibleCoordinatesCanal(){
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        board.placeParcel(parcel2,new Coordinate(1,0,-1));
        List<Coordinate[]> possibleCanals = bot1.possibleCoordinatesCanal();
        Collections.shuffle(possibleCanals);
        Coordinate[] tabco = possibleCanals.get(0);
        assertTrue(board.isPlayableCanal(tabco[0],tabco[1]));
    }

    @Test
    public void notExistPossibleCoordinatesBamboo(){
        assertEquals(0,bot1.possibleCoordinatesCharacter().size());
    }

    @Test
    public void ExistPossibleCoordinatesBamboo(){
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        assertTrue(parcel1.getIrrigated());
        assertEquals(parcel1.getCoordinates(), bot1.possibleCoordinatesCharacter().get(0));
    }

    @Test
    public void movePanda(){
        assertTrue(board.placeParcel(parcel1,new Coordinate(1,-1,0)));
        bot1.randomMovePanda(bot1.possibleCoordinatesCharacter());
        assertEquals(1,bot1.getInventoryBamboo()[0]);
        assertEquals(0,parcel1.getNbBamboo());
    }

    @Test
    public void movePeasant(){
        board.placeParcel(parcel1,new Coordinate(1,-1,0));
        bot1.randomMovePeasant(bot1.possibleCoordinatesCharacter());
        assertEquals(2,parcel1.getNbBamboo());
    }

    @Test
    public void addInventoryMission() throws ExceptionTakenoko {
        board.placeParcel(parcel1,new Coordinate(1,-1,0));  // parcel blue
        bot1.addBamboo(parcel1.getColor());
        assertEquals(0,bot1.getInventoryBamboo()[0]);
        assertEquals(1,bot1.getInventoryBamboo()[1]);
    }

    @Test
    public void addInventoryMissionNoColor() throws ExceptionTakenoko {
        board.placeParcel(parcel2, new Coordinate(1, -1, 0));  // parcel nColor
        Exception exception = assertThrows(ExceptionTakenoko.class, () -> {bot1.addBamboo(parcel2.getColor());});
        assertEquals(exception.getMessage(), "invalid color");
    }
}
