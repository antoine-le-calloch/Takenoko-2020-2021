package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.bot.strategie.MissionPandaStrat;
import fr.unice.polytech.startingpoint.bot.strategie.MissionParcelStrat;
import fr.unice.polytech.startingpoint.bot.strategie.MissionPeasantStrat;
import fr.unice.polytech.startingpoint.game.GameInteraction;
import fr.unice.polytech.startingpoint.game.board.Coordinate;
import fr.unice.polytech.startingpoint.game.board.ParcelInformation;
import fr.unice.polytech.startingpoint.game.mission.Mission;
import fr.unice.polytech.startingpoint.type.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * <h1>{@link Bot} :</h1>
 *
 * <p>This class provides a skeletal implementation of the {@link PandaBot},
 * {@link ParcelBot}, {@link PeasantBot} and {@link RandomBot} classes.</p>
 *
 * <p>The programmer needs only to extend this class and provide
 * implementations for the {@link #botPlay(WeatherType)} method.</p>
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
    protected final MissionPandaStrat missionPandaStrat;
    protected final MissionParcelStrat stratMissionParcel ;
    protected final MissionPeasantStrat stratMissionPeasant ;

    protected final GameInteraction gameInteraction;

    /**
     * <p>Set up the bot. Initialize all variables.</p>
     *
     * @param gameInteraction
     *            <b>Game object.</b>
     */
    public Bot(GameInteraction gameInteraction) {
        this.gameInteraction = gameInteraction;
        this.missionPandaStrat = new MissionPandaStrat(this);
        this.stratMissionParcel = new MissionParcelStrat(this);
        this.stratMissionPeasant = new MissionPeasantStrat(this);
    }

    /**<p>The actions of the bot during his turn.</p>
     */
    public abstract void botPlay(WeatherType weatherType);

    public abstract MissionType bestMissionTypeToDraw();

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

    boolean isJudiciousDrawMission() {
        int NB_MAX_MISSION = 5;
        return gameInteraction.getMissionsSize() < NB_MAX_MISSION &&
                gameInteraction.getResourceSize(ResourceType.ALL_MISSION) > 0 &&
                !gameInteraction.contains(ActionType.DRAW_MISSION);
    }

    public Mission determineBestMissionToDo() {
        Random randInt = new Random();
        List<Mission> missionList = new LinkedList<>();
        List<int[]> intsList = new LinkedList<>();
        for (Mission mission : gameInteraction.getInventoryMissions()){
            int nbMove = -1;
            switch (mission.getMissionType()){
                case PANDA:
                    nbMove = missionPandaStrat.howManyMoveToDoMission(mission);
                    break;
                case PARCEL:
                    nbMove = stratMissionParcel.howManyMoveToDoMission(mission);
                    break;
                case PEASANT:
                    nbMove = stratMissionPeasant.howManyMoveToDoMission(mission);
                    break;
            }
            if ( nbMove > 0){
                missionList.add(mission);
                intsList.add(new int[]{nbMove,mission.getPoints()});
            }
        }
        if (!missionList.isEmpty())
            return missionList.get(determineBestMission(intsList));
        else
            return gameInteraction.getInventoryMissions().get((randInt.nextInt(gameInteraction.getInventoryMissions().size())));
    }

    public int determineBestMission(List<int[]> intsList){
        int[] bestInts = new int[]{0,0};
        int bestMissionOrdinal = 0;
        for (int[] ints : intsList){
            if (ints[0] < bestInts[0]){
                ints[1] = bestInts[1];
            }
            else if (ints[0] == bestInts[0] && ints[1] > bestInts[1])
                ints[1] = bestInts[1];
        }
        for (int i = 0; i < intsList.size(); i++) {
            if (intsList.get(i)[0] == bestInts[0] && intsList.get(i)[1] == bestInts[1])
                bestMissionOrdinal = i;
        }
        return bestMissionOrdinal;
    }

    void playMission(Mission mission){
        switch (mission.getMissionType()){
            case PANDA:
                missionPandaStrat.stratOneTurn(mission);
                return;
            case PARCEL:
                stratMissionParcel.stratOneTurn(mission);
                return;
            case PEASANT:
                stratMissionPeasant.stratOneTurn(mission);
        }
    }

    public boolean isJudiciousPlayWeather(){
        return !gameInteraction.contains(ActionType.WEATHER);
    }

    public void playWeather(WeatherType weatherType){
        if(weatherType.equals(WeatherType.RAIN))
            stratRain();
        else if(weatherType.equals(WeatherType.THUNDERSTORM))
            stratThunderstorm();
        else if(weatherType.equals(WeatherType.QUESTION_MARK))
            stratQuestionMark();
        else if(weatherType.equals(WeatherType.CLOUD))
            stratCloud();
    }

    public void stratThunderstorm(){
        List<Coordinate> irrigatedParcelsWithMoreThan1Bamboo = getGameInteraction().getAllParcelsIrrigated()
                .stream()
                .filter( coordinate -> getGameInteraction().getPlacedParcelsNbBamboo(coordinate) > 0 && !getGameInteraction().getPlacedParcelInformation(coordinate).getImprovementType().equals(ImprovementType.ENCLOSURE) )
                .collect(Collectors.toList());
        if(!irrigatedParcelsWithMoreThan1Bamboo.isEmpty())
            getGameInteraction().rainAction(irrigatedParcelsWithMoreThan1Bamboo.get(0));
    }

    public void stratRain(){
        List<Coordinate> parcelsIrrigated= getGameInteraction().getAllParcelsIrrigated();
        List<Coordinate> parcelsIrrigatedWithFertilizer=parcelsIrrigated.stream().
                filter(coordinate -> getGameInteraction().getPlacedParcelInformation(coordinate).getImprovementType()
                        .equals(ImprovementType.FERTILIZER)).collect(Collectors.toList());
        if(!parcelsIrrigatedWithFertilizer.isEmpty())
            getGameInteraction().rainAction(parcelsIrrigatedWithFertilizer.get(0));
        else if(!parcelsIrrigated.isEmpty())
            getGameInteraction().rainAction(parcelsIrrigated.get(0));
    }

    public void stratQuestionMark(){
        getGameInteraction().questionMarkAction(WeatherType.SUN);
    }

    public void stratCloud(){
        if(getGameInteraction().getResourceSize(ResourceType.WATHERSHEDMPROVEMENT) > 0)
            getGameInteraction().cloudAction(ImprovementType.WATERSHED,WeatherType.SUN);
        else if(getGameInteraction().getResourceSize(ResourceType.FERTIZILERIMPROVEMENT) > 0)
            getGameInteraction().cloudAction(ImprovementType.FERTILIZER,WeatherType.SUN);
        else
            getGameInteraction().cloudAction(ImprovementType.ENCLOSURE,WeatherType.SUN);
        ImprovementType improvementType = (gameInteraction.getInventoryImprovementTypes().isEmpty()) ? null : gameInteraction.getInventoryImprovementTypes().get(0);
        List<Coordinate> coordinateList = gameInteraction.getPlacedCoordinates().stream()
                .filter(coordinate -> gameInteraction.getPlacedParcelInformation(coordinate).getImprovementType().equals(ImprovementType.NOTHING))
                .collect(Collectors.toList());
        if (improvementType != null && !coordinateList.isEmpty())
            gameInteraction.placeImprovement(improvementType,coordinateList.get(0));
    }

    public GameInteraction getGameInteraction(){
        return gameInteraction;
    }
}