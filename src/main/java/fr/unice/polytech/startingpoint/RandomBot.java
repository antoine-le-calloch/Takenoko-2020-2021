package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RandomBot extends Bot {
    Random random;

    RandomBot(String botName) {
        super(botName);
        random = new Random();
    }

    //Action d'un bot pendant un tour
    @Override
    void play(Resource resource,Board board){
        int nb= random.nextInt(2);
        if (nb == 0) {
            drawMission(resource);
        }
        else {
            placeParcel(resource, board);
        }
    }

    //place une parcelle aleatoirement sur une case disponible
    @Override
    void placeParcel(Resource resource, Board board){
        ArrayList<Coordinate> possibleCoord = possibleCoordinates(board);
        Collections.shuffle(possibleCoord);
        board.putParcel(resource.drawParcel(), possibleCoord.get(0));
    }
}
