package fr.unice.polytech.startingpoint;

import java.lang.String;
import java.util.Arrays;

public class Mission {
    private final int iDMission;
    private final int points;
    private final String forme;

    Mission(int nbMission, int points, String forme) {
        this.iDMission = nbMission;
        this.points = points;
        this.forme = forme;
    }

    //Verifie si une mission est faite
    int checkMission(Board board){
        return checkMissionParcel(board);
    }

    //Verifie si une mission parcelle est faite
    int checkMissionParcel(Board board){
        if (forme.equals("triangle")) {
            for(Parcel parcel : board.getParcel()) {
                if (isPlaced(parcel.getCoordinates() ,new Coordinate(1,-1,0) ,board) && isPlaced(parcel.getCoordinates() ,new Coordinate(1,0,-1), board)){
                    return points;
                }
            }
        }
        if (forme.equals("ligne")) {
            for(Parcel parcel : board.getParcel()) {
                if (isPlaced(parcel.getCoordinates(), new Coordinate(0, 1, -1), board) && isPlaced(parcel.getCoordinates(), new Coordinate(0,2,-2), board)){
                    return points;
                }
            }
        }
        return 0;
    }

    //Verifie si une parcelle est placé aux coordonnées qu'on lui donne additioné à un offset
    boolean isPlaced(Coordinate coord, Coordinate offset,Board board){
        for(Parcel parcel : board.getParcel()) {
            if (parcel.getCoordinates().isEqualTo(new Coordinate(coord,offset)))
                return true;
        }
        return false;
    }
}
