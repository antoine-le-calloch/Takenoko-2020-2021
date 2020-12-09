package fr.unice.polytech.startingpoint.Bot;

import fr.unice.polytech.startingpoint.Game.*;
import fr.unice.polytech.startingpoint.Type.ColorType;
import fr.unice.polytech.startingpoint.Type.MissionType;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

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
    public void drawMissionParcel(){
        Random mockRand = mock(Random.class);
        Random mockRand2 = mock(Random.class);
        Mockito.when(mockRand.nextInt(5)).thenReturn(0);//donne une val au random pour piocher une mission
        Mockito.when(mockRand2.nextInt(3)).thenReturn(0);//donne une val au random pour choisir la mission
        rdmBot1.setRand(mockRand,mockRand2);//set les Random mock

        assertEquals(0,rdmBot1.getInventory().getMissions().size());
        rdmBot1.botPlay();
        assertEquals(1,rdmBot1.getInventory().getMissions().size());
        assertEquals(MissionType.PARCEL,rdmBot1.getInventory().getMissions().get(0).getMissionType());
    }

    @Test
    public void drawMissionPanda(){
        Random mockRand = mock(Random.class);
        Random mockRand2 = mock(Random.class);
        Mockito.when(mockRand.nextInt(5)).thenReturn(0);//donne une val au random pour piocher une mission
        Mockito.when(mockRand2.nextInt(3)).thenReturn(1);//donne une val au random pour choisir la mission
        rdmBot1.setRand(mockRand,mockRand2);//set les Random mock

        assertEquals(0,rdmBot1.getInventory().getMissions().size());
        rdmBot1.botPlay();
        assertEquals(1,rdmBot1.getInventory().getMissions().size());
        assertEquals(MissionType.PANDA,rdmBot1.getInventory().getMissions().get(0).getMissionType());
    }

    @Test
    public void drawMissionPeasant(){
        Random mockRand = mock(Random.class);
        Random mockRand2 = mock(Random.class);
        Mockito.when(mockRand.nextInt(5)).thenReturn(0);//donne une val au random pour piocher une mission
        Mockito.when(mockRand2.nextInt(3)).thenReturn(2);//donne une val au random pour choisir la mission
        rdmBot1.setRand(mockRand,mockRand2);//set les Random mock

        assertEquals(0,rdmBot1.getInventory().getMissions().size());
        rdmBot1.botPlay();
        assertEquals(1,rdmBot1.getInventory().getMissions().size());
        assertEquals(MissionType.PEASANT,rdmBot1.getInventory().getMissions().get(0).getMissionType());
    }

    @Test
    public void putCanal(){
        Random mockRand = mock(Random.class);
        board.placeParcel(new Parcel(ColorType.NO_COLOR), new Coordinate(1,-1,0));//ajoute une pièce ou mettre le canal
        board.placeParcel(new Parcel(ColorType.NO_COLOR), new Coordinate(0,-1,1));//ajoute une pièce ou mettre le canal
        Mockito.when(mockRand.nextInt(5)).thenReturn(1);//donne une val au random pour piocher une mission
        rdmBot1.setRand(mockRand,new Random());//set les Random mock

        assertEquals(0,board.getPlacedCanals().size());
        rdmBot1.botPlay();
        assertEquals(1,board.getPlacedCanals().size());
    }

    @Test
    public void putParcel(){
        Random mockRand = mock(Random.class);
        Mockito.when(mockRand.nextInt(5)).thenReturn(2);//donne une val au random pour piocher une mission
        rdmBot1.setRand(mockRand,new Random());//set les Random mock

        assertEquals(1,board.getPlacedParcels().size());//1 parcel (central)
        rdmBot1.botPlay();
        assertEquals(2,board.getPlacedParcels().size());//2 parcels (central + la parcel posée)
    }

    @Test
    public void movePanda(){
        Random mockRand = mock(Random.class);
        Mockito.when(mockRand.nextInt(5)).thenReturn(3);//donne une val au random pour piocher une mission
        Coordinate central = new Coordinate(0,0,0);
        board.placeParcel(new Parcel(ColorType.NO_COLOR), new Coordinate(1,-1,0));//ajoute une pièce ou mettre le panda
        rdmBot1.setRand(mockRand,new Random());//set les Random mock

        assertEquals(central,board.getPanda().getCoordinate());//Le Panda est au centre
        rdmBot1.botPlay();
        assertNotEquals(central,board.getPanda().getCoordinate());//Le Panda n'est plus au centre
    }

    @Test
    public void movePaesant(){
        Random mockRand = mock(Random.class);
        Mockito.when(mockRand.nextInt(5)).thenReturn(4);//donne une val au random pour piocher une mission
        Coordinate central = new Coordinate(0,0,0);
        Parcel parcel1Bamboo = new Parcel(ColorType.NO_COLOR);//parcel qui aura plus d'un bamboo pour recevoir le paysan
        parcel1Bamboo.addBamboo();//ajout d'un bamboo
        board.placeParcel(new Parcel(ColorType.NO_COLOR), new Coordinate(1,-1,0));//ajoute une pièce ou mettre le paysan
        rdmBot1.setRand(mockRand,new Random());//set les Random mock

        assertEquals(central,board.getPeasant().getCoordinate());//Le Paesant est au centre
        rdmBot1.botPlay();
        assertNotEquals(central,board.getPeasant().getCoordinate());//Le Paesant n'est plus au centre
    }
}