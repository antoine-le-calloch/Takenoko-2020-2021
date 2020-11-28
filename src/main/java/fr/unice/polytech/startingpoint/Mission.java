package fr.unice.polytech.startingpoint;

import java.lang.String;

abstract class Mission {
    final int points;

    Mission(int points) {
        this.points = points;
    }

    //Verifie si une mission est faite
    abstract int checkMission(Board board, Bot bot);

    abstract String getGoal();

    abstract String getColor();
}
