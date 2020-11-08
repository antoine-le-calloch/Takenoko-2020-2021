package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

class Board {

    ArrayList<Parcel> placedParcel = new ArrayList<>();

    Board(){
    }

    ArrayList<Parcel> getParcel(){
        return placedParcel;
    }

    void putParcel(Parcel parcel){
        placedParcel.add(parcel);
    }
}
