package fr.unice.polytech.startingpoint;

public class Mission {
    private int iDMission;
    private int points;

    Mission(int nbMission, int points) {
        this.iDMission = nbMission;
        this.points = points;
    }

    int getPoints() {
        return points;
    }

    int getIDMission() {
        return iDMission;
    }

    int checkMission(Board board){
        if (iDMission==1 && board.getParcel().size()!=1){
            return points;
        }
        return 0;
    }
}
