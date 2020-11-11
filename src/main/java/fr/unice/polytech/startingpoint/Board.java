package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

class Board {
    ArrayList<Parcel> placedParcels = new ArrayList<>();

    Board(){
        placedParcels.add(new Parcel(0,0,0));
    }

    boolean putParcel(Parcel parcel,int x, int y, int z){
        if(playableParcel(new Parcel(x,y,z))){
            parcel.setPosition(x,y,z);
            placedParcels.add(parcel);
            return true;
        }
        return false;
    }

    boolean playableParcel(Parcel parcel){
        int nbParcelAround = 0;
        for(Parcel placedParcel : placedParcels){
            int norm = parcel.getNorm(placedParcel);
            if(norm == 0){
                return false;
            }
            else if(norm == 1){
                if(placedParcel.getNorm(new Parcel(0,0,0)) == 0){
                    nbParcelAround++;
                }
                nbParcelAround ++;
            }
        }
        return (nbParcelAround > 1);
    }

    ArrayList<Parcel> getParcel(){
        return (ArrayList<Parcel>) placedParcels.clone();
    }
}
