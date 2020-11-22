package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

class RandomBot extends Bot {
    Random random;
    Resource resource;
    Board board;

    RandomBot(Resource resource, Board board) {
        super(resource, board);
        this.resource = resource;
        this.board = board;
        random = new Random();
    }

    //Action d'un bot pendant un tour
    @Override
    void Botplay(){
        int nb = random.nextInt(3);
        if (nb == 0) {
            drawMission();
        }
        else if (nb ==1) {
            if (possibleCoordinatesCanal().size() > 0)
                placeCanal(possibleCoordinatesCanal());
        }
        else {
            placeParcel(possibleCoordinatesParcel()); // placera une parcelle sur une case d'une coordonée qui est dans la liste
        }
    }



}
