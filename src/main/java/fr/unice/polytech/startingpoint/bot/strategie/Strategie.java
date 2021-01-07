package fr.unice.polytech.startingpoint.bot.strategie;

import fr.unice.polytech.startingpoint.bot.Bot;
import fr.unice.polytech.startingpoint.game.GameInteraction;
import fr.unice.polytech.startingpoint.game.board.Coordinate;
import fr.unice.polytech.startingpoint.game.board.BoardRules;
import fr.unice.polytech.startingpoint.game.mission.Mission;
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
        this.boardRules = bot.getGameInteraction().getRules();
    }

    /**<p>The actions of the bot during his turn.</p>
     * @param mission
     */
    public abstract void stratOneTurn(Mission mission);

    public abstract int howManyMoveToDoMission(Mission mission);


    /**@return <b>A list of all parcels’ coordinates present on the board and one layer of coordinates around.</b>
     */
    public List<Coordinate> allPlaces(){
        Set<Coordinate> possibleCoordinates = new HashSet<>();
        for(Coordinate c : bot.getGameInteraction().getPlacedCoordinates()) {
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
        for(Coordinate c : bot.getGameInteraction().getPlacedCoordinates()) {
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
        for(Coordinate coordinate1 : bot.getGameInteraction().getPlacedCoordinates()){
            for(Coordinate coordinate2 : bot.getGameInteraction().getPlacedCoordinates()){
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
        for(Coordinate coordinate : bot.getGameInteraction().getPlacedCoordinates()) {
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
        for(Coordinate c : bot.getGameInteraction().getPlacedCoordinates()) {
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
                .filter(c-> boardRules.isPlayableParcel(c) && !bot.getGameInteraction().isPlacedParcel(c))
                .collect(Collectors.toList());
    }

    /**@return <b>A list of all possible coordinates next to all parcels with the color given </b>
     **/
    public List<Coordinate> allPosssibleCoordinatesNextToParcelsWithAColor(ColorType colorGiven){
        Set<Coordinate> posssibleCoNextToParcelsWithAColor=new HashSet<>();
        List<Coordinate> placedCoordinatesByColor=bot.getGameInteraction().getPlacedCoordinatesByColor(colorGiven);

        for (Coordinate  placedCoordinate: placedCoordinatesByColor)
            posssibleCoNextToParcelsWithAColor.addAll(playableCoordinatesAroundACoordinateGivenCo(placedCoordinate));

        return new ArrayList<>(posssibleCoNextToParcelsWithAColor);
    }
}