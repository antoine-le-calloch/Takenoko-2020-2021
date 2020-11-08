package fr.unice.polytech.startingpoint;

import javax.swing.*;
import java.util.ArrayList;

public class Mission {
    int iDMission;
    int points;

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

        if (iDMission==1 && board.placedParcel.size()!=0){



            return points;

        }

        return 0;


    }

}
