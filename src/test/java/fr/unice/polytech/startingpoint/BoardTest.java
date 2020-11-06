package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    @Test
    public void parcelIncrease(){
        Resource resource = new Resource();
        Board board = new Board();
        board.putParcel(resource.drawParcel());
        assertEquals(1,board.getParcel().size());
    }
}
