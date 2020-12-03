package fr.unice.polytech.startingpoint.Game;


import fr.unice.polytech.startingpoint.Type.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Resource {
    private final List<Mission> deckMissionParcel = new ArrayList<>();
    private final List<Mission> deckMissionPanda = new ArrayList<>();
    private final List<Mission> deckMissionPeasant = new ArrayList<>();
    private final List<Parcel> deckParcel = new ArrayList<>();
    private final List<Canal> deckCanal = new ArrayList<>();

    public Resource(){
        initializeDeckParcel();
        initializeDeckMission();
        initializeDeckCanal();
    }

    //Création de toutes les parcelles et on les ajoute dans le deck parcelles
    private void initializeDeckParcel(){
        int nbParcel = 26;
        for (int i = 0; i < nbParcel / 2; i++){
            deckParcel.add(new Parcel(ColorType.RED));
            deckParcel.add(new Parcel(ColorType.BLUE));
        }
    }

    //Création de toutes les missions et on les ajoute dans le deck missions
    private void initializeDeckMission() {
        initializeDeckMissionParcel();
        initializeDeckMissionPanda();
        initializeDeckMissionPeasant();
    }

    //Initialise le deck des mission parcelles
    private void initializeDeckMissionParcel(){
        int nbMissionParcel = 15;
        for (int i = 0; i < nbMissionParcel / 5; i++){
            deckMissionParcel.add(new ParcelMission(2, FormType.TRIANGLE, ColorType.RED));
            deckMissionParcel.add(new ParcelMission(3, FormType.TRIANGLE, ColorType.BLUE));
            deckMissionParcel.add(new ParcelMission(3, FormType.LINE, ColorType.RED));
            deckMissionParcel.add(new ParcelMission(4, FormType.LINE, ColorType.BLUE));
            deckMissionParcel.add(new ParcelMission(4, FormType.LINE, ColorType.BLUE));
        }
        Collections.shuffle(deckMissionParcel);
    }

    //Initialise le deck des mission panda
    private void initializeDeckMissionPanda(){
        int nbMissionParcel = 15;
        deckMissionPanda.add(new PandaMission(3, ColorType.BLUE));
        for (int i = 0; i < nbMissionParcel / 2; i++){
            deckMissionPanda.add(new PandaMission(3, ColorType.RED));
            deckMissionPanda.add(new PandaMission(3, ColorType.BLUE));
        }
        Collections.shuffle(deckMissionPanda);
    }

    //Initialise le deck des mission peasant
    private void initializeDeckMissionPeasant(){
        int nbMissionParcel = 15;
        for (int i = 0; i < nbMissionParcel; i++){
            deckMissionPeasant.add(new PeasantMission(4, ColorType.RED));
        }
        Collections.shuffle(deckMissionPeasant);
    }


    //Initialise le deck des canaux
    private void initializeDeckCanal(){
        int nbCanal = 27;
        for (int i = 0; i < nbCanal; i++){
            deckCanal.add(new Canal());
        }
    }

    //Pioche une parcelle du deck
    public Parcel drawParcel() {
        Parcel parcel = deckParcel.get(0);
        deckParcel.remove(parcel);
        return parcel;
    }

    //Pioche une mission du deck
    public Mission drawMission(MissionType type){
        Mission mission = null;
        switch (type) {
            case PARCEL:
                mission = deckMissionParcel.get(0);
                deckMissionParcel.remove(0);
                return mission;
            case PANDA:
                mission = deckMissionPanda.get(0);
                deckMissionPanda.remove(0);
                return mission;
            case PEASANT:
                mission = deckMissionPeasant.get(0);
                deckMissionPeasant.remove(0);
                return mission;
        }
        return mission;
    }

    //Pioche un canal du deck
    public Canal drawCanal(){
        Canal canal = deckCanal.get(0);
        deckCanal.remove(canal);
        return canal;
    }

    //Renvoie la liste du deck de parcelles
    public List<Parcel> getParcel(){
        return deckParcel;
    }

    //Renvoie la liste du deck de canaux
    public int getNbMissionParcel(){
        return deckMissionParcel.size();
    }

    //Renvoie la liste du deck de canaux
    public List<Canal> getCanal(){
        return deckCanal;
    }

    //Renvoie la liste du deck de missions
    public int getNbMission(){
        return deckMissionPanda.size() + deckMissionParcel.size() + deckMissionPeasant.size();
    }
}