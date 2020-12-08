package fr.unice.polytech.startingpoint;

import fr.unice.polytech.startingpoint.Bot.Bot;
import fr.unice.polytech.startingpoint.Bot.PandaBot;
import fr.unice.polytech.startingpoint.Bot.PeasantBot;
import fr.unice.polytech.startingpoint.Game.Character;
import fr.unice.polytech.startingpoint.Game.*;
import fr.unice.polytech.startingpoint.Type.*;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

public class PeasantTest {
    Character peasant1;
    Board board;
    Parcel parcel1;

    @BeforeEach void setUp(){
        peasant1 = new Character(CharacterType.PEASANT);
        board = new Board();
        parcel1 = new Parcel(ColorType.RED);
    }
}
