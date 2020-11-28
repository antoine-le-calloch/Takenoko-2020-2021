package fr.unice.polytech.startingpoint;

import java.util.SortedSet;

class Canal {
    private Coordinate coordinates1;
    private Coordinate coordinates2;

    Canal(){}

    //Renvoie le canal après avoir fixé les coordonnées avec les coordonnées passées en paramètre
    Canal setCoordinates(Coordinate coord1, Coordinate coord2) {
        coordinates1 = coord1;
        coordinates2 = coord2;
        return this;
    }

    //Renvoie un SortedSet des coordonnées du canal
    SortedSet<Coordinate> getCoordinatesCanal(){
        return Coordinate.getSortedSet(coordinates1 , coordinates2);
    }
}
