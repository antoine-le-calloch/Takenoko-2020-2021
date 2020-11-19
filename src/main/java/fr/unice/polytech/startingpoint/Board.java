package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

class Board {
    private final ArrayList<Parcel> placedParcels = new ArrayList<>();

    Board(){
        placedParcels.add(new Parcel(new Coordinate(0,0,0)));
    }

    //Place une parcelle sur le board (quand cela est possible)
    boolean putParcel(Parcel parcel,Coordinate coord){
        if(playableParcel(coord)){
            parcel.setCoordinates(coord);
            placedParcels.add(parcel);
            if (Coordinate.getNorm(parcel.getCoordinates(),new Coordinate(0,0,0) )==2){
                parcel.setIrrigated();
            }
            return true;
        }
        return false;
    }

    //verifie si on peut poser une parcelle sur le board
    boolean playableParcel(Coordinate coord){
        int nbParcelAround = 0;
        for (Parcel placedParcel : placedParcels) {
            int norm = Coordinate.getNorm(coord,placedParcel.getCoordinates());
            if (norm == 0) {
                return false;
            }
            else if (norm == 2) {
                if (Coordinate.getNorm(new Coordinate(0,0,0),placedParcel.getCoordinates()) == 0) {
                    nbParcelAround++;
                }
                nbParcelAround++;
            }
        }
        return (nbParcelAround > 1);
    }

    ArrayList<Parcel> getParcel(){
        return (ArrayList<Parcel>) placedParcels.clone();
    }
}
