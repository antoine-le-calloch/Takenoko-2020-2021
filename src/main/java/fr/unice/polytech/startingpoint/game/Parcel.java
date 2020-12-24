package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.ColorType;
import fr.unice.polytech.startingpoint.type.ImprovementType;

/**
 * Classe representant une parcelle
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 0.5
 */

class Parcel {
    private final int NUMBER_MINIMAL_OF_BAMBOOS=0;
    private final int NUMBER_MAXIMAL_OF_BAMBOOS=4;
    private int nbBamboo = NUMBER_MINIMAL_OF_BAMBOOS;
    private boolean irrigated = false;
    private final ParcelInformation parcelInformation;

    Parcel(ColorType colorType, ImprovementType improvementType){
        parcelInformation = new ParcelInformation(colorType,improvementType);
        if (improvementType == ImprovementType.WATERSHED)
            setIrrigated();
    }

    Parcel(ColorType colorType){
        this(colorType,ImprovementType.NOTHING);
    }

    Parcel(ImprovementType improvementType){
        this(ColorType.NO_COLOR,improvementType);
    }

    Parcel(){
        this(ColorType.NO_COLOR,ImprovementType.NOTHING);
    }

    //Ajoute un bamboo à la parcelle
    void addBamboo(){
        if (nbBamboo < NUMBER_MAXIMAL_OF_BAMBOOS)
            nbBamboo ++;
        if (nbBamboo < NUMBER_MAXIMAL_OF_BAMBOOS && parcelInformation.getImprovementType() == ImprovementType.FERTILIZER)
            nbBamboo ++;
    }

    //Supprime un bambou de la parcelle
    ColorType delBamboo(){
        if (nbBamboo > NUMBER_MINIMAL_OF_BAMBOOS && parcelInformation.getImprovementType() != ImprovementType.ENCLOSURE){
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
        if (improvementType == ImprovementType.WATERSHED)
            setIrrigated();
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