package fr.unice.polytech.startingpoint;

import java.lang.String;

public class Mission {
    private final int points;
    private final String goalForm;
    private final String goalColor;

    Mission(int points, String goalForm, String goalColor) {
        this.points = points;
        this.goalForm = goalForm;
        this.goalColor = goalColor;
    }

    //Verifie si une mission est faite
    int checkMission(Board board){
        return checkMissionParcel(board);
    }

    String getGoal(){ return goalForm;}



    int checkMissionParcel(Board board) {
        switch (goalForm) {
            case "triangle":
                if (checkTriangle(board))
                    return points;
            case "ligne":
                if (checkLine(board))
                    return points;
            default:
                return 0;
        }
    }

    //retourne vrai si il y a un triangle sur le plateau
    boolean checkTriangle(Board board) {
        for (Parcel parcel : board.getPlacedParcels()) {
            if (board.irrigatedParcels.contains(new Coordinate(parcel.getCoordinates(),Coordinate.offSets().get(0))) &&
                    board.irrigatedParcels.contains(new Coordinate(parcel.getCoordinates(),Coordinate.offSets().get(1)))) {
                return true;
            }
        }
        return false;
    }

    //retourne faux si il y a un triangle sur le plateau
    boolean checkLine(Board board) {
        for (Parcel parcel : board.getPlacedParcels()) {
            if (board.irrigatedParcels.contains(new Coordinate(parcel.getCoordinates(),Coordinate.offSets().get(2))) &&
                    board.irrigatedParcels.contains(new Coordinate(parcel.getCoordinates(),Coordinate.offSets().get(5)))) {
                return true;
            }
        }
        return false;
    }


}
