package fr.unice.polytech.startingpoint.Game;


import fr.unice.polytech.startingpoint.Type.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe representant les ressources disponibles au cours d'une partie
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

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
            deckMissionParcel.add(new ParcelMission(ColorType.RED, 2, FormType.TRIANGLE));
            deckMissionParcel.add(new ParcelMission(ColorType.BLUE, 3, FormType.TRIANGLE));
            deckMissionParcel.add(new ParcelMission(ColorType.RED, 3, FormType.LINE));
            deckMissionParcel.add(new ParcelMission(ColorType.BLUE, 4, FormType.LINE));
            deckMissionParcel.add(new ParcelMission(ColorType.BLUE, 4, FormType.LINE));
        }
        Collections.shuffle(deckMissionParcel);
    }

    //Initialise le deck des mission panda
    private void initializeDeckMissionPanda(){
        int nbMissionParcel = 15;
        deckMissionPanda.add(new PandaMission(ColorType.RED, 3));
        for (int i = 0; i < nbMissionParcel / 2; i++){
            deckMissionPanda.add(new PandaMission(ColorType.RED, 3));
            deckMissionPanda.add(new PandaMission(ColorType.BLUE, 3));
        }
        Collections.shuffle(deckMissionPanda);
    }

    //Initialise le deck des mission peasant
    private void initializeDeckMissionPeasant(){
        int nbMissionParcel = 15;
        deckMissionPeasant.add(new PeasantMission(ColorType.RED, 4));
        for (int i = 0; i < nbMissionParcel / 2; i++){
            deckMissionPeasant.add(new PeasantMission(ColorType.RED, 4));
            deckMissionPeasant.add(new PeasantMission(ColorType.BLUE, 4));
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
    public List<Parcel> drawParcel() {
        if (deckParcel.size() > 2) {
            Collections.shuffle(deckParcel);
            List<Parcel> parcelList = new ArrayList<>();
            parcelList.add(deckParcel.get(0));
            parcelList.add(deckParcel.get(1));
            parcelList.add(deckParcel.get(2));
            return parcelList;
        }
        return deckParcel;
    }

    public Parcel selectParcel(Parcel parcel){
        deckParcel.remove(parcel);
        return parcel;
    }

    //Pioche un canal du deck
    public Canal drawCanal(){
        if (deckCanal.size() != 0) {
            Canal canal = deckCanal.get(0);
            deckCanal.remove(canal);
            return canal;
        }
        return null;
    }

    //Pioche une mission du deck
    public Mission drawMission(MissionType type){
        switch (type) {
            case PARCEL:
                if (deckMissionParcel.size() > 0) {
                    return deckMissionParcel.remove(0);
                }
            case PEASANT:
                if (deckMissionPeasant.size() > 0) {
                    return deckMissionPeasant.remove(0);
                }
            case PANDA:
                if (deckMissionPanda.size() > 0) {
                    return deckMissionPanda.remove(0);
                }
            default:
                return null;
        }
    }

    boolean isEmpty(){
        return ((deckCanal.size()==0 || deckParcel.size()==0) || (deckMissionPanda.size()==0 && deckMissionPeasant.size()==0 && deckMissionParcel.size()==0));
    }

    public List<Mission> getDeckParcelMission(){
        return deckMissionParcel;
    }

    public List<Mission> getDeckPandaMission(){
        return deckMissionPanda;
    }

    public List<Mission> getDeckPeasantMission(){
        return deckMissionPeasant;
    }

    //Renvoie la liste du deck de parcelles
    public List<Parcel> getParcel(){
        return deckParcel;
    }

    //Renvoie la liste du deck de canaux
    public int getNbMission(){
        return deckMissionParcel.size() + deckMissionPanda.size() + deckMissionPeasant.size();
    }

    //Renvoie la liste du deck de canaux
    public List<Canal> getCanal(){
        return deckCanal;
    }
}