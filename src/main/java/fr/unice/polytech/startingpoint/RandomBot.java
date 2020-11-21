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
        int nb = random.nextInt(2);
        if (nb == 0) {
            drawMission();
        }
        else {
            placeParcel(possibleCoordinates()); // placera une parcelle sur une case d'une coordonée qui est dans la lite
        }
    }



}
