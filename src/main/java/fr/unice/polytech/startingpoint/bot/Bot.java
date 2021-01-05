package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.game.Coordinate;
import fr.unice.polytech.startingpoint.game.ParcelInformation;
import fr.unice.polytech.startingpoint.game.GameInteraction;
import fr.unice.polytech.startingpoint.type.CharacterType;
import fr.unice.polytech.startingpoint.type.MissionType;
import fr.unice.polytech.startingpoint.type.WeatherType;

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

    protected final GameInteraction gameInteraction;

    /**
     * <p>Set up the bot. Initialize all variables.</p>
     *
     * @param gameInteraction
     *            <b>Game object.</b>
     */
    public Bot(GameInteraction gameInteraction) {
        this.gameInteraction = gameInteraction;
    }

    /**<p>The actions of the bot during his turn.</p>
     */
    public abstract void botPlay(WeatherType weatherType);

    /**<p>Draw a mission with the type required in the resources.</p>
     *
     * @param missionType
     *            <b>The type of the mission the bot want to draw.</b>
     */
    public void drawMission(MissionType missionType){
        gameInteraction.drawMission(missionType);
    }

    /**@return Preview a list of 3 ColorTypes from the resources.
     */
    public List<ParcelInformation> drawParcel() {
        return gameInteraction.drawParcels();
    }

    public void selectParcel(ParcelInformation parcelInformation){
        gameInteraction.selectParcel(parcelInformation);
    }

    /**<p>Draw a canal in the resources and place it in the inventory.</p>
     */
    public void drawCanal() {
        gameInteraction.drawCanal();
    }

    /**<p>Place a parcel at the coordinates specified in the following parameters.</p>
     *
     * @param coordinate
     *            <b>The coordinates where the bot want to place the parcel on the board.</b>
     */
    public void placeParcel(Coordinate coordinate){
        gameInteraction.placeParcel(coordinate);
    }

    /**<p>Place a canal at the coordinates specified in the following parameter.</p>
     *
     * @param coordinates
     *            <b>The coordinates where the bot want to place the canal on the board.</b>
     */
    public void placeCanal(Coordinate[] coordinates) {
        gameInteraction.placeCanal(coordinates[0],coordinates[1]);
    }

    /**<p>Move the Panda to coordinates specified in the following parameter.</p>
     *
     * @param coordinate
     *            <b>The coordinates where the bot want to move the Panda on the board.</b>
     */
    public void movePanda(Coordinate coordinate) {
        gameInteraction.moveCharacter(CharacterType.PANDA,coordinate);
    }

    /**<p>Move the Peasant to coordinates specified in the following parameter.</p>
     *
     * @param coordinate
     *            <b>The coordinates where the bot want to move the Peasant on the board.</b>
     */
    public void movePeasant(Coordinate coordinate){
        gameInteraction.moveCharacter(CharacterType.PEASANT,coordinate);
    }

    public GameInteraction getGameInteraction(){return gameInteraction;}
}