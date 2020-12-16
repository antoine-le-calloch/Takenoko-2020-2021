package fr.unice.polytech.startingpoint.game;


import fr.unice.polytech.startingpoint.type.*;
import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;

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

class Resource {
    private final List<Mission> deckMissionParcel = new ArrayList<>();
    private final List<Mission> deckMissionPanda = new ArrayList<>();
    private final List<Mission> deckMissionPeasant = new ArrayList<>();
    private final List<Parcel> deckParcel = new ArrayList<>();
    private final List<Canal> deckCanal = new ArrayList<>();

    Resource(){
        initializeDeckParcel();
        initializeDeckMission();
        initializeDeckCanal();
    }

    //Création de toutes les parcelles et on les ajoute dans le deck parcelles
    private void initializeDeckParcel(){
        int nbParcel = 32;
        for (int i = 0; i < nbParcel / 8; i++){
            deckParcel.add(new Parcel(ColorType.RED,ImprovementType.NOTHING));
            deckParcel.add(new Parcel(ColorType.RED,ImprovementType.WATERSHED));
            deckParcel.add(new Parcel(ColorType.RED,ImprovementType.FERTILIZER));
            deckParcel.add(new Parcel(ColorType.RED,ImprovementType.ENCLOSURE));
            deckParcel.add(new Parcel(ColorType.BLUE,ImprovementType.NOTHING));
            deckParcel.add(new Parcel(ColorType.BLUE,ImprovementType.WATERSHED));
            deckParcel.add(new Parcel(ColorType.BLUE,ImprovementType.FERTILIZER));
            deckParcel.add(new Parcel(ColorType.BLUE,ImprovementType.ENCLOSURE));
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

    Parcel selectParcel(Parcel parcel){
        deckParcel.remove(parcel);
        return parcel;
    }

    //Pioche une parcelle du deck
    List<Parcel> drawParcel() throws OutOfResourcesException {
        if (deckParcel.size() > 2) {
            Collections.shuffle(deckParcel);
            List<Parcel> parcelList = new ArrayList<>();
            parcelList.add(deckParcel.get(0));
            parcelList.add(deckParcel.get(1));
            parcelList.add(deckParcel.get(2));
            return parcelList;
        }
        else if (!deckParcel.isEmpty()){
            return deckParcel;
        }
        throw new OutOfResourcesException("No more Parcel to draw.");
    }

    //Pioche un canal du deck
    Canal drawCanal() throws OutOfResourcesException {
        if (deckCanal.size() != 0) {
            Canal canal = deckCanal.get(0);
            deckCanal.remove(canal);
            return canal;
        }
        throw new OutOfResourcesException("No more Canal to draw.");
    }

    //Pioche une mission du deck
    Mission drawMission(MissionType type) throws OutOfResourcesException {
        switch (type) {
            case PARCEL:
                if (deckMissionParcel.size() > 0) {
                    return deckMissionParcel.remove(0);
                }
                throw new OutOfResourcesException("No more ParcelMission to draw.");
            case PEASANT:
                if (deckMissionPeasant.size() > 0) {
                    return deckMissionPeasant.remove(0);
                }
                throw new OutOfResourcesException("No more PeasantMission to draw.");
            case PANDA:
                if (deckMissionPanda.size() > 0) {
                    return deckMissionPanda.remove(0);
                }
                throw new OutOfResourcesException("No more PandaMission to draw.");
            default:
                throw new IllegalArgumentException("Wrong MissionType to draw.");
        }
    }

    List<Mission> getDeckParcelMission(){
        return new ArrayList<>(deckMissionParcel);
    }

    List<Mission> getDeckPandaMission(){
        return new ArrayList<>(deckMissionPanda);
    }

    List<Mission> getDeckPeasantMission(){
        return new ArrayList<>(deckMissionPeasant);
    }

    //Renvoie la liste du deck de parcelles
    List<Parcel> getDeckParcel(){
        return deckParcel;
    }

    //Renvoie la liste du deck de canaux
    int getNbMission(){
        return deckMissionParcel.size() + deckMissionPanda.size() + deckMissionPeasant.size();
    }

    //Renvoie la liste du deck de canaux
    List<Canal> getDeckCanal(){
        return deckCanal;
    }


    /**@return <b>True, if the resources are considers empty.</b>
     */
    boolean isEmpty(){
        return ( (deckCanal.size()==0 || deckParcel.size()==0) ||
                (deckMissionParcel.size()==0 && deckMissionPanda.size()==0 && deckMissionPeasant.size()==0) );
    }
}