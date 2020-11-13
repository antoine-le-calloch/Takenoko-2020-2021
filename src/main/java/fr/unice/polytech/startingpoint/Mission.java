package fr.unice.polytech.startingpoint;

import java.lang.String;
import java.util.Arrays;

public class Mission {
    private final int iDMission;
    private final int points;
    private final String forme;

    Mission(int nbMission, int points, String forme) {
        this.iDMission = nbMission;
        this.points = points;
        this.forme = forme;
    }

    int checkMission(Board board){
        return checkMissionParcel(board);
    }

    int checkMissionParcel(Board board){
        if (forme.equals("triangle")) {
            for(Parcel parcel : board.getParcel()) {
                if (isPlaced(parcel.getCoordinates() ,new int[]{1,-1,0} ,board) && isPlaced(parcel.getCoordinates() ,new int[]{1,0,-1} ,board)){
                    return points;
                }
            }
        }
        if (forme.equals("ligne")) {
            for(Parcel parcel : board.getParcel()) {
                if (isPlaced(parcel.getCoordinates(), new int[]{0, 1, -1}, board) && isPlaced(parcel.getCoordinates(), new int[]{0, 2, -2}, board)){
                    return points;
                }
            }
        }
        return 0;
    }

    boolean isPlaced(int[] coord, int[] offset,Board board){
        int [] parcelcoord = new int[]{coord[0]+offset[0],coord[1]+offset[1],coord[2]+offset[2]};
        for(Parcel parcel : board.getParcel()) {
            if (Arrays.equals(parcel.getCoordinates(), parcelcoord))
                return true;
        }
        return false;
    }
}
