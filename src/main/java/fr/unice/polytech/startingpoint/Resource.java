package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

class Resource {


    ArrayList<Mission> deckMission = new ArrayList<>();
    ArrayList<Parcel> deckParcel = new ArrayList<>();

    Resource(){
        int nbParcel = 14;
        for (int i = 0; i < nbParcel ; i++){
            deckParcel.add(new Parcel());
        }
        int nbMission = 2;
        for (int i = 0; i < nbMission ; i++){
            deckMission.add(new Mission(1, 3));
        }
    }

    ArrayList<Parcel> getParcel(){
        return deckParcel;
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
}
