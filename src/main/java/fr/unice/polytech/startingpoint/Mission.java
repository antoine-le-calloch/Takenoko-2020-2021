package fr.unice.polytech.startingpoint;

import java.lang.String;

public class Mission {
    private final int points;
    private final String goal;

    Mission(int points, String goal) {
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
                if (board.checkTriangle())
                    return points;
            case "ligne":
                if (board.checkLine())
                    return points;
            default:
                return 0;
        }
    }
}
