package fr.unice.polytech.startingpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ResourceTest {
    Resource resource;

    @BeforeEach
    public void initialize(){
        resource = new Resource();
    }

    @Test
    public void parcelDecrease(){
        resource.drawParcel();
        assertEquals(26,resource.getParcel().size());
    }
}