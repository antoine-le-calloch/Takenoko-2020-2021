package fr.unice.polytech.startingpoint.Bot;

import fr.unice.polytech.startingpoint.Game.*;
import fr.unice.polytech.startingpoint.Type.*;

import fr.unice.polytech.startingpoint.exception.BadPlaceCanalException;
import fr.unice.polytech.startingpoint.exception.BadPlaceParcelException;
import fr.unice.polytech.startingpoint.exception.MoveCharacterException;


import java.util.*;

/**
 * <h1>{@link Bot} :</h1>
 *
 * <p>This class provides a skeletal implementation of the {@link PandaBot},
 * {@link ParcelBot}, {@link PeasantBot} classes.</p>
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
    protected final Resource resource;
    protected final Board board;
    protected final Inventory inventory;

    /**
     * <h2>{@link #Bot(Resource, Board)} :</h2>
     *
     * <p>Set up the bot. Initialize all variables.</p>
     *
     * @param resource
     *            <b>Resource object.</b>
     * @param board
     *            <b>Board object.</b>
     */
    public Bot(Resource resource, Board board) {
        this.resource = resource;
        this.board = board;
        this.inventory = new Inventory();
    }

    /**
     * <h2>{@link #botPlay()} :</h2>
     *
     * <p>The actions of the bot during his turn.</p>
     */
    public abstract void botPlay();

    /**
     * <h2>{@link #drawMission(MissionType)} : (<b>ACTION 1</b>)</h2>
     *
     * <p>Draw a mission with the type required in the resources.</p>
     *
     * @param missionType
     *            <b>The type of the mission the bot want to draw.</b>
     * @see MissionType
     * @see Inventory
     * @see Resource
     * @see Mission
     */
    public void drawMission(MissionType missionType){
        inventory.addMission(resource.drawMission(missionType));
    }

    /**
     * <h2>{@link #drawParcel()} : </h2>
     *
     * <p>Preview a list of 3 parcels from the resources.</p>
     *
     * @see Parcel
     * @see Resource
     */
    public List<Parcel> drawParcel() {
        return resource.drawParcel();
    }

    /**
     * <h2>{@link #drawCanal()} : </h2>
     *
     * <p>Draw a canal in the resources and place it in the inventory.</p>
     *
     * @see Inventory
     * @see Resource
     * @see Canal
     */
    public void drawCanal() {
        inventory.addCanal(resource.drawCanal());
    }

    /**
     * <h2>{@link #placeParcel(Coordinate, Parcel)} : (<b>ACTION 2</b>)</h2>
     *
     * <p>Place a parcel at the coordinates specified in the following parameters.</p>
     *
     * @param coordinate
     *            <b>The coordinates where the bot want to place the parcel on the board.</b>
     * @param parcel
     *            <b>The parcel the bot want to place on the board.</b>
     * @see Coordinate
     * @see Parcel
     * @see Board
     */
    public void placeParcel(Coordinate coordinate, Parcel parcel){
        try {
            board.placeParcel(parcel, coordinate);
        } catch (BadPlaceParcelException E) {
            // FAIRE QUELQUE CHOSE SI CA MARCHE PAS, IMPORTANT
        }
    }

    /**
     * <h2>{@link #placeCanal(Coordinate[])} : (<b>ACTION 3</b>)</h2>
     *
     * <p>Place a canal at the coordinates specified in the following parameter.</p>
     *
     * @param coordinates
     *            <b>The coordinates where the bot want to place the canal on the board.</b>
     * @see Coordinate
     * @see Board
     * @see Resource
     */
    public void placeCanal(Coordinate[] coordinates) {
        try {
            drawCanal();
            board.placeCanal(inventory.pickCanal() , coordinates[0], coordinates[1]);
        } catch (BadPlaceCanalException E) {
            // FAIRE QUELQUE CHOSE SI CA MARCHE PAS, IMPORTANT
        }
    }

    /**
     * <h2>{@link #movePanda(Coordinate)} : (<b>ACTION 4</b>)</h2>
     *
     * <p>Move the Panda to coordinates specified in the following parameter.</p>
     *
     * @param coordinate
     *            <b>The coordinates where the bot want to move the Panda on the board.</b>
     * @see Coordinate
     * @see Board
     * @see fr.unice.polytech.startingpoint.Game.Character
     */
    public void movePanda(Coordinate coordinate) {
        try {
            board.moveCharacter(board.getPanda(), coordinate);
        } catch (MoveCharacterException E) {
            // FAIRE QUELQUE CHOSE SI CA MARCHE PAS, IMPORTANT
        }
    }

    /**
     * <h2>{@link #movePeasant(Coordinate)} : (<b>ACTION 5</b>)</h2>
     *
     * <p>Move the Peasant to coordinates specified in the following parameter.</p>
     *
     * @param coordinate
     *            <b>The coordinates where the bot want to move the Peasant on the board.</b>
     * @see Coordinate
     * @see Board
     * @see fr.unice.polytech.startingpoint.Game.Character
     */
    public void movePeasant(Coordinate coordinate){
        try {
            board.moveCharacter(board.getPeasant(), coordinate);
        } catch (MoveCharacterException E) {
            // FAIRE QUELQUE CHOSE SI CA MARCHE PAS, IMPORTANT
        }
    }

    /**
     * <h2>{@link #allPlaces()} :</h2>
     *
     * @return <b>A list of all parcels’ coordinates present on the board and one layer of coordinates around.</b>
     * @see Coordinate
     * @see Board
     */
    public List<Coordinate> allPlaces(){
        Set<Coordinate> possibleCoordinates = new HashSet<>();
        for(Coordinate c : board.getPlacedParcels().keySet()) {
            possibleCoordinates.add(c);
            for (Coordinate offSet : Coordinate.offSets()){
                possibleCoordinates.add(new Coordinate(c,offSet));
            }
        }
        return new ArrayList<>(possibleCoordinates);
    }

    /**
     * <h2>{@link #possibleCoordinatesParcel()} :</h2>
     *
     * @return <b>A list of coordinates for all placeable parcels on the board.</b>
     * @see Coordinate
     * @see Board
     */
    public List<Coordinate> possibleCoordinatesParcel(){
        Set<Coordinate> possibleCoordinates = new HashSet<>();
        for(Coordinate c : board.getPlacedParcels().keySet()) {
            for (Coordinate offSet : Coordinate.offSets()){
                if(board.isPlayableParcel(new Coordinate(c,offSet))){
                    possibleCoordinates.add(new Coordinate(c,offSet));
                }
            }
        }
        return new ArrayList<>(possibleCoordinates);
    }

    /**
     * <h2>{@link #possibleCoordinatesCanal()} :</h2>
     *
     * @return <b>A list of coordinates for all placeable canals on the board.</b>
     * @see Coordinate
     * @see Board
     */
    public List<Coordinate[]> possibleCoordinatesCanal(){
        Set<Coordinate[]> possibleCoordinates = new HashSet<>();
        for(Parcel parcel1 : board.getPlacedParcels().values()){
            for(Parcel parcel2 : board.getPlacedParcels().values()){
                if (board.isPlayableCanal(parcel1.getCoordinates(),parcel2.getCoordinates()))
                    possibleCoordinates.add(new Coordinate[]{parcel1.getCoordinates(),parcel2.getCoordinates()});
            }
        }
        return new ArrayList<>(possibleCoordinates);
    }

    /**
     * <h2>{@link #possibleCoordinatesPanda()} :</h2>
     *
     * @return <b>A list of coordinates where the Panda can be moved on the board.</b>
     * @see Coordinate
     * @see Board
     * @see fr.unice.polytech.startingpoint.Game.Character
     */
    public List<Coordinate> possibleCoordinatesPanda(){
        Set<Coordinate> possibleCoordinates = new HashSet<>();
        for(Coordinate c : board.getPlacedParcels().keySet()) {
            if (board.isMovableCharacter(board.getPanda(),c)){
                possibleCoordinates.add(c);
            }
        }
        return new ArrayList<>(possibleCoordinates);
    }

    /**
     * <h2>{@link #possibleCoordinatesPeasant()} :</h2>
     *
     * @return <b>A list of coordinates  where the Peasant can be moved on the board.</b>
     * @see Coordinate
     * @see Board
     * @see fr.unice.polytech.startingpoint.Game.Character
     */
    public List<Coordinate> possibleCoordinatesPeasant(){
        Set<Coordinate> possibleCoordinates = new HashSet<>();
        for(Coordinate c : board.getPlacedParcels().keySet()) {
            if (board.isMovableCharacter(board.getPeasant(),c)){
                possibleCoordinates.add(c);
            }
        }
        return new ArrayList<>(possibleCoordinates);
    }

    /**
     * <h2>{@link #getInventory()} :</h2>
     *
     * @return <b>The inventory of the bot.</b>
     * @see Inventory
     */
    public Inventory getInventory(){
        return inventory;
    }
}