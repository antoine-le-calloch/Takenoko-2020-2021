package fr.unice.polytech.startingpoint.Game;

import java.util.*;

public class Canal {
    private Coordinate coordinates1;
    private Coordinate coordinates2;

    public Canal(){}

    //Renvoie le canal après avoir fixé les coordonnées avec les coordonnées passées en paramètre
    public Canal setCoordinates(Coordinate coord1, Coordinate coord2) {
        coordinates1 = coord1;
        coordinates2 = coord2;
        return this;
    }

    //Renvoie un SortedSet des coordonnées du canal
    public SortedSet<Coordinate> getCoordinatesCanal(){
        return Coordinate.getSortedSet(coordinates1 , coordinates2);
    }
}
