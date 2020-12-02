package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Resource {
    private final List<Mission> deckMissionParcel = new ArrayList<>();
    private final List<Mission> deckMissionPanda = new ArrayList<>();
    private final List<Mission> deckMissionPeasant = new ArrayList<>();
    private final List<Parcel> deckParcel = new ArrayList<>();
    private final List<Canal> deckCanal = new ArrayList<>();

    Resource(){
        initializedeckParcel();
        initializedeckMission();
        initializedeckCanal();
    }

    //Création de toutes les parcelles et on les ajoute dans le deck parcelles
    private void initializedeckParcel(){
        int nbParcel = 26;
        deckParcel.add(new Parcel("red"));
        for (int i = 0; i < nbParcel / 2; i++){
            deckParcel.add(new Parcel("red"));
            deckParcel.add(new Parcel("blue"));
        }
    }

    //Création de toutes les missions et on les ajoute dans le deck missions
    private void initializedeckMission() {
        initializedeckMissionParcel();
        initializedeckMissionPanda();
        initializedeckMissionPeasant();
    }

    //Initialise le deck des mission parcelles
    private void initializedeckMissionParcel(){
        int nbMissionParcel = 15;
        for (int i = 0; i < nbMissionParcel / 5; i++){
            deckMissionParcel.add(new ParcelMission(2, "triangle","red"));
            deckMissionParcel.add(new ParcelMission(3, "triangle","blue"));
            deckMissionParcel.add(new ParcelMission(3, "line","red"));
            deckMissionParcel.add(new ParcelMission(4, "line","blue"));
            deckMissionParcel.add(new ParcelMission(4, "line","blue"));
        }
        Collections.shuffle(deckMissionParcel);
    }

    //Initialise le deck des mission panda
    private void initializedeckMissionPanda(){
        int nbMissionParcel = 15;
        deckMissionPanda.add(new PandaMission(3,"blue"));
        for (int i = 0; i < nbMissionParcel / 2; i++){
            deckMissionPanda.add(new PandaMission(3,"red"));
            deckMissionPanda.add(new PandaMission(3,"blue"));
        }
        Collections.shuffle(deckMissionPanda);
    }

    //Initialise le deck des mission peasant
    private void initializedeckMissionPeasant(){
        int nbMissionParcel = 15;
        for (int i = 0; i < nbMissionParcel; i++){
            deckMissionPeasant.add(new PeasantMission(4));
        }
        Collections.shuffle(deckMissionPeasant);
    }


    //Initialise le deck des canaux
    private void initializedeckCanal(){
        int nbCanal = 27;
        for (int i = 0; i < nbCanal; i++){
            deckCanal.add(new Canal());
        }
    }

    //Pioche une parcelle du deck
    Parcel drawParcel() {
        Parcel parcel = deckParcel.get(0);
        deckParcel.remove(parcel);
        return parcel;
    }

    //Pioche une mission du deck
    Mission drawMission(String type) {
        Mission mission = null;
        switch (type) {
            case "parcel":
                mission = deckMissionParcel.get(0);
                deckMissionParcel.remove(mission);
                return mission;
            case "panda":
                mission = deckMissionPanda.get(0);
                deckMissionPanda.remove(mission);
                return mission;
            case "peasant":
                mission = deckMissionPeasant.get(0);
                deckMissionPeasant.remove(mission);
                return mission;
            default:
                return mission;
        }
    }

    //Pioche un canal du deck
    Canal drawCanal(){
        Canal canal = deckCanal.get(0);
        deckCanal.remove(canal);
        return canal;
    }

    //Renvoie la liste du deck de parcelles
    List<Parcel> getParcel(){
        return deckParcel;
    }

    //Renvoie la liste du deck de canaux
    int getNbMissionParcel(){
        return deckMissionParcel.size();
    }

    //Renvoie la liste du deck de canaux
    List<Canal> getCanal(){
        return deckCanal;
    }

    //Renvoie la liste du deck de missions
    int getNbMission(){
        return deckMissionPanda.size() + deckMissionParcel.size() + deckMissionPeasant.size();
    }
}