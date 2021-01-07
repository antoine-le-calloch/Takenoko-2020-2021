package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.bot.strategie.MissionParcelStrat;
import fr.unice.polytech.startingpoint.bot.strategie.MissionPeasantStrat;
import fr.unice.polytech.startingpoint.bot.strategie.RushPandaStrat;
import fr.unice.polytech.startingpoint.game.GameInteraction;
import fr.unice.polytech.startingpoint.game.mission.Mission;
import fr.unice.polytech.startingpoint.type.WeatherType;

import java.util.LinkedList;
import java.util.List;

public class IntelligentBot extends Bot {
    RushPandaStrat rushPandaStrat = new RushPandaStrat(this);
    MissionParcelStrat stratMissionParcel = new MissionParcelStrat(this);
    MissionPeasantStrat stratMissionPeasant = new MissionPeasantStrat(this);

    int NB_CHANGE_STRAT = 2;

    public IntelligentBot(GameInteraction gameInteraction) {
        super(gameInteraction);
    }

    @Override
    public void botPlay(WeatherType weatherType) {
        for (int i = gameInteraction.getStamina(); i > 0; i--) {


            Mission mission = determineBestMissionToDo();
            switch (mission.getMissionType()){
                case PANDA:
                    rushPandaStrat.stratOneTurn(weatherType,mission);
                    break;
                case PARCEL:
                    stratMissionParcel.stratOneTurn(weatherType,mission);
                    break;
                case PEASANT:
                    stratMissionPeasant.stratOneTurn(weatherType,mission);
                    break;
            }
        }
    }

    public Mission determineBestMissionToDo() {
        List<Mission> missionList = new LinkedList<>();
        List<int[]> intsList = new LinkedList<>();
        for (Mission mission : gameInteraction.getInventoryMissions()){
            int nbMove;
            switch (mission.getMissionType()){
                case PANDA:
                    nbMove = rushPandaStrat.howManyMoveToDoMission(mission);
                    if ( nbMove == -1){
                        missionList.add(mission);
                        intsList.add(new int[]{rushPandaStrat.howManyMoveToDoMission(mission),mission.getPoints()});
                    }
                    break;
                case PARCEL:
                    nbMove = stratMissionParcel.howManyMoveToDoMission(mission);
                    if ( nbMove == -1){
                        missionList.add(mission);
                        intsList.add(new int[]{nbMove,mission.getPoints()});
                    }
                    break;
                case PEASANT:
                    nbMove = stratMissionPeasant.howManyMoveToDoMission(mission);
                    if ( nbMove == -1){
                        missionList.add(mission);
                        intsList.add(new int[]{nbMove,mission.getPoints()});
                    }
                    break;
            }
        }
        return missionList.get(determineBestMission(intsList));
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

    /*

    Panda

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

    public Coordinate stratThunderstorm() {

        for(PandaMission pandaMission : bot.getGameInteraction().getInventoryPandaMissions()){

            Coordinate coordinateAllColor = strategyMissionAllColor();
            Coordinate coordinateOneColor = strategyMissionOneColor(pandaMission.getColorType());

            if( coordinateAllColor != null && pandaMission.getColorType().equals(ColorType.ALL_COLOR)){
                bot.getGameInteraction().thunderstormAction( coordinateAllColor );
                return coordinateAllColor;
            }
            else if( coordinateOneColor != null) {
                bot.getGameInteraction().thunderstormAction( coordinateOneColor );
                return coordinateOneColor;

            }
        }
        return null;
    }

    @Override
    public Coordinate stratRain() {
        for(PeasantMission peasantMission:bot.getGameInteraction().getInventoryPeasantMissions()){
            ColorType peasantMissionColor = peasantMission.getColorType();

            List<Coordinate> parcelsIrrigatedSameColorAsMission=bot.getGameInteraction().
                    getPlacedCoordinatesByColor(peasantMissionColor).stream()
                    .filter(bot.getGameInteraction()::isIrrigatedParcel)
                    .collect(Collectors.toList() );

            if(!parcelsIrrigatedSameColorAsMission.isEmpty()) {
                bot.getGameInteraction().rainAction(parcelsIrrigatedSameColorAsMission.get(0));
                return parcelsIrrigatedSameColorAsMission.get(0);
            }
        }
        return null;
    }

 */
}