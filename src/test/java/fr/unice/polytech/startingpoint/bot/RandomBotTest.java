package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.bot.RandomBot;
import fr.unice.polytech.startingpoint.game.Game;
import fr.unice.polytech.startingpoint.game.board.*;
import fr.unice.polytech.startingpoint.type.ColorType;
import fr.unice.polytech.startingpoint.type.ImprovementType;

import fr.unice.polytech.startingpoint.type.WeatherType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;

public class RandomBotTest {/*
    Game game;
    RandomBot rdmBot1;
    Board board;
    Resource resource;
    BoardRules boardRules;
    RandomStrat randomStrat;


    @BeforeEach
    public void setUp() {

        game = new Game();
        boardRules = game.getRules();
        board = game.getBoard();
        rdmBot1 = (RandomBot) game.getPlayerData().getBot();
        resource = game.getResource();
        randomStrat = new RandomStrat(rdmBot1);
    }

    @Test
    public void drawMissionParcel() {
        Random mockRand = mock(Random.class);
        Random mockRand2 = mock(Random.class);
        Mockito.when(mockRand.nextInt(5)).thenReturn(0);//donne une val au random pour piocher une mission
        Mockito.when(mockRand2.nextInt(3)).thenReturn(0);//donne une val au random pour choisir la mission
        randomStrat.setRand(mockRand, mockRand2);//set les Random mock

        assertEquals(1, game.getGameInteraction().getInventoryParcelMissions().size());
        randomStrat.stratOneTurn(WeatherType.NO_WEATHER, );
        assertEquals(2, game.getGameInteraction().getInventoryParcelMissions().size());
    }


    @Test
    public void drawMissionPanda() {
        Random mockRand = mock(Random.class);
        Random mockRand2 = mock(Random.class);
        Mockito.when(mockRand.nextInt(5)).thenReturn(0);//donne une val au random pour piocher une mission
        Mockito.when(mockRand2.nextInt(3)).thenReturn(1);//donne une val au random pour choisir la mission
        randomStrat.setRand(mockRand, mockRand2);//set les Random mock

        assertEquals(1, game.getGameInteraction().getInventoryPandaMissions().size());
        randomStrat.stratOneTurn(WeatherType.NO_WEATHER, );

        assertEquals(2, game.getGameInteraction().getInventoryPandaMissions().size());
    }

    @Test
    public void drawMissionPeasant() {
        Random mockRand = mock(Random.class);
        Random mockRand2 = mock(Random.class);
        Mockito.when(mockRand.nextInt(5)).thenReturn(0);//donne une val au random pour piocher une mission
        Mockito.when(mockRand2.nextInt(3)).thenReturn(2);//donne une val au random pour choisir la mission
        randomStrat.setRand(mockRand, mockRand2);//set les Random mock

        assertEquals(1, game.getGameInteraction().getInventoryPeasantMissions().size());
        randomStrat.stratOneTurn(WeatherType.NO_WEATHER, );

        assertEquals(2, game.getGameInteraction().getInventoryPeasantMissions().size());
    }

    @Test
    public void putCanal(){
        Random mockRand = mock(Random.class);
        board.placeParcel(new Parcel(ColorType.NO_COLOR, ImprovementType.NOTHING), new Coordinate(1, -1, 0));//ajoute une pièce ou mettre le canal
        board.placeParcel(new Parcel(ColorType.NO_COLOR,ImprovementType.NOTHING), new Coordinate(0, -1, 1));//ajoute une pièce ou mettre le canal
        Mockito.when(mockRand.nextInt(5)).thenReturn(1);//donne une val au random pour piocher une mission
        randomStrat.setRand(mockRand, new Random());//set les Random mock

        assertEquals(0, board.getPlacedCanals().size());
        randomStrat.stratOneTurn(WeatherType.NO_WEATHER, );
        assertEquals(1, board.getPlacedCanals().size());
    }

    @Test
    public void putParcel(){
        Random mockRand = mock(Random.class);
        Mockito.when(mockRand.nextInt(5)).thenReturn(2);//donne une val au random pour piocher une mission
        randomStrat.setRand(mockRand,new Random());//set les Random mock

        assertEquals(1,board.getPlacedParcels().size());//1 parcel (central)
        randomStrat.stratOneTurn(WeatherType.NO_WEATHER, );
        assertEquals(2,board.getPlacedParcels().size());//2 parcels (central + la parcel posée)
    }

    @Test
    public void movePanda(){
        Random mockRand = mock(Random.class);
        Mockito.when(mockRand.nextInt(5)).thenReturn(3);//donne une val au random pour piocher une mission
        Coordinate central = new Coordinate(0,0,0);
        board.placeParcel(new Parcel(), new Coordinate(1,-1,0));//ajoute une pièce ou mettre le panda
        randomStrat.setRand(mockRand,new Random());//set les Random mock

        assertEquals(central,board.getPandaCoordinate());//Le Panda est au centre
        randomStrat.stratOneTurn(WeatherType.NO_WEATHER, );
        assertNotEquals(central,board.getPandaCoordinate());//Le Panda n'est plus au centre

    }

    @Test
    public void movePeasant(){
        Random mockRand = mock(Random.class);
        Mockito.when(mockRand.nextInt(5)).thenReturn(4);//donne une val au random pour piocher une mission
        Coordinate central = new Coordinate(0,0,0);
        Parcel parcel1Bamboo = new Parcel();//parcel qui aura plus d'un bamboo pour recevoir le paysan
        parcel1Bamboo.addBamboo();//ajout d'un bamboo
        board.placeParcel(new Parcel(), new Coordinate(1,-1,0));//ajoute une pièce ou mettre le paysan
        randomStrat.setRand(mockRand,new Random());//set les Random mock
        assertEquals(central,board.getPeasantCoordinate());//Le Paesant est au centre
        randomStrat.stratOneTurn(WeatherType.NO_WEATHER, );
        assertNotEquals(central,board.getPeasantCoordinate());//Le Paesant n'est plus au centre
    }*/
}
