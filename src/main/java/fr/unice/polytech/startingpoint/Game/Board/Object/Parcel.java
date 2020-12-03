package fr.unice.polytech.startingpoint.Game.Board.Object;

import fr.unice.polytech.startingpoint.Type.*;
import fr.unice.polytech.startingpoint.Game.Board.Coordinate.*;

public class Parcel {
    private Coordinate coordinates;
    private boolean irrigated = false;
    private int nbBamboo = 0;
    private final ColorType colorType;

    public Parcel(ColorType colorType){
        this.colorType = colorType;
    }

    //Renvoie la parcelle après avoir fixé ses coordonnées avec les coordonnées passées en paramètre
    public Parcel setCoordinates(Coordinate coordinate) {
        coordinates = coordinate;
        return this;
    }

    //Renvoie les coordonnées de la parcelle après l'avoir irrigué et lui avoir ajouté un bambou si elle ne l'était pas avant
    public Coordinate setIrrigated() {
        if(!irrigated)
            addBamboo();
        irrigated = true;
        return coordinates;
    }

    //Ajoute un bamboo à la parcelle
    public boolean addBamboo(){
        if (nbBamboo < 4){
            nbBamboo ++;
            return true;
        }
        return false;
    }

    //Supprime un bambou de la parcelle
    public boolean delBamboo(){
        if (nbBamboo > 0){
            nbBamboo --;
            return true;
        }
        return false;
    }

    public int getNbBamboo(){
        return nbBamboo;
    }

    //Renvoie si la parcelle est irriguée ou non
    public boolean getIrrigated(){
        return irrigated;
    }

    //Renvoie les coordonnées de la parcelle
    public Coordinate getCoordinates(){
        return coordinates;
    }

    //Renvoie la couleur de la parcelle
    public ColorType getColor() {
        return colorType;
    }
}