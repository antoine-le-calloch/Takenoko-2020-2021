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
        return checkMissionParcel(board);
    }

    String getGoal(){ return goal;}



    int checkMissionParcel(Board board) {
        switch (goal) {
            case "triangle":
                return checkTriangle(board);
            case "ligne":
                return checkLine(board);
            default: return 0;
        }
    }



    int checkLine(Board board) {
        for (Parcel parcel : board.getPlacedParcels()) {
            if (   board.irrigatedParcels.contains(new Coordinate(parcel.getCoordinates(),new Coordinate(0,1,-1))) &&
                    board.irrigatedParcels.contains(new Coordinate(parcel.getCoordinates(),new Coordinate(0,2,-2)))) {
                return points;
            }
        }
        return 0;
    }

    int checkTriangle(Board board) {
        for (Parcel parcel : board.getPlacedParcels()) {
            if (   board.irrigatedParcels.contains(new Coordinate(parcel.getCoordinates(),new Coordinate(1,-1,0))) &&
                    board.irrigatedParcels.contains(new Coordinate(parcel.getCoordinates(),new Coordinate(1,0,-1)))) {
                return points;
            }
        }
        return 0;
    }


    //Verifie si une parcelle est placé aux coordonnées qu'on lui donne additioné à un offset
    boolean isPlaced(Coordinate coord, Coordinate offset,Board board){
        for(Parcel parcel : board.getPlacedParcels()) {
            if (parcel.getCoordinates().equals(new Coordinate(coord,offset)))
                return true;
        }
        return false;
    }
}
