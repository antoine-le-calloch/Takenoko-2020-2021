package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Collections;

public class IntelligentBot extends Bot{
    IntelligentBot(String botName) {
        super(botName);
    }

    @Override
    void play(Resource resource,Board board){
        if (!haveMission())
            drawMission(resource);
        placeParcel(resource,board);
    }

    boolean haveMission(){
        return inventoryMission.size() > 1;
    }


    //place une parcelle sur une case pour finir une ligne / ou pose aleatoirement
    void placeParcel(Resource resource, Board board){
        ArrayList<Coordinate> possibleCoord = possibleCoordinates(board);

        /*
        for (Coordinate coordinate : possibleCoord) {
            if (board.getParcel().contains())
            si la liste placedparcel possède une parcelle qui a comme coordonnées : coordinate + (1,-1,0)
                                                                                  et coordinate + (2,-2,0)
            alors, on place la parcelle sur cette coordonnées
        }*/


        board.putParcel(resource.drawParcel(), possibleCoord.get(0));
    }


}
