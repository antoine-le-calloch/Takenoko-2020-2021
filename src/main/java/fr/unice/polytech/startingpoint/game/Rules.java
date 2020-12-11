package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.CharacterType;

public final class Rules {
    private final Resource resource ;
    private final Board board;

    Rules(Resource resource, Board board){
        this.resource = resource;
        this.board = board;
    }

    //Renvoie true si une parcelle peut être placée à la coordonnée passée en paramètre
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

    //Renvoie true si un canal peut être placé aux coordonnées passées en paramètre
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

    //Renvoie true si un character peut être placé aux coordonnées passées en paramètre
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

    boolean isEmpty(){
        return ( (resource.getDeckCanal().size()==0 || resource.getDeckParcel().size()==0) ||
                (resource.getDeckPandaMission().size()==0 && resource.getDeckParcelMission().size()==0 && resource.getDeckPeasantMission().size()==0) );
    }
}