package fr.unice.polytech.startingpoint.Bot;

import fr.unice.polytech.startingpoint.Game.*;
import fr.unice.polytech.startingpoint.Type.*;

import java.util.*;

/**
 * Classe qui represente un bot qui joue intelligemment en completant seulement des missions parcels
 * @author Manuel Enzo
 * @author Naud Eric
 * @author Madern Loic
 * @author Le Calloch Antoine
 * @version 2020.12.03
 */

public class ParcelBot extends Bot {

    public ParcelBot(Resource resource, Board board) {
        super(resource, board);
    }

    @Override
    public void botPlay(){
        if (doDrawMission() && resource.getDeckParcelMission().size() > 0)
            drawMission(MissionType.PARCEL);
        if (resource.getParcel().size() > 0){
            putParcel();
        }
        if (resource.getCanal().size() > 0 && possibleCoordinatesCanal().size() > 0) {
            placeCanal(possibleCoordinatesCanal().get(0));
        }
    }

    //Si le bot n'a pas de mission => true
    public boolean doDrawMission(){
        return getInventory().getMission().size() <= 5;
    }

    //Pour chaque mission, pose une cases a la meilleur place pour la terminer, ou pose sur une place random
    public void putParcel() {
        ParcelMission mission = (ParcelMission) getInventory().getMission().get(0);
        FormType formType = mission.getFormType();
        ColorType colorType = mission.getColor();
        Parcel newParcel = resource.drawParcel();

        if(newParcel.getColor().equals(colorType))
            placeParcel(bestCoordinatesForForm(formType, colorType),newParcel);
        else {
            List<Coordinate> list = possibleCoordinatesParcel();
            Collections.shuffle(list);
            placeParcel(possibleCoordinatesParcel().get(0), newParcel);
        }
    }

    //renvoie la coord de la pièce a poser pour terminer le plus vite une forme [form], ou renvoie une coord random
    public Coordinate bestCoordinatesForForm(FormType formType, ColorType colorType){
        Coordinate bestCoordinate = possibleCoordinatesParcel().get(0);
        int minNeedParcels = 3;

        for (Coordinate c : allPlaces()) {
            List<Coordinate> parcelToPlaceToDoForm = parcelToPlaceToDoForm(c, formType, colorType);

            if(parcelToPlaceToDoForm.size() == 1 && board.isPlayableParcel(parcelToPlaceToDoForm.get(0))) {
                bestCoordinate = parcelToPlaceToDoForm.get(0);
                minNeedParcels = 1;
            }
            else if(parcelToPlaceToDoForm.size() == 2 && minNeedParcels > 1) {

                if (board.isPlayableParcel(parcelToPlaceToDoForm.get(0)))
                    bestCoordinate = parcelToPlaceToDoForm.get(0);

                else if (board.isPlayableParcel(parcelToPlaceToDoForm.get(1)))
                    bestCoordinate = parcelToPlaceToDoForm.get(1);
            }
        }
        return bestCoordinate;
    }

    //renvoie une liste de toute les parcelles pas posé pour faire la forme [form] qui a pour parcel haute [x,y,z]
    public List<Coordinate> parcelToPlaceToDoForm(Coordinate c, FormType formType, ColorType colorType){
        List<Coordinate> parcelToPlaceToDoForm = new ArrayList<>();
        Coordinate cClone = c;
        for (int i = 0; i < 3; i++) {
            if((cClone.isCentral()) || (board.isPlacedParcel(cClone) && !board.getPlacedParcels().get(cClone).getColor().equals(colorType)))
                return new ArrayList<>();
            if(formType.equals(FormType.LINE)) {
                if (!board.isPlacedParcel(cClone))
                    parcelToPlaceToDoForm.add(cClone);
                cClone = new Coordinate(cClone,new Coordinate(0,-1,1));
            }
            else if(formType.equals(FormType.TRIANGLE)) {
                if(!board.isPlacedParcel(cClone))
                    parcelToPlaceToDoForm.add(cClone);
                cClone = new Coordinate(cClone,new Coordinate(2*i-1,-i ,1-i));
            }
        }
        return parcelToPlaceToDoForm;
    }

    public boolean putCanal() {
        for(Parcel parcel : board.getPlacedParcels().values()){
            for(Coordinate[] canal : possibleCoordinatesCanal()){
                if(!parcel.getIrrigated() && (canal[0].equals(parcel.getCoordinates()) || canal[1].equals(parcel.getCoordinates()))){
                    board.placeCanal(resource.drawCanal(),canal[0],canal[1]);
                    return true;
                }
            }
        }
        placeCanal(possibleCoordinatesCanal().get(0));
        return false;
    }
}