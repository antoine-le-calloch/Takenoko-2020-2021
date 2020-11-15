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
        //on vérifie aussi que ces coordonnées sont valides
        bot1.initializeNextCoordinates(board);
        ArrayList<int []> nextTocentral =bot1.getNextCoordinates();
        assertEquals(6,nextTocentral.size());
        Collections.shuffle(nextTocentral);
        int[] randomco=nextTocentral.get(0);
        assertEquals(2,    Board.getNorm(new int[]{0,0,0},randomco));
        int sumco=randomco[0]+randomco[1]+randomco[2];
        assertEquals(0,sumco);
        assertEquals(true,board.playableParcel(randomco));

    }

    @Test void initializeNextCoordinatesAwayFromCentral(){
        board.putParcel(parcel1,new int[]{1,-1,0});
        board.putParcel(parcel2,new int[]{1,0,-1});
        bot1.initializeNextCoordinates(board);
        ArrayList<int []> awayFromcentral =bot1.getNextCoordinates();
        Collections.shuffle(awayFromcentral);
        int[] randomco=awayFromcentral.get(0);
        int[] randomco2=awayFromcentral.get(1);
        int sumco=randomco[0]+randomco[1]+randomco[2];
        int sumco2=randomco2[0]+randomco2[1]+randomco2[2];
        assertEquals(sumco2,sumco);
        assertEquals(0,sumco);
        //la norme doit être comprise entre 2 et 18
        //METTRE UN HASHSET CAR IL Y A UNION POSSIBILITE 2 FOIS LA MM CASE
        assertTrue((Board.getNorm(randomco,randomco2)>=2));
        assertFalse((Board.getNorm(randomco,randomco2)>18));
}

}