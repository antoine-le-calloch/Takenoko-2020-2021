package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

class Board {
    private final ArrayList<Parcel> placedParcels = new ArrayList<>();
    private final ArrayList<int[]> nextCoordinates = new ArrayList<>();
    private final ArrayList<int[]> offset = new ArrayList<>();

    Board(){
        placedParcels.add(new Parcel(new int[]{0,0,0}));
        initializeOffset();
    }


    private void initializeOffset(){
        offset.add(new int[]{0,-1,1});
        offset.add(new int[]{1,-1,0});
        offset.add(new int[]{1,0,-1});
        offset.add(new int[]{0,1,-1});
        offset.add(new int[]{-1,1,0});
        offset.add(new int[]{-1,0,1});
    }

    boolean putParcel(Parcel parcel,int[] coord){
        if(playableParcel(coord)){
            parcel.setPosition(coord);
            placedParcels.add(parcel);
            return true;
        }
        return false;
    }

    boolean playableParcel(int[] coord){
        int nbParcelAround = 0;
        for (Parcel placedParcel : placedParcels) {
            int norm = getNorm(coord,placedParcel.getCoordinates());
            if (norm == 0) {
                return false;
            } else if (norm == 2) {
                if (getNorm(new int[]{0,0,0},placedParcel.getCoordinates()) == 0) {
                    nbParcelAround++;
                }
                nbParcelAround++;
            }
        }
        return (nbParcelAround > 1);
    }

    int getNorm(int[] coord1, int[] coord2) {
        int norm = 0;
        for(int i = 0 ; i < coord1.length ; i++){
            norm += (coord1[i] - coord2[i])*(coord1[i] - coord2[i]);
        }
        return norm;
    }

    ArrayList<Parcel> getParcel(){
        return (ArrayList<Parcel>) placedParcels.clone();
    }

    ArrayList<int[]> possibleCoordinates(){
        initializeNextCoordinates();
        ArrayList<int[]> possibleCoordinates = new ArrayList<>();
        for(int[] coord : nextCoordinates){
            if(playableParcel(coord)){
                possibleCoordinates.add(coord);
            }
        }
        return possibleCoordinates;
    }

    private void initializeNextCoordinates(){
        for(Parcel parcel : placedParcels) {
            for(int[] offset : offset) {
                nextCoordinates.add(new int[] { parcel.getCoordinates()[0] + offset[0], parcel.getCoordinates()[1] + offset[1], parcel.getCoordinates()[2] + offset[2] });
            }
        }
    }
}
