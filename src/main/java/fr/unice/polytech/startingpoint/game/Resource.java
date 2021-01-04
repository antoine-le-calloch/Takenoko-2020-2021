package fr.unice.polytech.startingpoint.game;


import fr.unice.polytech.startingpoint.exception.IllegalTypeException;
import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
import fr.unice.polytech.startingpoint.type.ColorType;
import fr.unice.polytech.startingpoint.type.FormType;
import fr.unice.polytech.startingpoint.type.ImprovementType;
import fr.unice.polytech.startingpoint.type.MissionType;

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
    Resource(Board board){
        initializeDeckParcel();
        initializeDeckMission(board);
        initializeDeckCanal();
    }

    /**Initialize {@link Parcel} deck.
     */
    private void initializeDeckParcel(){
        for (int i = 0; i<6; i++) {
            deckParcel.add(new Parcel(ColorType.GREEN, ImprovementType.NOTHING));
            deckParcel.add(new Parcel(ColorType.YELLOW, ImprovementType.NOTHING));
        }
        for (int i = 0; i<4; i++)
            deckParcel.add(new Parcel(ColorType.GREEN, ImprovementType.NOTHING));

        for (int i = 0; i<2; i++) {
            deckParcel.add(new Parcel(ColorType.GREEN, ImprovementType.WATERSHED));
            deckParcel.add(new Parcel(ColorType.GREEN, ImprovementType.ENCLOSURE));
        }
        deckParcel.add(new Parcel(ColorType.GREEN,ImprovementType.FERTILIZER));
        deckParcel.add(new Parcel(ColorType.YELLOW, ImprovementType.WATERSHED));
        deckParcel.add(new Parcel(ColorType.YELLOW, ImprovementType.ENCLOSURE));
        deckParcel.add(new Parcel(ColorType.YELLOW,ImprovementType.FERTILIZER));
        deckParcel.add(new Parcel(ColorType.RED, ImprovementType.WATERSHED));
        deckParcel.add(new Parcel(ColorType.RED, ImprovementType.ENCLOSURE));
        deckParcel.add(new Parcel(ColorType.RED,ImprovementType.FERTILIZER));
    }

    /**Initialize {@link Mission} decks.
     */
    private void initializeDeckMission(Board board) {
        initializeDeckMissionParcel(board);
        initializeDeckMissionPanda(board);
        initializeDeckMissionPeasant(board);
    }

    /**Initialize {@link ParcelMission} deck.
     */
    private void initializeDeckMissionParcel(Board board){
        int nbMissionParcel = 9;
        deckMissionParcel.add(new ParcelMission(board,ColorType.GREEN, 2, FormType.LINE));
        deckMissionParcel.add(new ParcelMission(board,ColorType.GREEN, 2, FormType.TRIANGLE));
        deckMissionParcel.add(new ParcelMission(board,ColorType.YELLOW, 3, FormType.LINE));
        deckMissionParcel.add(new ParcelMission(board,ColorType.YELLOW, 3, FormType.TRIANGLE));
        deckMissionParcel.add(new ParcelMission(board,ColorType.RED, 4, FormType.LINE));
        deckMissionParcel.add(new ParcelMission(board,ColorType.RED, 4, FormType.TRIANGLE));

        //mauvaise mission
        for (int i = 0; i < nbMissionParcel / 3; i++){
            deckMissionParcel.add(new ParcelMission(board,ColorType.YELLOW, 3, FormType.TRIANGLE));
            deckMissionParcel.add(new ParcelMission(board,ColorType.RED, 4, FormType.LINE));
            deckMissionParcel.add(new ParcelMission(board,ColorType.RED, 4, FormType.TRIANGLE));
        }
        Collections.shuffle(deckMissionParcel);
    }

    /**Initialize {@link PandaMission} deck.
     */
    private void initializeDeckMissionPanda(Board board){
        for (int i = 0; i<5; i++)
            deckMissionPanda.add(new PandaMission(board,ColorType.GREEN, 3));
        for (int i = 0; i<4; i++)
            deckMissionPanda.add(new PandaMission(board,ColorType.YELLOW, 4));
        for (int i = 0; i<3; i++)
            deckMissionPanda.add(new PandaMission(board,ColorType.RED, 5));
        for (int i = 0; i<3; i++)
            deckMissionPanda.add(new PandaMission(board,ColorType.ALL_COLOR, 5));

        Collections.shuffle(deckMissionPanda);
    }

    /**Initialize {@link PeasantMission} deck.
     */
    private void initializeDeckMissionPeasant(Board board){
        deckMissionPeasant.add(new PeasantMission(board,ColorType.RED, 5,ImprovementType.FERTILIZER));
        deckMissionPeasant.add(new PeasantMission(board,ColorType.RED, 6,ImprovementType.WATERSHED));
        deckMissionPeasant.add(new PeasantMission(board,ColorType.RED, 6,ImprovementType.ENCLOSURE));
        deckMissionPeasant.add(new PeasantMission(board,ColorType.RED, 7,ImprovementType.NOTHING));
        deckMissionPeasant.add(new PeasantMission(board,ColorType.GREEN, 3,ImprovementType.FERTILIZER));
        deckMissionPeasant.add(new PeasantMission(board,ColorType.GREEN, 4,ImprovementType.WATERSHED));
        deckMissionPeasant.add(new PeasantMission(board,ColorType.GREEN, 4,ImprovementType.ENCLOSURE));
        deckMissionPeasant.add(new PeasantMission(board,ColorType.GREEN, 5,ImprovementType.NOTHING));
        deckMissionPeasant.add(new PeasantMission(board,ColorType.YELLOW, 4,ImprovementType.FERTILIZER));
        deckMissionPeasant.add(new PeasantMission(board,ColorType.YELLOW, 5,ImprovementType.WATERSHED));
        deckMissionPeasant.add(new PeasantMission(board,ColorType.YELLOW, 5,ImprovementType.ENCLOSURE));
        deckMissionPeasant.add(new PeasantMission(board,ColorType.YELLOW, 6,ImprovementType.NOTHING));

        //mauvaise mission
        for (int i = 0; i < 3; i++){
            deckMissionPeasant.add(new PeasantMission(board,ColorType.GREEN, 4,ImprovementType.NOTHING));
        }
        Collections.shuffle(deckMissionPeasant);
    }

    /**Initialize {@link Canal} deck.
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
    List<Parcel> drawParcels() throws OutOfResourcesException {
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
        if (!deckCanal.isEmpty()) {
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
                if (!deckMissionParcel.isEmpty()) {
                    return deckMissionParcel.remove(0);
                }
                throw new OutOfResourcesException("No more ParcelMission to draw.");
            case PEASANT:
                if (!deckMissionPeasant.isEmpty()) {
                    return deckMissionPeasant.remove(0);
                }
                throw new OutOfResourcesException("No more PeasantMission to draw.");
            case PANDA:
                if (!deckMissionPanda.isEmpty()) {
                    return deckMissionPanda.remove(0);
                }
                throw new OutOfResourcesException("No more PandaMission to draw.");
            default:
                throw new IllegalTypeException("Wrong MissionType to draw.");
        }
    }

    /**@return <b>True, if the resources are considers empty.</b>
     */
    boolean isEmpty(){
        return ( (deckCanal.isEmpty() || deckParcel.isEmpty()) ||
                (deckMissionParcel.isEmpty() && deckMissionPanda.isEmpty() && deckMissionPeasant.isEmpty()) );
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