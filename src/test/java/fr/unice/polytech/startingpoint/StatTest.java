package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StatTest {
    Stat stat1Game2P;
    Stat stat1000Game2P;
    Stat stat1000Game4P;
    int[] p1w;
    int[] p2w;
    int[] equality;

    int[] p1w4P;
    int[] p2w4P;

    @BeforeEach
    public void initialize(){
        stat1Game2P = new Stat(1,2);
        stat1000Game2P = new Stat(1000,2);
        stat1000Game4P = new Stat(1000,4);
        p1w = new int[]{2, 1};
        p2w = new int[]{1, 2};
        equality = new int[]{1, 1};

        p1w4P = new int[]{9, 5, 3, 1};
        p2w4P = new int[]{2, 7, 4, 3};
    }

    @Test
    public void p1Winner2P(){
        stat1Game2P.add(p1w);
        assertEquals("Joueur 1 : 100.0% win rate with a 2.0 points average\n" +
                "Joueur 2 : 0.0% win rate with a 1.0 points average\n",stat1Game2P.toString());
    }

    @Test
    public void p2Winner2P(){
        stat1Game2P.add(p2w);
        assertEquals("Joueur 1 : 0.0% win rate with a 1.0 points average\n" +
                "Joueur 2 : 100.0% win rate with a 2.0 points average\n",stat1Game2P.toString());
    }

    @Test
    public void equalityCase2P(){
        stat1Game2P.add(equality);
        assertEquals("Joueur 1 : 100.0% win rate with a 1.0 points average\n" +
                "Joueur 2 : 100.0% win rate with a 1.0 points average\n",stat1Game2P.toString());
    }

    @Test
    public void p1Win700GameLose3002P(){
        for (int i = 0; i < 700; i++) {
            stat1000Game2P.add(p1w);
        }
        for (int i = 0; i < 300; i++) {
            stat1000Game2P.add(p2w);
        }
        assertEquals("Joueur 1 : 70.0% win rate with a 1.7 points average\n" +
                "Joueur 2 : 30.0% win rate with a 1.3 points average\n",stat1000Game2P.toString());
    }

    @Test
    public void p1Win700GameLose3004P(){
        for (int i = 0; i < 700; i++) {
            stat1000Game4P.add(p1w4P);
        }
        for (int i = 0; i < 300; i++) {
            stat1000Game4P.add(p2w4P);
        }
        assertEquals("Joueur 1 : 70.0% win rate with a 6.9 points average\n" +
                             "Joueur 2 : 30.0% win rate with a 5.6 points average\n" +
                             "Joueur 3 : 0.0% win rate with a 3.3 points average\n" +
                             "Joueur 4 : 0.0% win rate with a 1.6 points average\n"
                             ,stat1000Game4P.toString());
    }
}
