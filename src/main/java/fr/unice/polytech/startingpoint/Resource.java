package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Collections;

class Resource {
    private final ArrayList<Mission> deckMission = new ArrayList<>();
    private final ArrayList<Parcel> deckParcel = new ArrayList<>();
    private final ArrayList<Canal> deckCanal=new ArrayList<>();



    Resource(){
        initializedeckParcel();
        initializedeckMission();
        initializedeckCanal();
    }

    //Creation de toutes les parcelles et on les ajoute dans le deck parcelles
    private void initializedeckParcel(){
        int nbParcel = 27;
        for (int i = 0; i < nbParcel; i++){
            deckParcel.add(new Parcel());
        }
    }

    //Creation de toutes les missions et on les ajoute dans le deck missions
    private void initializedeckMission() {
        int nbMission = 32;
        for (int i = 0; i < nbMission / 4; i++) {
            deckMission.add(new Mission(1, 2, "triangle"));
            deckMission.add(new Mission(2, 3, "line"));

        }
        Collections.shuffle(deckMission);
    }

    //permet de piocher une parcelle du deck, la parcelle est enlevé du deck
    Parcel drawParcel() {
        Parcel parcel = deckParcel.get(0);
        deckParcel.remove(parcel);
        return parcel;
    }

    //permet de piocher une mission du deck, la mission est enlevé du deck
    Mission drawMission() {
        Mission mission = deckMission.get(0);
        deckMission.remove(mission);
        return mission;
    }


    private void initializedeckCanal(){
        int nbCanal = 27;
        for (int i = 0; i < nbCanal; i++){
            deckCanal.add(new Canal());
        }
    }

    Canal drawCanal(){
        Canal canal = deckCanal.get(0);
        deckCanal.remove(canal);
        return canal;
    }

    ArrayList<Parcel> getParcel(){
        return deckParcel;
    }
    ArrayList<Canal> getCanal(){
        return deckCanal;
    }
    ArrayList<Mission> getMission(){
        return deckMission;
    }
}
