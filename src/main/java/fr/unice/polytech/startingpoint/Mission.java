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
        for (Parcel parcel : board.getParcel()) {
            if (board.checkGoal(goal,parcel.getCoordinates()))
                return points;
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


    String getGoal(){ return goal;}
}
