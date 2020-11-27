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
        int nbParcel = 26;
        deckParcel.add(new Parcel("red"));
        for (int i = 0; i < nbParcel / 2; i++){
            deckParcel.add(new Parcel("red"));
            deckParcel.add(new Parcel("blue"));
        }
    }

    //Creation de toutes les missions et on les ajoute dans le deck missions
    private void initializedeckMission() {
        int nbMission = 35;
        for (int i = 0; i < nbMission / 4; i++) {
            deckMission.add(new ParcelMission(2, "triangle","red"));
            deckMission.add(new ParcelMission(3, "triangle","blue"));
            deckMission.add(new ParcelMission(3, "line","red"));
            deckMission.add(new ParcelMission(4, "line","blue"));
            deckMission.add(new BambooMission(100000));
        }
        Collections.shuffle(deckMission);
    }

    private void initializedeckCanal(){
        int nbCanal = 27;
        for (int i = 0; i < nbCanal; i++){
            deckCanal.add(new Canal());
        }
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
