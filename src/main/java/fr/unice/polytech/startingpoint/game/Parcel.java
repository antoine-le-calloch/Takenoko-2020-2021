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
    private boolean irrigated = false;
    private final ParcelInformation parcelInformation;

    Parcel(ColorType colorType, ImprovementType improvementType){
        parcelInformation = new ParcelInformation(colorType,improvementType);
        if (improvementType == ImprovementType.WATERSHED)
            setIrrigated();
    }

    Parcel(ColorType colorType){
        parcelInformation = new ParcelInformation(colorType);
    }

    Parcel(ImprovementType improvementType){
        parcelInformation = new ParcelInformation(improvementType);
        if (improvementType == ImprovementType.WATERSHED)
            setIrrigated();
    }

    Parcel(){
        parcelInformation = new ParcelInformation();
    }

    //Ajoute un bamboo à la parcelle
    void addBamboo(){
        if (nbBamboo < 4)
            nbBamboo ++;
        if (nbBamboo < 4 && parcelInformation.getImprovementType() == ImprovementType.FERTILIZER)
            nbBamboo ++;
    }

    //Supprime un bambou de la parcelle
    ColorType delBamboo(){
        if (nbBamboo > 0 && parcelInformation.getImprovementType() != ImprovementType.ENCLOSURE){
            nbBamboo --;
            return parcelInformation.getColorType();
        }
        return ColorType.NO_COLOR;
    }

    //Renvoie les coordonnées de la parcelle après l'avoir irrigué et lui avoir ajouté un bambou si elle ne l'était pas avant
    void setIrrigated() {
        if(!irrigated)
            addBamboo();
        irrigated = true;
    }

    void setImprovementType(ImprovementType improvementType) {
        parcelInformation.setImprovementType(improvementType);
    }

    //Renvoie si la parcelle est irriguée ou non
    boolean getIrrigated(){
        return irrigated;
    }

    //Renvoie la couleur de la parcelle
    ColorType getColor() {
        return parcelInformation.getColorType();
    }

    ImprovementType getImprovement(){
        return parcelInformation.getImprovementType();
    }

    ParcelInformation getParcelInformation() {
        return parcelInformation;
    }

    int getNbBamboo(){
        return nbBamboo;
    }
}