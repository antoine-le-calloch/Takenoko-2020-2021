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
    protected final MissionParcelStrat missionParcelStrat;
    protected final MissionPeasantStrat missionPeasantStrat;

    protected final GameInteraction gameInteraction;

    public Bot(GameInteraction gameInteraction) {
        this.gameInteraction = gameInteraction;
        this.missionPandaStrat = new MissionPandaStrat(gameInteraction);
        this.missionParcelStrat = new MissionParcelStrat(gameInteraction);
        this.missionPeasantStrat = new MissionPeasantStrat(gameInteraction);
    }

    public void botPlay(WeatherType weatherType) {
        if (isJudiciousPlayWeather())
            playWeather(weatherType);
        for (int i = gameInteraction.getStamina(); i > 0; i--) {
            if(isJudiciousDrawMission())
                drawMission(bestMissionTypeToDraw());
            else
                playMission(determineBestMissionToDo());
        }
    }

    /**<b><u>WEATHER HANDLING
     */

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
        List<Coordinate> irrigatedParcelsWithMoreThan1Bamboo = gameInteraction.getAllParcelsIrrigated()
                .stream()
                .filter( coordinate -> gameInteraction.getPlacedParcelsNbBamboo(coordinate) > 0 && !gameInteraction.getPlacedParcelInformation(coordinate).getImprovementType().equals(ImprovementType.ENCLOSURE) )
                .collect(Collectors.toList());
        if(!irrigatedParcelsWithMoreThan1Bamboo.isEmpty())
            gameInteraction.rainAction(irrigatedParcelsWithMoreThan1Bamboo.get(0));
    }

    public void stratRain(){
        List<Coordinate> parcelsIrrigated= gameInteraction.getAllParcelsIrrigated();
        List<Coordinate> parcelsIrrigatedWithFertilizer=parcelsIrrigated.stream().
                filter(coordinate -> gameInteraction.getPlacedParcelInformation(coordinate).getImprovementType()
                        .equals(ImprovementType.FERTILIZER)).collect(Collectors.toList());
        if(!parcelsIrrigatedWithFertilizer.isEmpty())
            gameInteraction.rainAction(parcelsIrrigatedWithFertilizer.get(0));
        else if(!parcelsIrrigated.isEmpty())
            gameInteraction.rainAction(parcelsIrrigated.get(0));
    }

    public void stratQuestionMark(){
        gameInteraction.questionMarkAction(WeatherType.SUN);
    }

    public void stratCloud(){
        if(gameInteraction.getResourceSize(ResourceType.WATERSHED_IMPROVEMENT) > 0)
            gameInteraction.cloudAction(ImprovementType.WATERSHED,WeatherType.SUN);
        else if(gameInteraction.getResourceSize(ResourceType.FERTILIZER_IMPROVEMENT) > 0)
            gameInteraction.cloudAction(ImprovementType.FERTILIZER,WeatherType.SUN);
        else
            gameInteraction.cloudAction(ImprovementType.ENCLOSURE,WeatherType.SUN);
        ImprovementType improvementType = (gameInteraction.getInventoryImprovementTypes().isEmpty()) ? null : gameInteraction.getInventoryImprovementTypes().get(0);
        List<Coordinate> coordinateList = gameInteraction.getPlacedCoordinates().stream()
                .filter(coordinate -> gameInteraction.getPlacedParcelInformation(coordinate).getImprovementType().equals(ImprovementType.NOTHING))
                .collect(Collectors.toList());
        if (improvementType != null && !coordinateList.isEmpty())
            gameInteraction.placeImprovement(improvementType,coordinateList.get(0));
    }

    /**<b><u>MISSION HANDLING
     */

    public MissionType chooseMissionTypeDrawable(MissionType missionType1,MissionType missionType2,MissionType missionType3) {
        if (gameInteraction.getResourceSize(ResourceType.get(missionType1)) > 0)
            return missionType1;
        else if (gameInteraction.getResourceSize(ResourceType.get(missionType2)) > 0)
            return missionType2;
        else
            return missionType3;
    }

    boolean isJudiciousDrawMission() {
        int NB_MAX_MISSION = 5;
        return gameInteraction.getMissionsSize() < NB_MAX_MISSION &&
                gameInteraction.getResourceSize(ResourceType.ALL_MISSION) > 0 &&
                !gameInteraction.contains(ActionType.DRAW_MISSION);
    }

    /**<p>Draw a mission with the type required in the resources.</p>
     *
     * @param missionType
     *            <b>The type of the mission the bot want to draw.</b>
     */
    public void drawMission(MissionType missionType){
        gameInteraction.drawMission(missionType);
    }

    protected abstract MissionType bestMissionTypeToDraw();

    Mission determineBestMissionToDo() {
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
                    nbMove = missionParcelStrat.howManyMoveToDoMission(mission);
                    break;
                case PEASANT:
                    nbMove = missionPeasantStrat.howManyMoveToDoMission(mission);
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

    int determineBestMission(List<int[]> intsList){
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
                missionParcelStrat.stratOneTurn(mission);
                return;
            case PEASANT:
                missionPeasantStrat.stratOneTurn(mission);
        }
    }
}