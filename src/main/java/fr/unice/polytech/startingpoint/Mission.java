package fr.unice.polytech.startingpoint;

import java.lang.String;

public class Mission {
    private final int iDMission;
    private final int points;
    private enum Type {parcel};
    private enum Forme {triangle, ligne};
    private final Type type;
    private final Forme forme;

    Mission(int nbMission, int points, String type, String forme) {
        this.iDMission = nbMission;
        this.points = points;
        this.type = Type.valueOf(type);
        this.forme = Forme.valueOf(forme);
    }

    int checkMission(Board board){
        if (type.toString().equals("parcel"))
            return checkMissionParcel(board);
        return 0;
    }

    int checkMissionParcel(Board board){
        if (forme.toString().equals("triangle")) {
            return points;
        }
        if (forme.toString().equals("ligne")) {
            return points;
        }
        return 0;
    }
}
