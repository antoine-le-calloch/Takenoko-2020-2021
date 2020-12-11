package fr.unice.polytech.startingpoint.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class RulesTest {
    Game game;

    @BeforeEach
    public void initialize(){
        game = new Game();
    }

    @Test
    void parcelNonexistentSoNoCanal(){
        assertFalse(game.getRules().isPlayableCanal(new Coordinate(0,-1,1),new Coordinate(1,-1,0)));
        assertFalse(game.getRules().isPlayableCanal(new Coordinate(0,0,0),new Coordinate(1,-1,0)));
    }
}
