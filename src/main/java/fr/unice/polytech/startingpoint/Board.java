package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

class Board {
    private final ArrayList<Parcel> placedParcels = new ArrayList<>();
    private final ArrayList<Coordinate> placedCoords = new ArrayList<>();
    final ArrayList<Coordinate> irrigatedParcels = new ArrayList<>();
    private final ArrayList<Canal> placedCanals = new ArrayList<>();

    Board(){
        placedParcels.add(new Parcel(new Coordinate(0,0,0)));
    }

    //place un canal et ajoute les parcel irrigé dans le set
    boolean putCanal(Canal canal, Coordinate coord, Coordinate coord2){

        if(playableCanal(coord,coord2)){
            canal.setCoordinates(coord,coord2);
            Parcel parcel=getParcelbyCo(coord);
            Parcel parcel2=getParcelbyCo(coord2);
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

        if( !(isplacedParcel(toplacecoord1) && isplacedParcel(toplacecoord2)) ){
            return false;
        }
        if( placedCanals.size()==0 && (!isNextoCentral(toplacecoord1) || (!isNextoCentral(toplacecoord2)) )){
            return false;
        }
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
    boolean isNextoCentral(Coordinate coord){
        return (Coordinate.getNorm(coord,new Coordinate(0,0,0))) == 2;
    }



    //Place une parcelle sur le board (quand cela est possible)
    boolean putParcel(Parcel parcel,Coordinate coord){
        if(playableParcel(coord)){
            parcel.setCoordinates(coord);
            placedParcels.add(parcel);
            placedCoords.add(coord);
            if (isNextoCentral(coord)){
                parcel.setIrrigated();
            }
            return true;
        }
        return false;
    }

    //verifie si on peut poser une parcelle sur le board
    boolean playableParcel(Coordinate coord){
        int nbParcelAround = 0;
        for (Parcel placedParcel : placedParcels) {
            int norm = Coordinate.getNorm(coord,placedParcel.getCoordinates());
            if (norm == 0) {
                return false;
            }
            else if (norm == 2) {
                if (Coordinate.getNorm(new Coordinate(0,0,0),placedParcel.getCoordinates()) == 0) {
                    nbParcelAround++;
                }
                nbParcelAround++;
            }
        }
        return (nbParcelAround > 1);
    }

    //Renvoie un true si une parcelle est posée aux coordonnées pasées en paramètres
    boolean isplacedParcel(Coordinate coordinate){
        for (Parcel placedParcel : placedParcels) {
            if(placedParcel.getCoordinates().equals(coordinate))
                return true;
        }
        return false;
    }

    //Obtient une parcelle par des coordonnées données
    Parcel getParcelbyCo(Coordinate coordinate){
        for (Parcel placedParcel : placedParcels) {
            if(placedParcel.getCoordinates().equals(coordinate))
                return placedParcel;
        }
        return null;
    }

    ArrayList<Parcel> getPlacedparcels(){
        return placedParcels;
    }

    ArrayList<Canal> getPlacedcanals(){
        return placedCanals;
    }

    ArrayList<Coordinate> getPlacedCoord(){
        return placedCoords;
    }
}
