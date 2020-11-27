package fr.unice.polytech.startingpoint;

import java.util.*;

class Board {
    private final Set<Coordinate> freePlaces = new HashSet<>();
    private final Map<Coordinate,Parcel> placedParcels = new HashMap<>();
    private final List<Coordinate> irrigatedParcels = new ArrayList<>();
    private final List<Canal> placedCanals = new ArrayList<>();

    Board(){
        placeParcel(new Parcel("noColor"),new Coordinate(0,0,0));
    }

    //place un canal et ajoute les parcel irrigé dans le set
    boolean putCanal(Canal canal, Coordinate coord1, Coordinate coord2){
        if(playableCanal(coord1,coord2)){
            placedCanals.add(canal.setCoordinates(coord1,coord2));
            if(!placedParcels.get(coord1).setIrrigated()) {
                irrigatedParcels.add(coord1);
            }
            if(!placedParcels.get(coord2).setIrrigated()) {
                irrigatedParcels.add(coord2);
            }
            return true;
        }
        return false;
     }

    boolean playableCanal(Coordinate toplacecoord1, Coordinate toplacecoord2){
        if(!isPlaced(toplacecoord1) || !isPlaced(toplacecoord2))
            return false;

        if( placedCanals.size()==0 && (!Coordinate.isNextoCentral(toplacecoord1) || (!Coordinate.isNextoCentral(toplacecoord2)) ))
            return false;

        if (toplacecoord1.equals(new Coordinate(0,0,0)) || toplacecoord2.equals(new Coordinate(0,0,0)))
            return false;

        for (Canal placedCanal : placedCanals) {

            Coordinate[] coordscanalplaced = placedCanal.getCoordinatesCanal();
            if( placedCanal.sameDoubleCoordinates(toplacecoord1,toplacecoord2)){
                return false;
            }
            if(  ((coordscanalplaced[0].equals(toplacecoord1)) && (!Coordinate.coNextToEachother(toplacecoord2,coordscanalplaced[1]))) || ((coordscanalplaced[0].equals(toplacecoord2)) && (!Coordinate.coNextToEachother(toplacecoord1,coordscanalplaced[1]))) ) {
                return false;
            }
        }
        return true;
    }

    //Place une parcelle sur le board
    void placeParcel(Parcel newParcel, Coordinate newCoordinate){
        newParcel.setCoordinates(newCoordinate);
        placedParcels.put(newCoordinate,newParcel);
        freePlaces.remove(newCoordinate);

        for (Coordinate coordinate : newCoordinate.coordinatesAround()) {
            if(coordinate.isCentral())
                newParcel.setIrrigated();
            if(freeParcel(coordinate))
                freePlaces.add(coordinate);
        }
    }

    boolean isPlaced(Coordinate coordinate){
        return placedParcels.keySet().contains(coordinate);
    }

    //Renvoie true si une parcelle est libre aux coordonnées pasées en paramètres
    boolean freeParcel(Coordinate coord){
        int nbParcelAround = 0;
        for(Coordinate coordAround : coord.coordinatesAround()) {
            if(isPlaced(coordAround))
                nbParcelAround++;
            if(coordAround.isCentral())
                return true;
        }
        return nbParcelAround>1;
    }

    //Renvoie une liste des places libres
    List<Coordinate> getFreePlaces(){
        return new ArrayList<>(freePlaces);
    }

    List<Coordinate> getAllPlaces() {
        List<Coordinate> allPlaces = new ArrayList<>(placedParcels.keySet());
        allPlaces.addAll(getFreePlaces());
        return allPlaces;
    }

    //Renvoie une liste des parcels placé
    Map<Coordinate,Parcel> getPlacedParcels(){
        return placedParcels;
    }

    List<Coordinate> getIrrigatedParcels() {
        return irrigatedParcels;
    }

    //Renvoie une liste des canaux placé
    List<Canal> getPlacedCanals(){
        return placedCanals;
    }
}
