package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

class IntelligentBot extends Bot{
    Resource resource;
    Board board;

    IntelligentBot(Resource resource, Board board) {
        super(resource, board);
        this.resource = resource;
        this.board = board;
    }

    @Override
    void Botplay(){
        if (!haveMission())
            drawMission();
        placeParcel();
    }

    boolean haveMission(){
        return inventoryMission.size() > 1;
    }


    //place une parcelle sur une case pour finir une ligne / ou pose aleatoirement
    void placeParcel(){
        ArrayList<Coordinate> possibleCoord = possibleCoordinates();

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
