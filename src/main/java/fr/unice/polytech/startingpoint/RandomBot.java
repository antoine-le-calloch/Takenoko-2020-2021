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
            placeParcel();
        }
    }

    //place une parcelle aleatoirement sur une case disponible
    @Override
    void placeParcel(){
        ArrayList<Coordinate> possibleCoord = possibleCoordinates();
        Collections.shuffle(possibleCoord);
        board.putParcel(resource.drawParcel(), possibleCoord.get(0));
    }
}
