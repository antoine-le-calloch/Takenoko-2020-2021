package fr.unice.polytech.startingpoint;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class BotTest {
    private Bot bot1;
    private Bot bot2;
    private Resource resource;
    private Board board;
    Parcel parcel1;
    Parcel parcel2;

    @BeforeEach public void setUp(){
        bot1 = new Bot("Bob");
        bot2 = new Bot("Bob");
        resource = new Resource();
        board = new Board();
        parcel1 = new Parcel();
        parcel2 = new Parcel();
    }

    @Test public void testEquals(){
        assertEquals(bot1,bot1);
        assertNotEquals(bot1, null);
        assertNotEquals(bot1,bot2);
    }

    @Test
    public void parcelIncrease(){
        bot1.placeParcel(resource, board);
        assertEquals(2,board.getParcel().size());
    }

    @Test
    public void missionIncrease(){
        bot1.drawMission(resource);
        assertEquals(1,bot1.getInventoryMission().size());
    }

    @Test
    public void missionDecrease(){
        bot1.drawMission(resource);
        Mission todelete=bot1.getInventoryMission().get(0);
        bot1.deleteMission(todelete);
        assertEquals(0,bot1.getInventoryMission().size());
        bot1.deleteMission(todelete);
        assertNotEquals(-1,bot1.getInventoryMission().size());
    }

    @Test void initializeNextCoordinatesNextToCentral(){
        //on génère une liste des 6 tuples de coordonnées possibles
        // à côté de la parcelle centrale
        //on mélange la liste
        //toutes les coordonnées
        // de cette liste ont une norme de 2 par rapport à la parcelle centrale
        //on vérifie aussi que ces coordonnées sont valides et sont posables

        ArrayList<Coordinate> nextTocentral = bot1.coordinatesAroundBoard(board);
        assertEquals(6,nextTocentral.size());
        Collections.shuffle(nextTocentral);
        Coordinate randomco=nextTocentral.get(0);
        assertEquals(2,    Coordinate.getNorm(new Coordinate(0,0,0),randomco));
        int[] tabco = randomco.getCoordinate();
        int sumco=tabco[0]+tabco[1]+tabco[2];
        assertEquals(0,sumco);
        assertEquals(true,board.playableParcel(randomco));

    }

    @Test void initializeNextCoordinatesAwayFromCentral(){
        board.putParcel(parcel1,new Coordinate(1,-1,0));
        board.putParcel(parcel1,new Coordinate(1,0,-1));
        ArrayList<Coordinate> nextTocentral = bot1.coordinatesAroundBoard(board);
        Collections.shuffle(nextTocentral);
        Coordinate randomco=nextTocentral.get(0);
        Coordinate randomco2=nextTocentral.get(1);
        int [] tabco= randomco.getCoordinate();
        int [] tabco2= randomco.getCoordinate();
        int sumco=tabco[0]+tabco[1]+tabco[2];
        int sumco2=tabco2[0]+tabco2[1]+tabco2[2];
        assertEquals(sumco2,sumco);
        assertEquals(0,sumco);
        //la norme doit être comprise entre 2 et 18
        //en effet si on regarde le plateau parmis toutes les parcelles dasn next coordinates dans cette exemple
        // soit une case est à côté l'une de l'autre minoré par une  norme de 2
        //soit éloignée au max de 18 majorée par une norme de 18
        assertTrue((Coordinate.getNorm(randomco,randomco2)>=2));
        assertFalse((Coordinate.getNorm(randomco,randomco2)>18));
    }


    @Test void possibleCoordinatestest(){
        ArrayList<Coordinate> possibleco = bot1.possibleCoordinates(board);
        Collections.shuffle(possibleco);
        assertEquals(true , board.playableParcel(possibleco.get(0)));
    }



}