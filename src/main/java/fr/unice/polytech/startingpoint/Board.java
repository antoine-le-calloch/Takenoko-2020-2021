package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.HashSet;

class Board {
    private final HashSet<Coordinate> allPlaces = new HashSet<>();
    private final HashSet<Coordinate> freePlaces = new HashSet<>();
    private final ArrayList<Coordinate> occupiedPlaces = new ArrayList<>();


    private final ArrayList<Parcel> placedParcels = new ArrayList<>();
    final ArrayList<Coordinate> irrigatedParcels = new ArrayList<>();
    private final ArrayList<Canal> placedCanals = new ArrayList<>();

    Board(){
        putParcel(new Parcel(),new Coordinate(0,0,0));
    }

    //place un canal et ajoute les parcel irrigé dans le set
    boolean putCanal(Canal canal, Coordinate coord, Coordinate coord2){

        if(playableCanal(coord,coord2)){
            canal.setCoordinates(coord,coord2);
            Parcel parcel= getParcelByCo(coord);
            Parcel parcel2= getParcelByCo(coord2);
            placedCanals.add(canal);
            if(!parcel.getIrrigated()) {
                parcel.setIrrigated();
                irrigatedParcels.add(parcel.getCoordinates());
            }
            if(!parcel2.getIrrigated()) {
                parcel2.setIrrigated();
                irrigatedParcels.add(parcel.getCoordinates());
            }
            return true;
        }
        return false;
     }

    boolean playableCanal(Coordinate toplacecoord1, Coordinate toplacecoord2){

        if(!isParcel(toplacecoord1) || !isParcel(toplacecoord2))
            return false;

        if( placedCanals.size()==0 && (!isNextoCentral(toplacecoord1) || (!isNextoCentral(toplacecoord2)) ))
            return false;

        if (toplacecoord1.equals(new Coordinate(0,0,0)) || toplacecoord2.equals(new Coordinate(0,0,0)))
            return false;

        for (Canal placedCanal : placedCanals) {

            Coordinate[] coordscanalplaced=placedCanal.getCoordinatesCanal();
            if( placedCanal.sameDoublecoordinates(toplacecoord1,toplacecoord2)){
                return false;
            }
            if(  ((coordscanalplaced[0].equals(toplacecoord1)) && (!coNextToEachother(toplacecoord2,coordscanalplaced[1]))) || ((coordscanalplaced[0].equals(toplacecoord2)) && (!coNextToEachother(toplacecoord1,coordscanalplaced[1]))) ) {
                return false;
            }
        }
        return true;
    }

    // renvoie true si une coordonée est à côté d'une autre
    boolean coNextToEachother(Coordinate coord1,Coordinate coord2){
        return (Coordinate.getNorm(coord1, coord2)) == 2;
    }

    // renvoie true si une coordonée est à côté du milieu
    boolean isNextoCentral(Coordinate coord){
        return (Coordinate.getNorm(coord,new Coordinate(0,0,0))) == 2;
    }

    //Place une parcelle sur le board
    void putParcel(Parcel newParcel,Coordinate newCoord){
        newParcel.setCoordinates(newCoord);
        setBoard(newParcel,newCoord);
    }

    void setBoard(Parcel newParcel,Coordinate newCoord){
        placedParcels.add(newParcel);
        occupiedPlaces.add(newCoord);
        freePlaces.remove(newCoord);

        for (Coordinate coord : newCoord.coordinatesAround()) {
            if(coord.isCentral())
                newParcel.setIrrigated();

            if(freeParcel(coord))
                freePlaces.add(coord);
            allPlaces.add(newCoord);
        }
    }

    //Obtient une parcelle par des coordonnées données
    Parcel getParcelByCo(Coordinate coordinate){
        for (Parcel placedParcel : placedParcels) {
            if(placedParcel.getCoordinates().equals(coordinate))
                return placedParcel;
        }
        return null;
    }

    boolean isParcel(Coordinate coord){
        return occupiedPlaces.contains(coord);
    }

    //Renvoie true si une parcelle est libre aux coordonnées pasées en paramètres
    boolean freeParcel(Coordinate coord){
        int nbParcelAround = 0;
        for(Coordinate coordAround : coord.coordinatesAround()) {
            if(occupiedPlaces.contains(coordAround))
                nbParcelAround++;
            if(coordAround.isCentral())
                return true;
        }
        return nbParcelAround>1;
    }

    //Renvoie une liste des places libres
    ArrayList<Coordinate> getFreePlaces(){
        return new ArrayList<>(freePlaces);
    }

    //Renvoie une liste de toutes les places existantes
    ArrayList<Coordinate> getAllPlaces(){
        return new ArrayList<>(allPlaces);
    }

    //Renvoie une liste des places occupées
    ArrayList<Coordinate> getOccupiedPlaces(){
        return occupiedPlaces;
    }

    //Renvoie une liste des parcels placé
    ArrayList<Parcel> getPlacedParcels(){
        return placedParcels;
    }

    //Renvoie une liste des canaux placé
    ArrayList<Canal> getPlacedCanals(){
        return placedCanals;
    }
}
