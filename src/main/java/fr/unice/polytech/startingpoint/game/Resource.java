package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.exception.IllegalTypeException;
import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
import fr.unice.polytech.startingpoint.game.mission.PandaMission;
import fr.unice.polytech.startingpoint.game.mission.ParcelMission;
import fr.unice.polytech.startingpoint.game.mission.PeasantMission;
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
    private final List<PandaMission> deckPandaMission = new ArrayList<>();
    private final List<ParcelMission> deckParcelMission = new ArrayList<>();
    private final List<PeasantMission> deckPeasantMission = new ArrayList<>();
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

    private void initializeDeckMission() {
        initializeDeckMissionParcel();
        initializeDeckMissionPanda();
        initializeDeckMissionPeasant();
    }

    /**Initialize {@link ParcelMission} deck.
     */
    private void initializeDeckMissionParcel(){
        deckParcelMission.add(new ParcelMission(ColorType.GREEN, FormType.LINE, 2));
        deckParcelMission.add(new ParcelMission(ColorType.GREEN, FormType.TRIANGLE, 2));
        deckParcelMission.add(new ParcelMission(ColorType.GREEN, FormType.ARC, 2));
        deckParcelMission.add(new ParcelMission(ColorType.GREEN, FormType.DIAMOND, 3));
        deckParcelMission.add(new ParcelMission(ColorType.YELLOW, FormType.LINE, 3));
        deckParcelMission.add(new ParcelMission(ColorType.YELLOW, FormType.TRIANGLE, 3));
        deckParcelMission.add(new ParcelMission(ColorType.YELLOW, FormType.ARC, 3));
        deckParcelMission.add(new ParcelMission(ColorType.YELLOW, FormType.DIAMOND, 4));
        deckParcelMission.add(new ParcelMission(ColorType.RED, FormType.LINE, 4));
        deckParcelMission.add(new ParcelMission(ColorType.RED, FormType.TRIANGLE, 4));
        deckParcelMission.add(new ParcelMission(ColorType.RED, FormType.ARC, 4));
        deckParcelMission.add(new ParcelMission(ColorType.RED, FormType.DIAMOND, 5));
        deckParcelMission.add(new ParcelMission(ColorType.YELLOW, ColorType.GREEN, FormType.DIAMOND, 3));
        deckParcelMission.add(new ParcelMission(ColorType.YELLOW, ColorType.RED, FormType.DIAMOND, 5));
        deckParcelMission.add(new ParcelMission(ColorType.RED, ColorType.GREEN, FormType.DIAMOND, 4));
        Collections.shuffle(deckParcelMission);
    }

    /**Initialize {@link PandaMission} deck.
     */
    private void initializeDeckMissionPanda(){
        for (int i = 0; i<5; i++)
            deckPandaMission.add(new PandaMission(ColorType.GREEN, 3));
        for (int i = 0; i<4; i++)
            deckPandaMission.add(new PandaMission(ColorType.YELLOW, 4));
        for (int i = 0; i<3; i++) {
            deckPandaMission.add(new PandaMission(ColorType.RED, 5));
            deckPandaMission.add(new PandaMission(ColorType.ALL_COLOR, 5));
        }
        Collections.shuffle(deckPandaMission);
    }

    /**Initialize {@link PeasantMission} deck.
     */
    private void initializeDeckMissionPeasant(){
        deckPeasantMission.add(new PeasantMission(ColorType.RED, ImprovementType.FERTILIZER, 5));
        deckPeasantMission.add(new PeasantMission(ColorType.RED, ImprovementType.WATERSHED, 6));
        deckPeasantMission.add(new PeasantMission(ColorType.RED, ImprovementType.ENCLOSURE, 6));
        deckPeasantMission.add(new PeasantMission(ColorType.RED, ImprovementType.NOTHING, 7));
        deckPeasantMission.add(new PeasantMission(ColorType.GREEN, ImprovementType.FERTILIZER, 3));
        deckPeasantMission.add(new PeasantMission(ColorType.GREEN, ImprovementType.WATERSHED, 4));
        deckPeasantMission.add(new PeasantMission(ColorType.GREEN, ImprovementType.ENCLOSURE, 4));
        deckPeasantMission.add(new PeasantMission(ColorType.GREEN, ImprovementType.NOTHING, 5));
        deckPeasantMission.add(new PeasantMission(ColorType.YELLOW, ImprovementType.FERTILIZER, 4));
        deckPeasantMission.add(new PeasantMission(ColorType.YELLOW, ImprovementType.WATERSHED, 5));
        deckPeasantMission.add(new PeasantMission(ColorType.YELLOW, ImprovementType.ENCLOSURE, 5));
        deckPeasantMission.add(new PeasantMission(ColorType.YELLOW, ImprovementType.NOTHING, 6));
        deckPeasantMission.add(new PeasantMission(ColorType.GREEN, ImprovementType.WHATEVER, 8));
        deckPeasantMission.add(new PeasantMission(ColorType.YELLOW, ImprovementType.WHATEVER, 7));
        deckPeasantMission.add(new PeasantMission(ColorType.RED, ImprovementType.WHATEVER, 6));
        Collections.shuffle(deckPeasantMission);
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

    PandaMission drawPandaMission() throws OutOfResourcesException {
        if (!deckPandaMission.isEmpty()) {
            return deckPandaMission.remove(0);
        }
        throw new OutOfResourcesException("No more PandaMission to draw.");
    }

    ParcelMission drawParcelMission() throws OutOfResourcesException {
        if (!deckParcelMission.isEmpty()) {
            return deckParcelMission.remove(0);
        }
        throw new OutOfResourcesException("No more ParcelMission to draw.");
    }

    PeasantMission drawPeasantMission() throws OutOfResourcesException {
        if (!deckPeasantMission.isEmpty()) {
            return deckPeasantMission.remove(0);
        }
        throw new OutOfResourcesException("No more PeasantMission to draw.");
    }

    /**@return <b>True, if the resources are considers empty.</b>
     */
    boolean isEmpty(){
        return ( (deckCanal.isEmpty() || deckParcel.isEmpty()) ||
                (deckParcelMission.isEmpty() && deckPandaMission.isEmpty() && deckPeasantMission.isEmpty()) );
    }

    /**@return <b>The list of {@link ParcelMission}.</b>
     */
    List<ParcelMission> getDeckParcelMission(){
        return new ArrayList<>(deckParcelMission);
    }

    /**@return <b>The list of {@link PandaMission}.</b>
     */
    List<PandaMission> getDeckPandaMission(){
        return new ArrayList<>(deckPandaMission);
    }

    /**@return <b>The list of {@link PeasantMission}.</b>
     */
    List<PeasantMission> getDeckPeasantMission(){
        return new ArrayList<>(deckPeasantMission);
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
        return deckParcelMission.size() + deckPandaMission.size() + deckPeasantMission.size();
    }
}