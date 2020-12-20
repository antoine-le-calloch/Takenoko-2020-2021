package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.CharacterType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CharacterTest {
    Character panda;
    Character peasant;

    @BeforeEach
    void setUp(){
        panda = new Character(CharacterType.PANDA);
        peasant = new Character(CharacterType.PEASANT);
    }

    @Test
    void samePanda(){
        assertEquals(panda,panda);
        assertEquals(panda.getCharacterType(),new Character(CharacterType.PANDA).getCharacterType());
        assertNotEquals(panda,new Character(CharacterType.PANDA));
        assertNotEquals(panda,null);
    }

    @Test
    void samePeasant(){
        assertEquals(peasant,peasant);
        assertEquals(peasant.getCharacterType(),new Character(CharacterType.PEASANT).getCharacterType());
        assertNotEquals(peasant,new Character(CharacterType.PEASANT));
        assertNotEquals(peasant,null);
    }

    @Test
    void moveCharacter(){
        assertEquals(new Coordinate(),panda.getCoordinate());
        assertEquals(new Coordinate(),peasant.getCoordinate());

        panda.setCoordinate(new Coordinate(1,-1,0));
        peasant.setCoordinate(new Coordinate(-1,1,0));

        assertEquals(new Coordinate(1,-1,0),panda.getCoordinate());
        assertEquals(new Coordinate(-1,1,0),peasant.getCoordinate());
    }
}