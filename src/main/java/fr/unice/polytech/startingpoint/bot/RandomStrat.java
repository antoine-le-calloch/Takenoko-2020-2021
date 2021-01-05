package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.game.Coordinate;
import fr.unice.polytech.startingpoint.game.ParcelInformation;
import fr.unice.polytech.startingpoint.type.ActionType;
import fr.unice.polytech.startingpoint.type.MissionType;
import fr.unice.polytech.startingpoint.type.ResourceType;
import fr.unice.polytech.startingpoint.type.WeatherType;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomStrat extends Strategie{
    private Random random;
    private Random random2;

    /**<p>Set up the bot. Call the constructor from {@link Bot} superclass and initialize two {@link Random} objects.</p>
     *
     * @param bot
     *            <b>Bot object.</b>
     */
    public RandomStrat(Bot bot) {
        super(bot);
        random = new Random();
        random2 = new Random();
    }



    /**<p>Set the {@link #random} and {@link #random2} to new objects specified in the parameters.</p>
     *
     * @param rand1
     *            <b>The first {@link Random} object.</b>
     * @param rand2
     *            <b>The second {@link Random} object.</b>
     */
    public void setRand(Random rand1, Random rand2){
        random = rand1;
        random2 = rand2;
    }

    /**<p>The actions of the bot during his turn.</p>
     * @param weatherType
     */
    public void stratOneTurn(WeatherType weatherType){

        if(isJudiciousPlayWeather())
            playWeather(weatherType);

        int randAction = random.nextInt(5);

        if (randAction == 0 && bot.gameInteraction.getResourceSize(ResourceType.ALL_MISSION) > 0 && !bot.gameInteraction.contains(ActionType.DRAW_MISSION)) {// pioche mission
            int randMission = random2.nextInt(3);

            if (randMission == 0 && bot.gameInteraction.getResourceSize(ResourceType.PARCEL_MISSION) > 0)
                bot.drawMission(MissionType.PARCEL);
            if (randMission == 1 && bot.gameInteraction.getResourceSize(ResourceType.PANDA_MISSION) > 0)
                bot.drawMission(MissionType.PANDA);
            if (randMission == 2 && bot.gameInteraction.getResourceSize(ResourceType.PEASANT_MISSION) > 0)
                bot.drawMission(MissionType.PEASANT);
        }

        else if (randAction == 1 && bot.gameInteraction.getResourceSize(ResourceType.CANAL) > 0 && !bot.gameInteraction.contains(ActionType.DRAW_CANAL)) {  // place canal
            if (possibleCoordinatesCanal().size() > 0) {
                List<Coordinate[]> list = possibleCoordinatesCanal();
                Collections.shuffle(list);
                bot.drawCanal();
                bot.placeCanal(list.get(0));
            }
        }

        else if (randAction == 2 && bot.gameInteraction.getResourceSize(ResourceType.PARCEL) > 0 && !bot.gameInteraction.contains(ActionType.DRAW_PARCELS)){ // place parcel
            List<ParcelInformation> parcelList = bot.drawParcel();
            Collections.shuffle(parcelList);
            bot.selectParcel(parcelList.get(0));
            List<Coordinate> list = possibleCoordinatesParcel();
            Collections.shuffle(list);
            bot.placeParcel(list.get(0));
        }

        else if (randAction == 3 && possibleCoordinatesPanda().size() != 0 && !bot.gameInteraction.contains(ActionType.MOVE_PANDA)) {
            List<Coordinate> list = possibleCoordinatesPanda();
            Collections.shuffle(list);
            bot.movePanda(list.get(0));
        }

        else if (possibleCoordinatesPeasant().size() != 0 && !bot.gameInteraction.contains(ActionType.MOVE_PEASANT)) {
            List<Coordinate> list = possibleCoordinatesPeasant();
            Collections.shuffle(list);
            bot.movePeasant(list.get(0));
        }
    }


    public boolean isJudiciousPlayWeather(){
        if(!bot.gameInteraction.contains(ActionType.WEATHER)){
            return true;
        }
        return false;
    }

    public void playWeather(WeatherType weatherType){
        if(weatherType.equals(WeatherType.RAIN))
            stratRain();
        else if(weatherType.equals(WeatherType.THUNDERSTORM))
            stratThunderstorm();
    }
}