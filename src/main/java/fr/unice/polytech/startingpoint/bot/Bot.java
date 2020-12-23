package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.exception.BadCoordinateException;
import fr.unice.polytech.startingpoint.exception.OutOfResourcesException;
import fr.unice.polytech.startingpoint.exception.RulesViolationException;
import fr.unice.polytech.startingpoint.game.*;
import fr.unice.polytech.startingpoint.type.CharacterType;
import fr.unice.polytech.startingpoint.type.MissionType;

import java.util.List;

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
    protected final PlayerInteraction playerInteraction;
    int NB_ACTION = 2;
    /**
     * <p>Set up the bot. Initialize all variables.</p>
     *
     * @param playerInteraction
     *            <b>Game object.</b>
     */
    public Bot(PlayerInteraction playerInteraction) {
        this.playerInteraction = playerInteraction;
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
            playerInteraction.drawMission(missionType);
        }
        catch (OutOfResourcesException | RulesViolationException e) {
            e.printStackTrace();
        }
    }

    /**@return Preview a list of 3 ColorTypes from the resources.
     */
    public List<ParcelInformation> drawParcel() {
        try {
            return playerInteraction.drawParcels();
        }
        catch (OutOfResourcesException | RulesViolationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void selectParcel(ParcelInformation parcelInformation){
        try {
            playerInteraction.selectParcel(parcelInformation);
        }
        catch (RulesViolationException e) {
            e.printStackTrace();
        }
    }

    /**<p>Draw a canal in the resources and place it in the inventory.</p>
     */
    public void drawCanal() {
        try {
            playerInteraction.drawCanal();
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
            playerInteraction.placeParcel(coordinate);
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
            playerInteraction.placeCanal(coordinates[0],coordinates[1]);
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
            playerInteraction.moveCharacter(CharacterType.PANDA,coordinate);
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
            playerInteraction.moveCharacter(CharacterType.PEASANT,coordinate);
        }
        catch (OutOfResourcesException | BadCoordinateException | RulesViolationException e) {
            e.printStackTrace();
        }
    }
}