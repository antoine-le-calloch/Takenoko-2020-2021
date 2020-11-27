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

    String getGoal(){
        return goalForm;
    }

    int checkMissionParcel(Board board) {
        switch (goalForm) {
            case "triangle":
                if (checkFormIrrigateWithColor(board,0,1))
                    return points;
            case "ligne":
                if (checkFormIrrigateWithColor(board,2,5))
                    return points;
            default:
                return 0;
        }
    }

    //retourne vrai si il y a un triangle sur le plateau
    boolean checkFormIrrigateWithColor(Board board, int offset1, int offset2) {
        for (Parcel parcel : board.getPlacedParcels().values()) {
            Coordinate coord1 = new Coordinate(parcel.getCoordinates(), Coordinate.offSets().get(offset1));
            Coordinate coord2 = new Coordinate(parcel.getCoordinates(), Coordinate.offSets().get(offset2));
            if (board.getIrrigatedParcels().contains(coord1) && board.getIrrigatedParcels().contains(coord2)) {
                if (parcel.getColor().equals(goalColor) && board.getParcelByCo(coord1).getColor().equals(goalColor) && board.getParcelByCo(coord2).getColor().equals(goalColor))
                    return true;
            }
        }
        return false;
    }
}
