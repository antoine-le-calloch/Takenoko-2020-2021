package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ResourceTest {
    @Test
    public void parcelDecrease(){
        Resource resource = new Resource();
        resource.drawParcel();
        assertEquals(13,resource.getParcel().size());
    }
}
