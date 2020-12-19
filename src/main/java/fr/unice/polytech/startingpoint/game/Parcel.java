package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.*;

/**
 * Classe representant une parcelle
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 0.5
 */

class Parcel {
    private int nbBamboo = 0;
    private final ColorType colorType;
    private boolean irrigated = false;
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

    Parcel(ImprovementType improvementType){
        this.colorType = ColorType.NO_COLOR;
        this.improvementType = improvementType;
        setIrrigatedImprovement();
    }

    Parcel(){
        this.colorType = ColorType.NO_COLOR;
        this.improvementType = ImprovementType.NOTHING;
        setIrrigatedImprovement();
    }

    //Ajoute un bamboo à la parcelle
    void addBamboo(){
        if (nbBamboo < 4)
            nbBamboo ++;
        if (nbBamboo < 4 && improvementType == ImprovementType.FERTILIZER)
            nbBamboo ++;
    }

    //Supprime un bambou de la parcelle
    ColorType delBamboo(){
        if (nbBamboo > 0 && improvementType != ImprovementType.ENCLOSURE){
            nbBamboo --;
            return colorType;
        }
        return ColorType.NO_COLOR;
    }

    //Renvoie les coordonnées de la parcelle après l'avoir irrigué et lui avoir ajouté un bambou si elle ne l'était pas avant
    void setIrrigated() {
        if(!irrigated)
            addBamboo();
        irrigated = true;
    }

    void setIrrigatedImprovement(){
        if (improvementType == ImprovementType.WATERSHED)
            setIrrigated();
    }

    void setImprovementType(ImprovementType improvementType) {
        this.improvementType = improvementType;
    }

    //Renvoie si la parcelle est irriguée ou non
    boolean getIrrigated(){
        return irrigated;
    }

    //Renvoie la couleur de la parcelle
    ColorType getColor() {
        return colorType;
    }

    int getNbBamboo(){
        return nbBamboo;
    }
}