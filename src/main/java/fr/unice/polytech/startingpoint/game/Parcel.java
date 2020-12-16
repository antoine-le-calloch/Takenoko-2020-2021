package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.exception.CantDeleteBambooException;
import fr.unice.polytech.startingpoint.type.*;

/**
 * Classe representant une parcelle
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

class Parcel {
    private Coordinate coordinates;
    private boolean irrigated = false;
    private int nbBamboo = 0;
    private final ColorType colorType;
    private ImprovementType improvementType;

    Parcel(ColorType colorType, ImprovementType improvementType){
        this.colorType = colorType;
        this.improvementType = improvementType;
        setIrrigatedImprovement();
    }

    Parcel(ColorType colorType){
        this.colorType = colorType;
        this.improvementType = ImprovementType.NOTHING;
        setIrrigatedImprovement();
    }

    Parcel(){
        this.colorType = ColorType.NO_COLOR;
        this.improvementType = ImprovementType.NOTHING;
        setIrrigatedImprovement();
    }

    void setImprovementType(ImprovementType improvementType) {
        this.improvementType = improvementType;
    }

    void setIrrigatedImprovement(){
        if (improvementType == ImprovementType.WATERSHED)
            setIrrigated();
    }

    //Renvoie la parcelle après avoir fixé ses coordonnées avec les coordonnées passées en paramètre
    Parcel setCoordinates(Coordinate coordinate) {
        coordinates = coordinate;
        return this;
    }

    //Renvoie les coordonnées de la parcelle après l'avoir irrigué et lui avoir ajouté un bambou si elle ne l'était pas avant
    void setIrrigated() {
        if(!irrigated)
            addBamboo();
        irrigated = true;
    }

    //Ajoute un bamboo à la parcelle
    void addBamboo(){
        if (nbBamboo < 4)
            nbBamboo ++;
        if (nbBamboo < 4 && improvementType == ImprovementType.FERTILIZER)
            nbBamboo ++;
    }

    //Supprime un bambou de la parcelle
    void delBamboo() throws CantDeleteBambooException {
        if (nbBamboo > 0 && improvementType != ImprovementType.ENCLOSURE)
            nbBamboo --;
        else
            throw new CantDeleteBambooException(coordinates);
    }

    int getNbBamboo(){
        return nbBamboo;
    }

    //Renvoie si la parcelle est irriguée ou non
    boolean getIrrigated(){
        return irrigated;
    }

    //Renvoie les coordonnées de la parcelle
    Coordinate getCoordinates(){
        return coordinates;
    }

    //Renvoie la couleur de la parcelle
    ColorType getColor() {
        return colorType;
    }
}