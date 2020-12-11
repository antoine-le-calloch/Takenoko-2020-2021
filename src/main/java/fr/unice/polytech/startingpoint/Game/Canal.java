package fr.unice.polytech.startingpoint.game;

import java.util.*;

/**
 * Classe representant un canal
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */


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
