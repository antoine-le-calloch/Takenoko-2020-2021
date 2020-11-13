package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

class Board {
    private ArrayList<Parcel> placedParcels = new ArrayList<>();
    private ArrayList<int[]> allCoordinates = new ArrayList<int[]>();
    
    Board(){
        placedParcels.add(new Parcel(new int[]{0,0,0}));
        initializeAllCoordinates();
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
        ArrayList<int[]> possibleCoordinates = new ArrayList<>();
        for(int[] coord : allCoordinates){
            if(playableParcel(coord)){
                possibleCoordinates.add(coord);
            }
        }
        return possibleCoordinates;
    }

    private void initializeAllCoordinates(){
        for ( int i = -13 ; i <= 3 ; i++){
            for ( int j = -13 ; j <= 13 ; j++){
                for ( int k = -13 ; k <= 13 ; k++){
                    if( (i + j + k) == 0 ){
                        allCoordinates.add(new int[] {i,j,k});
                    }
                }
            }
        }
    }
}
