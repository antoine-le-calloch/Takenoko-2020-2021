package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.CharacterType;

/**
 * <h1>{@link Rules} :</h1>
 *
 * <p>This class provides a class that check the actions asked by the bot.</p>
 *
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @see Game
 * @version 0.5
 */

public final class Rules {
    private final Resource resource ;
    private final Board board;

    /**
     * <p>Set up the rules. Initialize all variables.</p>
     *
     * @param resource
     *            <b>Resource object.</b>
     * @param board
     *            <b>Board object.</b>
     */
    Rules(Resource resource, Board board){
        this.resource = resource;
        this.board = board;
    }

    /**@return <b>True, if the parcel is playable on the coordinates specified in parameter.</b>
     */
    public boolean isPlayableParcel(Coordinate coord){
        if (coord.isCentral())
            return false;
        int nbParcelAround = 0;
        for(Coordinate coordAround : coord.coordinatesAround()) {
            if(board.isPlacedParcel(coordAround))
                nbParcelAround++;
            if(coordAround.isCentral())
                return true;
        }
        return nbParcelAround>1;
    }

    /**@return <b>True, if the canal is playable on the coordinates specified in parameter.</b>
     */
    public boolean isPlayableCanal(Coordinate coordinate1, Coordinate coordinate2) {
        if ( !board.isPlacedCanal(coordinate1, coordinate2) &&
                coordinate1.isNextTo(coordinate2) &&
                !coordinate1.isCentral() && !coordinate2.isCentral() &&
                board.isPlacedParcel(coordinate1) && board.isPlacedParcel(coordinate2) ) {
            if (coordinate1.isNextTo(new Coordinate(0, 0, 0)) && coordinate2.isNextTo(new Coordinate(0, 0, 0))) {
                return true;
            }
            for (Coordinate coordinate : Coordinate.getInCommonAroundCoordinates(coordinate1, coordinate2)) {
                if (board.isPlacedCanal(coordinate1, coordinate))
                    return true;
                if (board.isPlacedCanal(coordinate2, coordinate))
                    return true;
            }
        }
        return false;
    }

    /**@return <b>True, if the character is movable on the coordinates specified in parameter.</b>
     */
    public boolean isMovableCharacter(CharacterType characterType, Coordinate coordinate){
        Character character = board.getCharacter(characterType);
        if(board.isPlacedParcel(coordinate) && coordinate.isOnTheSameLine(character.getCoordinate())){
            if(!character.getCoordinate().isNextTo(coordinate)){
                for(Coordinate c : Coordinate.getAllCoordinatesBetween(character.getCoordinate(),coordinate)){
                    if(!board.isPlacedParcel(c))
                        return false;
                }
            }
            return true;
        }
        return false;
    }
}