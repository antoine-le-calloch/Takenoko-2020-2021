package fr.unice.polytech.startingpoint.Game;

import fr.unice.polytech.startingpoint.Type.*;

public class Character {
    private final CharacterType characterType;
    private Coordinate coordinate;

    public Character(CharacterType characterType){
        this.characterType = characterType;
        coordinate = new Coordinate(0,0,0);
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate(){
        return coordinate;
    }

    public void action(Coordinate coordinate, Board board) {
        switch (characterType){
            case PANDA:
                board.getPlacedParcels().get(coordinate).delBamboo();
                break;
            case PEASANT:
                board.getPlacedParcels().get(coordinate).addBamboo();
                break;
        }
    }
}
