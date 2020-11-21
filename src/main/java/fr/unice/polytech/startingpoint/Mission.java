package fr.unice.polytech.startingpoint;

import java.lang.String;

public class Mission {
    private final int points;
    private final String goal;

    Mission(int nbMission, int points, String goal) {
        this.points = points;
        this.goal = goal;
    }

    //Verifie si une mission est faite
    int checkMission(Board board){
        return checkMissionParcel(board)+checkMissionIrrigated(board);
    }

    //Verifie si une mission parcelle est faite
    int checkMissionParcel(Board board){
        if (goal.equals("triangle")) {
            for(Parcel parcel : board.getParcel()) {
                if (isPlaced(parcel.getCoordinates() ,new Coordinate(1,-1,0) ,board) && isPlaced(parcel.getCoordinates() ,new Coordinate(1,0,-1), board)){
                    return points;
                }
            }
        }
        if (goal.equals("ligne")) {
            for(Parcel parcel : board.getParcel()) {
                if (isPlaced(parcel.getCoordinates(), new Coordinate(0, 1, -1), board) && isPlaced(parcel.getCoordinates(), new Coordinate(0,2,-2), board)){
                    return points;
                }
            }
        }
        return 0;
    }
    int checkMissionIrrigated(Board board){
        for(Parcel parcel : board.getParcel()){
            if (parcel.getIrrigated()) {
                return points;
            }
        }
        return 0;
    }

    //Verifie si une parcelle est placé aux coordonnées qu'on lui donne additioné à un offset
    boolean isPlaced(Coordinate coord, Coordinate offset,Board board){
        for(Parcel parcel : board.getParcel()) {
            if (parcel.getCoordinates().equals(new Coordinate(coord,offset)))
                return true;
        }
        return false;
    }

    String getGoal(){ return goal;}
}
