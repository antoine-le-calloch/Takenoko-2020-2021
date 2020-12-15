
package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.bot.RandomBot;
import fr.unice.polytech.startingpoint.exception.BadPlaceParcelException;
import fr.unice.polytech.startingpoint.type.BotType;
import fr.unice.polytech.startingpoint.type.ColorType;
import fr.unice.polytech.startingpoint.type.MissionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    Game game;
    RandomBot rdmBot1;
    Board board;
    Parcel parcel1;
    Resource resource;
    Rules rules;
    PlayerData playerData;


    @BeforeEach
    public void setUp() {

        game = new Game(new BotType[]{BotType.RANDOM}, 3);
        rules = game.getRules();
        playerData = game.getPlayerData();
        board = game.getBoard();
        parcel1 = new Parcel(ColorType.NO_COLOR);
        rdmBot1 = (RandomBot) game.getPlayerData().getBot();
        resource = game.getResource();
    }

    @Test
    public void drawMissionParcel() {
        Random mockRand = mock(Random.class);
        Random mockRand2 = mock(Random.class);
        Mockito.when(mockRand.nextInt(5)).thenReturn(0);//donne une val au random pour piocher une mission
        Mockito.when(mockRand2.nextInt(3)).thenReturn(0);//donne une val au random pour choisir la mission
        rdmBot1.setRand(mockRand, mockRand2);//set les Random mock

        assertEquals(0, playerData.getMissions().size());
        rdmBot1.botPlay();
        assertEquals(1, playerData.getMissions().size());

        assertEquals(MissionType.PARCEL, playerData.getMissions().get(0).missionType);
    }


    @Test
    public void drawMissionPanda() {
        Random mockRand = mock(Random.class);
        Random mockRand2 = mock(Random.class);
        Mockito.when(mockRand.nextInt(5)).thenReturn(0);//donne une val au random pour piocher une mission
        Mockito.when(mockRand2.nextInt(3)).thenReturn(1);//donne une val au random pour choisir la mission
        rdmBot1.setRand(mockRand, mockRand2);//set les Random mock

        assertEquals(0, playerData.getMissions().size());
        rdmBot1.botPlay();
        assertEquals(1, playerData.getMissions().size());
        assertEquals(MissionType.PANDA, playerData.getMissions().get(0).missionType);
    }

    @Test
    public void drawMissionPeasant() {
        Random mockRand = mock(Random.class);
        Random mockRand2 = mock(Random.class);
        Mockito.when(mockRand.nextInt(5)).thenReturn(0);//donne une val au random pour piocher une mission
        Mockito.when(mockRand2.nextInt(3)).thenReturn(2);//donne une val au random pour choisir la mission
        rdmBot1.setRand(mockRand, mockRand2);//set les Random mock


        assertEquals(0, playerData.getMissions().size());
        rdmBot1.botPlay();
        assertEquals(1, playerData.getMissions().size());
        assertEquals(MissionType.PEASANT, playerData.getMissions().get(0).missionType);
    }

    @Test
    public void putCanal() throws BadPlaceParcelException {
        Random mockRand = mock(Random.class);
        board.placeParcel(new Parcel(ColorType.NO_COLOR), new Coordinate(1, -1, 0));//ajoute une pièce ou mettre le canal
        board.placeParcel(new Parcel(ColorType.NO_COLOR), new Coordinate(0, -1, 1));//ajoute une pièce ou mettre le canal
        Mockito.when(mockRand.nextInt(5)).thenReturn(1);//donne une val au random pour piocher une mission
        rdmBot1.setRand(mockRand, new Random());//set les Random mock

        assertEquals(0, board.getPlacedCanals().size());
        rdmBot1.botPlay();
        assertEquals(1, board.getPlacedCanals().size());
    }

}