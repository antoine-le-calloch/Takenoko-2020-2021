package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.game.*;
import fr.unice.polytech.startingpoint.type.*;

import fr.unice.polytech.startingpoint.exception.*;

import java.util.*;

/**
 * <h1>{@link Bot} :</h1>
 *
 * <p>This class provides a skeletal implementation of the {@link PandaBot},
 * {@link ParcelBot}, {@link PeasantBot} and {@link RandomBot} classes.</p>
 *
 * <p>The programmer needs only to extend this class and provide
 * implementations for the {@link #botPlay()} method.</p>
 *
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @see Bot
 * @see PandaBot
 * @see ParcelBot
 * @see PeasantBot
 * @see RandomBot
 * @version 0.5
 */

public abstract class Bot {
    protected final Game game;
    protected final Rules rules;

    /**
     * <p>Set up the bot. Initialize all variables.</p>
     *
     * @param game
     *            <b>Game object.</b>
     */
    public Bot(Game game, Rules rules) {
        this.game = game;
        this.rules = rules;
    }

    /**<p>The actions of the bot during his turn.</p>
     */
    public abstract void botPlay();

    /**<p>Draw a mission with the type required in the resources.</p>
     *
     * @param missionType
     *            <b>The type of the mission the bot want to draw.</b>
     */
    public void drawMission(MissionType missionType){
        try {
            game.drawMission(missionType);
        }
        catch (OutOfResourcesException | RulesViolationException e) {
            e.printStackTrace();
        }
    }

    /**@return Preview a list of 3 ColorTypes from the resources.
     */
    public List<ParcelInformation> drawParcel() {
        try {
            return game.drawParcels();
        }
        catch (OutOfResourcesException | RulesViolationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void selectParcel(ParcelInformation parcelInformation){
        try {
            game.selectParcel(parcelInformation);
        }
        catch (RulesViolationException e) {
            e.printStackTrace();
        }
    }

    /**<p>Draw a canal in the resources and place it in the inventory.</p>
     */
    public void drawCanal() {
        try {
            game.drawCanal();
        }
        catch (OutOfResourcesException | RulesViolationException e) {
            e.printStackTrace();
        }
    }

    /**<p>Place a parcel at the coordinates specified in the following parameters.</p>
     *
     * @param coordinate
     *            <b>The coordinates where the bot want to place the parcel on the board.</b>
     */
    public void placeParcel(Coordinate coordinate){
        try {
            game.placeParcel(coordinate);
        }
        catch (BadCoordinateException | RulesViolationException e) {
            e.printStackTrace();
        }
    }

    /**<p>Place a canal at the coordinates specified in the following parameter.</p>
     *
     * @param coordinates
     *            <b>The coordinates where the bot want to place the canal on the board.</b>
     */
    public void placeCanal(Coordinate[] coordinates) {
        try {
            game.placeCanal(coordinates[0],coordinates[1]);
        }
        catch (OutOfResourcesException | BadCoordinateException | RulesViolationException e) {
            e.printStackTrace();
        }
    }

    /**<p>Move the Panda to coordinates specified in the following parameter.</p>
     *
     * @param coordinate
     *            <b>The coordinates where the bot want to move the Panda on the board.</b>
     */
    public void movePanda(Coordinate coordinate) {
        try {
            game.moveCharacter(CharacterType.PANDA,coordinate);
        }
        catch (OutOfResourcesException | BadCoordinateException | RulesViolationException e) {
            e.printStackTrace();
        }
    }

    /**<p>Move the Peasant to coordinates specified in the following parameter.</p>
     *
     * @param coordinate
     *            <b>The coordinates where the bot want to move the Peasant on the board.</b>
     */
    public void movePeasant(Coordinate coordinate){
        try {
            game.moveCharacter(CharacterType.PEASANT,coordinate);
        }
        catch (OutOfResourcesException | BadCoordinateException | RulesViolationException e) {
            e.printStackTrace();
        }
    }

    /**@return <b>A list of all parcels’ coordinates present on the board and one layer of coordinates around.</b>
     */
    public List<Coordinate> allPlaces(){
        Set<Coordinate> possibleCoordinates = new HashSet<>();
        for(Coordinate c : game.getPlacedCoordinates()) {
            possibleCoordinates.add(c);
            for (Coordinate offSet : Coordinate.offSets()){
                possibleCoordinates.add(new Coordinate(c,offSet));
            }
        }
        return new ArrayList<>(possibleCoordinates);
    }

    /**@return <b>A list of coordinates for all placeable parcels on the board.</b>
     */
    public List<Coordinate> possibleCoordinatesParcel(){
        Set<Coordinate> possibleCoordinates = new HashSet<>();
        for(Coordinate c : game.getPlacedCoordinates()) {
            for (Coordinate offSet : Coordinate.offSets()){
                if(rules.isPlayableParcel(new Coordinate(c,offSet))){
                    possibleCoordinates.add(new Coordinate(c,offSet));
                }
            }
        }
        return new ArrayList<>(possibleCoordinates);
    }

    /**@return <b>A list of coordinates for all placeable canals on the board.</b>
     */
    public List<Coordinate[]> possibleCoordinatesCanal(){
        Set<Coordinate[]> possibleCoordinates = new HashSet<>();
        for(Coordinate coordinate1 : game.getPlacedCoordinates()){
            for(Coordinate coordinate2 : game.getPlacedCoordinates()){
                if (rules.isPlayableCanal(coordinate1, coordinate2))
                    possibleCoordinates.add(new Coordinate[]{coordinate1, coordinate2});
            }
        }
        return new ArrayList<>(possibleCoordinates);
    }

    /**@return <b>A list of coordinates where the Panda can be moved on the board.</b>
     */
    public List<Coordinate> possibleCoordinatesPanda(){
        Set<Coordinate> possibleCoordinates = new HashSet<>();
        for(Coordinate coordinate : game.getPlacedCoordinates()) {
            if (rules.isMovableCharacter(CharacterType.PANDA,coordinate)){
                possibleCoordinates.add(coordinate);
            }
        }
        return new ArrayList<>(possibleCoordinates);
    }

    /**@return <b>A list of coordinates  where the Peasant can be moved on the board.</b>
     */
    public List<Coordinate> possibleCoordinatesPeasant(){
        Set<Coordinate> possibleCoordinates = new HashSet<>();
        for(Coordinate c : game.getPlacedCoordinates()) {
            if (rules.isMovableCharacter(CharacterType.PEASANT,c)){
                possibleCoordinates.add(c);
            }
        }
        return new ArrayList<>(possibleCoordinates);
    }
}