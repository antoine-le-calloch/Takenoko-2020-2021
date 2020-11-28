package fr.unice.polytech.startingpoint;

import java.util.*;

class Board {
    private final Set<Coordinate> playablePlaces = new HashSet<>();
    private final Map<Coordinate, Parcel> placedParcels = new HashMap<>();
    private final Set<Coordinate> irrigatedParcels = new HashSet<>();
    private final Map<SortedSet<Coordinate>, Canal> placedCanals = new HashMap<>();

    Board() {
        initializeCenter();
    }

    //Initialise la case centrale
    private void initializeCenter() {
        placedParcels.put(new Coordinate(0, 0, 0),new Parcel("noColor").setCoordinates(new Coordinate(0, 0, 0)));
        playablePlaces.addAll(Coordinate.offSets());
    }

    //Renvoie true si une parcelle peut être placée à la coordonnée passée en paramètre
    boolean playableParcel(Coordinate coord){
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
    boolean playableCanal(Coordinate toPlaceCoordinate1, Coordinate toPlaceCoordinate2) {
        if ( !isPlacedCanal(toPlaceCoordinate1, toPlaceCoordinate2) ){
            if ( toPlaceCoordinate1.isNextTo(toPlaceCoordinate2) ){
                if ( isPlacedParcel(toPlaceCoordinate1) && isPlacedParcel(toPlaceCoordinate2) ){
                    if ( toPlaceCoordinate1.isNextTo(new Coordinate(0, 0, 0)) && toPlaceCoordinate2.isNextTo(new Coordinate(0, 0, 0)) ) {
                        return true;
                    }
                    for ( Coordinate coordinate : Coordinate.getInCommonAroundCoordinates(toPlaceCoordinate1, toPlaceCoordinate2) ) {
                        if ( isPlacedCanal(toPlaceCoordinate1, coordinate) )
                            return true;
                        if ( isPlacedCanal(toPlaceCoordinate2, coordinate) )
                            return true;
                    }
                }
            }
        }
        return false;
    }

    //Place une parcelle sur le board si les conditions le permettent
    boolean placeParcel(Parcel newParcel, Coordinate newCoordinate){
        if(playableParcel(newCoordinate)){
            placedParcels.put(newCoordinate,newParcel.setCoordinates(newCoordinate));
            playablePlaces.remove(newCoordinate);
            for (Coordinate coordinate : newCoordinate.coordinatesAround()) {
                if(coordinate.isCentral()) {
                    irrigatedParcels.add(newParcel.setIrrigated());
                }
                if(playableParcel(coordinate))
                    playablePlaces.add(coordinate);
            }
            return true;
        }
        return false;
    }

    //Place un canal sur le board si les conditions le permettent
    boolean placeCanal(Canal canal, Coordinate coordinate1, Coordinate coordinate2) {
        if (playableCanal(coordinate1, coordinate2)) {
            placedCanals.put(Coordinate.getSortedSet(coordinate1, coordinate2), canal.setCoordinates(coordinate1, coordinate2));

            if (!placedParcels.get(coordinate1).getIrrigated()) {
                irrigatedParcels.add(placedParcels.get(coordinate1).setIrrigated());
            }
            if (!placedParcels.get(coordinate2).getIrrigated()) {
                irrigatedParcels.add(placedParcels.get(coordinate2).setIrrigated());
            }
            return true;
        }
        return false;
    }

    //Renvoie true si une parcelle est posées aux coordonnées passées en paramètre
    boolean isPlacedParcel(Coordinate coordinate){
        return placedParcels.containsKey(coordinate);
    }

    //Renvoie true si un canal est posé aux coordonnées passées en paramètre
    boolean isPlacedCanal(Coordinate coordinate1,Coordinate coordinate2){
        return placedCanals.containsKey(Coordinate.getSortedSet(coordinate1,coordinate2));
    }

    void irrigatedParcelsAdd(Coordinate coordinate) {
        irrigatedParcels.add(coordinate);
    }

    //Renvoie une liste des places jouables
    List<Coordinate> getPlayablePlaces(){
        return new ArrayList(playablePlaces);
    }

    //Renvoie une liste de toutes les places occupées et jouables
    List<Coordinate> getAllPlaces() {
        Set<Coordinate> allPlaces = new HashSet<>(placedParcels.keySet());
        allPlaces.addAll(getPlayablePlaces());
        allPlaces.remove(new Coordinate(0,0,0));
        return new ArrayList(allPlaces);
    }

    //Renvoie une map des parcelles placées
    Map<Coordinate,Parcel> getPlacedParcels(){
        return placedParcels;
    }

    //Renvoie une liste des parcelles irriguées
    List<Coordinate> getIrrigatedParcels() {
        return new ArrayList(irrigatedParcels);
    }

    //Renvoie une map des canaux placés
    Map<SortedSet<Coordinate>,Canal> getPlacedCanals(){
        return placedCanals;
    }
}