package fr.unice.polytech.startingpoint;

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
}
