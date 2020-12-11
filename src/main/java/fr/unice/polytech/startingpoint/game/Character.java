package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.*;

/**
 * Classe representant un personnage : le panda ou le paysan
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

class Character {
    private final CharacterType characterType;
    private Coordinate coordinate;

    Character(CharacterType characterType){
        this.characterType = characterType;
        coordinate = new Coordinate(0,0,0);
    }

    void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    CharacterType getCharacterType() {
        return characterType;
    }

    Coordinate getCoordinate(){
        return coordinate;
    }
}
