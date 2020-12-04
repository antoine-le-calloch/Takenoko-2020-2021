package fr.unice.polytech.startingpoint.Game;

import fr.unice.polytech.startingpoint.Type.*;

import java.util.*;

/**
 * Classe representant le plateau de jeu
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */


public class Board {
    private final Character panda = new Character(CharacterType.PANDA);
    private final Character peasant = new Character(CharacterType.PEASANT);
    private final Map<Coordinate, Parcel> placedParcels = new HashMap<>();
    private final Map<SortedSet<Coordinate>, Canal> placedCanals = new HashMap<>();

    public Board() {
        placedParcels.put(new Coordinate(0, 0, 0),new Parcel(ColorType.NO_COLOR).setCoordinates(new Coordinate(0, 0, 0)));
    }

    //Renvoie true si une parcelle peut être placée à la coordonnée passée en paramètre
    public boolean isPlayableParcel(Coordinate coord){
        int nbParcelAround = 0;
        for(Coordinate coordAround : coord.coordinatesAround()) {
            if(isPlacedParcel(coordAround))
                nbParcelAround++;
            if(coordAround.isCentral())
                return true;
        }
        return nbParcelAround>1;
    }

    //Renvoie true si un canal peut être placé aux coordonnées passées en paramètre
    public boolean isPlayableCanal(Coordinate toPlaceCoordinate1, Coordinate toPlaceCoordinate2) {
        if ( !isPlacedCanal(toPlaceCoordinate1, toPlaceCoordinate2) &&
                toPlaceCoordinate1.isNextTo(toPlaceCoordinate2) &&
                isPlacedParcel(toPlaceCoordinate1) && isPlacedParcel(toPlaceCoordinate2) ) {
            if (toPlaceCoordinate1.isNextTo(new Coordinate(0, 0, 0)) && toPlaceCoordinate2.isNextTo(new Coordinate(0, 0, 0))) {
                return true;
            }
            for (Coordinate coordinate : Coordinate.getInCommonAroundCoordinates(toPlaceCoordinate1, toPlaceCoordinate2)) {
                if (isPlacedCanal(toPlaceCoordinate1, coordinate))
                    return true;
                if (isPlacedCanal(toPlaceCoordinate2, coordinate))
                    return true;
            }
        }
        return false;
    }

    //Renvoie true si un character peut être placé aux coordonnées passées en paramètre
    public boolean isMovableCharacter(Character character, Coordinate coordinate){
        if(placedParcels.containsKey(coordinate) && coordinate.isOnTheSameLine(character.getCoordinate())){
            if(!character.getCoordinate().isNextTo(coordinate)){
                for(Coordinate c : Coordinate.getAllCoordinatesBetween(character.getCoordinate(),coordinate)){
                    if(!placedParcels.containsKey(c))
                        return false;
                }
            }
            return true;
        }
        return false;
    }

    //Place une parcelle sur le board si les conditions le permettent
    public boolean placeParcel(Parcel newParcel, Coordinate newCoordinate){
        if(isPlayableParcel(newCoordinate)){
            placedParcels.put(newCoordinate,newParcel.setCoordinates(newCoordinate));
            for (Coordinate coordinate : newCoordinate.coordinatesAround()) {
                if(coordinate.isCentral())
                    newParcel.setIrrigated();
            }
            return true;
        }
        return false;
    }

    //Place un canal sur le board si les conditions le permettent
    public boolean placeCanal(Canal canal, Coordinate coordinate1, Coordinate coordinate2) {
        if (isPlayableCanal(coordinate1, coordinate2)) {
            placedCanals.put(Coordinate.getSortedSet(coordinate1, coordinate2),canal.setCoordinates(coordinate1, coordinate2));
            placedParcels.get(coordinate1).setIrrigated();
            placedParcels.get(coordinate2).setIrrigated();
            return true;
        }
        return false;
    }

    //Fait bouger un personnage et effectue son action si les conditions le permettent
    public boolean moveCharacter(Character character, Coordinate coordinate){
        if(isMovableCharacter(character,coordinate)){
            character.setCoordinate(coordinate);
            characterAction(character);
            return true;
        }
        return false;
    }

    //Effectue l’action du personnage passé en paramètre
    public void characterAction(Character character){
        switch (character.getCharacterType()){
            case PANDA:
                placedParcels.get(character.getCoordinate()).delBamboo();
                break;
            case PEASANT:
                placedParcels.get(character.getCoordinate()).addBamboo();
                break;
        }
    }

    //Renvoie true si une parcelle est posées aux coordonnées passées en paramètre
    public boolean isPlacedParcel(Coordinate coordinate){
        return placedParcels.containsKey(coordinate);
    }

    //Renvoie true si une parcelle est posée et est irriguée aux coordonnées passées en paramètre
    public boolean isPlacedAndIrrigatedParcel(Coordinate coordinate){
        if (isPlacedParcel(coordinate))
            return placedParcels.get(coordinate).getIrrigated();
        return false;
    }

    //Renvoie true si un canal est posé aux coordonnées passées en paramètre
    public boolean isPlacedCanal(Coordinate coordinate1, Coordinate coordinate2){
        return placedCanals.containsKey(Coordinate.getSortedSet(coordinate1,coordinate2));
    }

    //Renvoie une map des parcelles placées
    public Map<Coordinate, Parcel> getPlacedParcels(){
        return placedParcels;
    }

    //Renvoie une map des canaux placés
    public Map<SortedSet<Coordinate>, Canal> getPlacedCanals(){
        return placedCanals;
    }

    //Renvoie le
    public Character getPanda() {
        return panda;
    }

    //Renvoie une map des canaux placés
    public Character getPeasant() {
        return peasant;
    }
}