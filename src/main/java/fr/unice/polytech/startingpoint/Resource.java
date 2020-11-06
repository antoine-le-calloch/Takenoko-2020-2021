package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

class Resource {

    ArrayList<Parcel> deckParcel = new ArrayList<>();

    Resource(){
        int nbParcel = 14;
        for (int i = 0; i < nbParcel ; i++){
            deckParcel.add(new Parcel());
        }
    }

    public Parcel getParcel() {
        Parcel parcel = deckParcel.get(0);
        deckParcel.remove(parcel);
        return parcel;
    }
}
