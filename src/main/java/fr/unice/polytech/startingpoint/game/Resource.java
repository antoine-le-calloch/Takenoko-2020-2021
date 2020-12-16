package fr.unice.polytech.startingpoint.game;


import fr.unice.polytech.startingpoint.type.*;
import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <h1>{@link Resource} :</h1>
 *
 * <p>This class provides a limited resources to the game.</p>
 *
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @see Game
 * @version 0.5
 */

class Resource {
    private final List<Mission> deckMissionParcel = new ArrayList<>();
    private final List<Mission> deckMissionPanda = new ArrayList<>();
    private final List<Mission> deckMissionPeasant = new ArrayList<>();
    private final List<Parcel> deckParcel = new ArrayList<>();
    private final List<Canal> deckCanal = new ArrayList<>();

    /**Initialize all decks.
     */
    Resource(){
        initializeDeckParcel();
        initializeDeckMission();
        initializeDeckCanal();
    }

    /**Initialize {@link Parcel} deck.
     */
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

    /**Initialize {@link Mission} decks.
     */
    private void initializeDeckMission() {
        initializeDeckMissionParcel();
        initializeDeckMissionPanda();
        initializeDeckMissionPeasant();
    }

    /**Initialize {@link ParcelMission} deck.
     */
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

    /**Initialize {@link PandaMission} deck.
     */
    private void initializeDeckMissionPanda(){
        int nbMissionParcel = 15;
        deckMissionPanda.add(new PandaMission(ColorType.RED, 3));
        for (int i = 0; i < nbMissionParcel / 2; i++){
            deckMissionPanda.add(new PandaMission(ColorType.RED, 3));
            deckMissionPanda.add(new PandaMission(ColorType.BLUE, 3));
        }
        Collections.shuffle(deckMissionPanda);
    }

    /**Initialize {@link PeasantMission} deck.
     */
    private void initializeDeckMissionPeasant(){
        int nbMissionParcel = 15;
        deckMissionPeasant.add(new PeasantMission(ColorType.RED, 4));
        for (int i = 0; i < nbMissionParcel / 2; i++){
            deckMissionPeasant.add(new PeasantMission(ColorType.RED, 4));
            deckMissionPeasant.add(new PeasantMission(ColorType.BLUE, 4));
        }
        Collections.shuffle(deckMissionPeasant);
    }

    /**Draw the {@link Parcel} specified in parameter.
     *
     * @return <b>The parcel drawn.</b>
     */
    private void initializeDeckCanal(){
        int nbCanal = 27;
        for (int i = 0; i < nbCanal; i++){
            deckCanal.add(new Canal());
        }
    }

    /**Draw the {@link Parcel} specified in parameter.
     *
     * @return <b>The parcel drawn.</b>
     */
    Parcel selectParcel(Parcel parcel){
        deckParcel.remove(parcel);
        return parcel;
    }

    /**
     * @return <b>A list of the parcels drawn.</b>
     * @throws OutOfResourcesException
     */
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

    /**Draw a {@link Canal}.
     *
     * @return <b>The canal drawn.</b>
     * @throws OutOfResourcesException
     */
    Canal drawCanal() throws OutOfResourcesException {
        if (deckCanal.size() != 0) {
            Canal canal = deckCanal.get(0);
            deckCanal.remove(canal);
            return canal;
        }
        throw new OutOfResourcesException("No more Canal to draw.");
    }

    /**Draw a {@link Mission} with the type specified in parameter.
     *
     * @param type
     *              <b>The {@link MissionType} the bot want to draw.</b>
     *
     * @return <b>The mission drawn.</b>
     * @throws OutOfResourcesException
     */
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

    /**@return <b>True, if the resources are considers empty.</b>
     */
    boolean isEmpty(){
        return ( (deckCanal.size()==0 || deckParcel.size()==0) ||
                (deckMissionParcel.size()==0 && deckMissionPanda.size()==0 && deckMissionPeasant.size()==0) );
    }

    /**@return <b>The list of {@link ParcelMission}.</b>
     */
    List<Mission> getDeckParcelMission(){
        return new ArrayList<>(deckMissionParcel);
    }

    /**@return <b>The list of {@link PandaMission}.</b>
     */
    List<Mission> getDeckPandaMission(){
        return new ArrayList<>(deckMissionPanda);
    }

    /**@return <b>The list of {@link PeasantMission}.</b>
     */
    List<Mission> getDeckPeasantMission(){
        return new ArrayList<>(deckMissionPeasant);
    }

    /**@return <b>The list of {@link Parcel}.</b>
     */
    List<Parcel> getDeckParcel(){
        return deckParcel;
    }

    /**@return <b>The list of {@link Canal}.</b>
     */
    List<Canal> getDeckCanal(){
        return deckCanal;
    }

    /**@return <b>The list number of missions.</b>
     */
    int getNbMission(){
        return deckMissionParcel.size() + deckMissionPanda.size() + deckMissionPeasant.size();
    }
}