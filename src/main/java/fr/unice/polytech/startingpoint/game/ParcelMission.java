package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.type.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe representant une mission parcelle
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

public class ParcelMission extends Mission {
    private final FormType formType;

    ParcelMission(ColorType colorType, int points,FormType formType) {
        super(MissionType.PARCEL,colorType,points);
        this.formType = formType;
    }

    //Renvoie le nombre de points que les missions rapportent si elles ont été accomplies
    public int checkMission(Board board, Inventory inventory) {
        for (Coordinate coordParcel : board.getPlacedParcels().keySet()) {
            if(setForm(coordParcel, formType).stream().allMatch(board::isPlacedAndIrrigatedParcel))
                if(setForm(coordParcel, formType).stream().allMatch(coord -> board.getPlacedParcels().get(coord).getColor().equals(colorType)))
                    return points;
        }
        return 0;
    }

    public List<Coordinate> setForm(Coordinate hightCoord, FormType form){
        List<Coordinate> coordForm = new ArrayList<>();
        coordForm.add(hightCoord);
        coordForm.add(new Coordinate(hightCoord,Coordinate.offSets().get(2)));
        if(form.equals(FormType.LINE))
            coordForm.add(new Coordinate(coordForm.get(1),Coordinate.offSets().get(2)));
        else
            coordForm.add(new Coordinate(coordForm.get(1),Coordinate.offSets().get(4)));
        return coordForm;
    }

    //Renvoie l'objectif de la mission
    public FormType getFormType(){
        return formType;
    }
}