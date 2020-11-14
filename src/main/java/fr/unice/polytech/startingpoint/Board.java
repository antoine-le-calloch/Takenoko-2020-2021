package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

class Board {
    private final ArrayList<Parcel> placedParcels = new ArrayList<>();

    Board(){
        placedParcels.add(new Parcel(new int[]{0,0,0}));
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

    static int getNorm(int[] coord1, int[] coord2) {
        int norm = 0;
        for(int i = 0 ; i < coord1.length ; i++){
            norm += (coord1[i] - coord2[i])*(coord1[i] - coord2[i]);
        }
        return norm;
    }

    ArrayList<Parcel> getParcel(){
        return (ArrayList<Parcel>) placedParcels.clone();
    }
}
