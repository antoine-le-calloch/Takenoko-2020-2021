package fr.unice.polytech.startingpoint.bot;

import fr.unice.polytech.startingpoint.game.GameInteraction;
import fr.unice.polytech.startingpoint.game.board.Coordinate;
import fr.unice.polytech.startingpoint.game.board.BoardRules;
import fr.unice.polytech.startingpoint.type.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Strategie {
    protected final Bot bot;
    protected final BoardRules boardRules;

    public Strategie(Bot bot) {
        this.bot = bot;
        this.boardRules = bot.gameInteraction.getRules();
    }

    /**<p>The actions of the bot during his turn.</p>
     * @param weatherType
     */
    public abstract void stratOneTurn(WeatherType weatherType);

    /**
     * @return <b>True if the bot can draw a mission.</b>
     * @see GameInteraction
     */
    public boolean isJudiciousDrawMission(){
        int NB_MISSION_MAX = 5;
        return bot.gameInteraction.getResourceSize(ResourceType.PARCEL_MISSION) > 0  && !bot.gameInteraction.contains(ActionType.DRAW_MISSION) && bot.gameInteraction.getMissionsSize() < NB_MISSION_MAX;
    }

    public Coordinate stratThunderstorm(){
        List<Coordinate> irrigatedParcelsWithMoreThan1Bamboo= bot.gameInteraction.getAllParcelsIrrigated().stream().
                filter(coordinate -> bot.gameInteraction.getPlacedParcelsNbBamboo(coordinate)>0
                        && !bot.gameInteraction.getPlacedParcelInformation(coordinate).getImprovementType().equals(ImprovementType.ENCLOSURE))
                .collect(Collectors.toList());

        if(!irrigatedParcelsWithMoreThan1Bamboo.isEmpty()) {
            bot.gameInteraction.rainAction(irrigatedParcelsWithMoreThan1Bamboo.get(0));
            return irrigatedParcelsWithMoreThan1Bamboo.get(0);
        }
        return null;
    }

    public Coordinate stratRain(){
        List<Coordinate> parcelsIrrigated= bot.gameInteraction.getAllParcelsIrrigated();
        List<Coordinate> parcelsIrrigatedWithFertilizer=parcelsIrrigated.stream().
                filter(coordinate -> bot.gameInteraction.getPlacedParcelInformation(coordinate).getImprovementType()
                .equals(ImprovementType.FERTILIZER)).collect(Collectors.toList());
        if(!parcelsIrrigatedWithFertilizer.isEmpty()){
            bot.gameInteraction.rainAction(parcelsIrrigatedWithFertilizer.get(0));
            return parcelsIrrigatedWithFertilizer.get(0);

        }else if(!parcelsIrrigated.isEmpty()){
            bot.gameInteraction.rainAction(parcelsIrrigated.get(0));
            return parcelsIrrigated.get(0);
        }
        return null;
    }

    public void stratQuestionMark(){
        bot.getGameInteraction().questionMarkAction(WeatherType.SUN);
    }

    public void stratCloud(){
        if(bot.getGameInteraction().getResourceSize(ResourceType.WATHERSHEDMPROVEMENT)>0)
            bot.getGameInteraction().cloudAction(ImprovementType.WATERSHED,WeatherType.SUN);
        else if(bot.getGameInteraction().getResourceSize(ResourceType.FERTIZILERIMPROVEMENT)>0)
            bot.getGameInteraction().cloudAction(ImprovementType.FERTILIZER,WeatherType.SUN);
        else if(bot.getGameInteraction().getResourceSize(ResourceType.ENCLOSUREIMPROVEMENT)>0)
            bot.getGameInteraction().cloudAction(ImprovementType.ENCLOSURE,WeatherType.SUN);
        else {
            bot.getGameInteraction().cloudAction(ImprovementType.ENCLOSURE,WeatherType.SUN);
        }
    }

    /**@return <b>A list of all parcels’ coordinates present on the board and one layer of coordinates around.</b>
     */
    public List<Coordinate> allPlaces(){
        Set<Coordinate> possibleCoordinates = new HashSet<>();
        for(Coordinate c : bot.gameInteraction.getPlacedCoordinates()) {
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
        for(Coordinate c : bot.gameInteraction.getPlacedCoordinates()) {
            for (Coordinate offSet : Coordinate.offSets()){
                if(boardRules.isPlayableParcel(new Coordinate(c,offSet))){
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
        for(Coordinate coordinate1 : bot.gameInteraction.getPlacedCoordinates()){
            for(Coordinate coordinate2 : bot.gameInteraction.getPlacedCoordinates()){
                if (boardRules.isPlayableCanal(coordinate1, coordinate2))
                    possibleCoordinates.add(new Coordinate[]{coordinate1, coordinate2});
            }
        }
        return new ArrayList<>(possibleCoordinates);
    }

    /**@return <b>A list of coordinates where the Panda can be moved on the board.</b>
     */
    public List<Coordinate> possibleCoordinatesPanda(){
        Set<Coordinate> possibleCoordinates = new HashSet<>();
        for(Coordinate coordinate : bot.gameInteraction.getPlacedCoordinates()) {
            if (boardRules.isMovableCharacter(CharacterType.PANDA,coordinate)){
                possibleCoordinates.add(coordinate);
            }
        }
        return new ArrayList<>(possibleCoordinates);
    }

    /**@return <b>A list of coordinates  where the Peasant can be moved on the board.</b>
     */
    public List<Coordinate> possibleCoordinatesPeasant(){
        Set<Coordinate> possibleCoordinates = new HashSet<>();
        for(Coordinate c : bot.gameInteraction.getPlacedCoordinates()) {
            if (boardRules.isMovableCharacter(CharacterType.PEASANT,c)){
                possibleCoordinates.add(c);
            }
        }
        return new ArrayList<>(possibleCoordinates);
    }




    /**@return <b>A list of playable coordinates around a coordinate given </b>
     **/

    public List<Coordinate> playableCoordinatesAroundACoordinateGivenCo(Coordinate coordinate){
        return coordinate.coordinatesAround().stream()
                .filter(c-> boardRules.isPlayableParcel(c) && !bot.gameInteraction.isPlacedParcel(c))
                .collect(Collectors.toList());
    }


    /**@return <b>A list of all possible coordinates next to all parcels with the color given </b>
     **/

    public List<Coordinate> allPosssibleCoordinatesNextToParcelsWithAColor(ColorType colorGiven){

        Set<Coordinate> posssibleCoNextToParcelsWithAColor=new HashSet<>();
        List<Coordinate> placedCoordinatesByColor=bot.gameInteraction.getPlacedCoordinatesByColor(colorGiven);


        for (Coordinate  placedCoordinate: placedCoordinatesByColor)
            posssibleCoNextToParcelsWithAColor.addAll(playableCoordinatesAroundACoordinateGivenCo(placedCoordinate));


        return new ArrayList<>(posssibleCoNextToParcelsWithAColor);
    }




}
