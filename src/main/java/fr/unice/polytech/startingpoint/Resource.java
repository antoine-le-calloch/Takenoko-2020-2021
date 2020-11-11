package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

class Resource {
    private ArrayList<Mission> deckMission = new ArrayList<>();
    private ArrayList<Parcel> deckParcel = new ArrayList<>();
    private final int nbParcel = 27;
    private final int nbMission = 46;

    Resource(){
        for (int i = 0; i < nbParcel ; i++){
            deckParcel.add(new Parcel());
        }
        for (int i = 0; i < nbMission ; i++){
            deckMission.add(new Mission(1, 3));
        }
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
