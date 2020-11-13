package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Collections;

class Resource {
    private ArrayList<Mission> deckMission = new ArrayList<>();
    private ArrayList<Parcel> deckParcel = new ArrayList<>();

    Resource(){
        int nbParcel = 27;
        for (int i = 0; i < nbParcel; i++){
            deckParcel.add(new Parcel());
        }
        int nbMission = 32;
        for (int i = 0; i < nbMission / 2 ; i++) {
            deckMission.add(new Mission(1, 2,"triangle"));
            deckMission.add(new Mission(2, 3, "ligne"));
        }
        Collections.shuffle(deckMission);
    }

    Parcel drawParcel() {
        Parcel parcel = deckParcel.get(0);
        deckParcel.remove(parcel);
        return parcel;
    }

    Mission drawMission() {
        Mission mission = deckMission.get(0);
        deckMission.remove(mission);
        return mission;
    }

    ArrayList<Parcel> getParcel(){
        return (ArrayList<Parcel>) deckParcel.clone();
    }
}
