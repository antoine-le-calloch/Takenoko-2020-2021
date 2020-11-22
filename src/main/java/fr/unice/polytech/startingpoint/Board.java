package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.zip.CheckedOutputStream;

class Board {
    private final ArrayList<Parcel> placedParcels = new ArrayList<>();
    private final ArrayList<Canal> placedCanals = new ArrayList<>();

    Board(){
        placedParcels.add(new Parcel(new Coordinate(0,0,0)));
    }

   boolean putCanal(Canal canal, Coordinate coord, Coordinate coord2){
        if(playableCanal(coord,coord2)){
            canal.setCoordinates(coord,coord2);
            Parcel parcel=getParcelbyCo(coord);
            Parcel parcel2=getParcelbyCo(coord2);
            placedCanals.add(canal);
            if(!parcel.getIrrigated()) {
                parcel.setIrrigated();
            }
            if(!parcel2.getIrrigated()) {
                parcel2.setIrrigated();
            }
            return true;
        }
        return false;
    }

    boolean playableCanal(Coordinate coord, Coordinate coord2){
        if( !(isplacedParcel(coord) && isplacedParcel(coord2)) ){
            return false;
        }
        else if (Coordinate.getNorm(coord,coord2)!=2) {
            return false;
        }
        else if(placedCanals.size()==0 && ((Coordinate.getNorm(coord2,new Coordinate(0,0,0) ) > 2) || (Coordinate.getNorm(coord,new Coordinate(0,0,0) ) > 2))){
            return false;
        }
        for (Canal placedCanal : placedCanals) {
            Coordinate[] cocanal=placedCanal.getCoordinatesCanal();
            if( (cocanal[0].equals(coord) && cocanal[1].equals(coord2)) || (cocanal[0].equals(coord2) && cocanal[1].equals(coord))){
                return false;
            }
            if( ( ((cocanal[0].equals(coord)) && (Coordinate.getNorm(coord2, cocanal[1]) != 2)) || ((cocanal[0].equals(coord2)) && (Coordinate.getNorm(coord2, cocanal[1]) != 2)) ) )
            {
                return false;
            }
        }
        return true;
    }

    //Place une parcelle sur le board (quand cela est possible)
    boolean putParcel(Parcel parcel,Coordinate coord){
        if(playableParcel(coord)){
            parcel.setCoordinates(coord);
            placedParcels.add(parcel);
            if (Coordinate.getNorm(parcel.getCoordinates(),new Coordinate(0,0,0) )==2){
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

    ArrayList<Parcel> getParcel(){
        return placedParcels;
    }
}
