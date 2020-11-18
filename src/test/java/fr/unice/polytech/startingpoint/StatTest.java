package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StatTest {
    Stat stat;
    int[] j1w;
    int[] j2w;
    int[] equality;

    @BeforeEach
    public void initialize(){
        stat = new Stat();
        j1w = new int[]{2, 1};
        j2w = new int[]{1, 2};
        equality = new int[]{1, 1};
    }

    @Test
    public void playerOneWinner(){
        assertEquals(stat.getWinner(j1w),"Joueur 1 gagne");
    }

    @Test
    public void playerTwoWinner(){
        assertEquals(stat.getWinner(j2w),"Joueur 2 gagne");
    }

    @Test
    public void equalityCase(){
        assertEquals(stat.getWinner(equality),"Egalité");
    }
}
