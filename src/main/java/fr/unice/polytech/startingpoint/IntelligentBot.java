package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.List;

class IntelligentBot extends Bot{

    IntelligentBot(Resource resource, Board board) {
        super(resource, board);
    }

    @Override
    void botPlay(){
        if (!doDrawMission() && resource.getNbMissionParcel() > 0)
            drawMission();
        if (resource.getParcel().size() > 0)
            putParcel();
        if(resource.getCanal().size()>0 && possibleCoordinatesCanal().size()>0)
            putCanal();
    }

    //Si le bot n'a pas de mission => true
    boolean doDrawMission(){
        return getInventory().getMission().size() > 5;
    }

    //Pour chaque mission, pose une cases a la meilleur place pour la terminer, ou pose sur une place random
    void putParcel() {
        ParcelMission mission = (ParcelMission) getInventory().getMission().get(0);
        Form form = mission.getGoal();
        Color color = mission.getColor();
        Parcel newParcel = resource.drawParcel();

        if(newParcel.getColor().equals(color))
            board.placeParcel(newParcel, bestCoordinatesForForm(form,color));
        else
            board.placeParcel(newParcel, board.getPlayablePlaces().get(1));
    }

    //renvoie la coord de la pièce a poser pour terminer le plus vite une forme [form], ou renvoie une coord random
    Coordinate bestCoordinatesForForm(Form form, Color color){
        Coordinate bestCoordinate = board.getPlayablePlaces().get(0);
        int minNeedPartels = 3;

        for (Coordinate coord : board.getAllPlaces()) {
            List<Coordinate> parcelToPlaceToDoForm = parcelToPlaceToDoForm(coord,form,color);

            if(parcelToPlaceToDoForm.size() == 1 && board.isPlayableParcel(parcelToPlaceToDoForm.get(0))) {
                bestCoordinate = parcelToPlaceToDoForm.get(0);
                minNeedPartels = 1;
            }
            else if(parcelToPlaceToDoForm.size() == 2 && minNeedPartels > 1) {

                if (board.isPlayableParcel(parcelToPlaceToDoForm.get(0)))
                    bestCoordinate = parcelToPlaceToDoForm.get(0);

                else if (board.isPlayableParcel(parcelToPlaceToDoForm.get(1)))
                    bestCoordinate = parcelToPlaceToDoForm.get(1);
            }
        }
        return bestCoordinate;
    }

    //renvoie une liste de toute les parcelles pas posé pour faire la forme [form] qui a pour parcel haute [x,y,z]
    List<Coordinate> parcelToPlaceToDoForm(Coordinate c, Form form, Color color){
        List<Coordinate> parcelToPlaceToDoForm = new ArrayList<>();
        Coordinate cClone = c;
        for (int i = 0; i < 3; i++) {
            if((cClone.isCentral()) || (board.isPlacedParcel(cClone) && !board.getPlacedParcels().get(cClone).getColor().equals(color)))
                return new ArrayList<>();
            if(form.equals(Form.LINE)) {
                if (!board.isPlacedParcel(cClone))
                    parcelToPlaceToDoForm.add(cClone);
                cClone = new Coordinate(cClone,new Coordinate(0,-1,1));
            }
            else if(form.equals(Form.TRIANGLE)) {
                if(!board.isPlacedParcel(cClone))
                    parcelToPlaceToDoForm.add(cClone);
                cClone = new Coordinate(cClone,new Coordinate(2*i-1,-i ,1-i));
            }
        }
        return parcelToPlaceToDoForm;
    }

    boolean putCanal() {
        for(Parcel parcel : board.getPlacedParcels().values()){
            for(Coordinate[] canal : possibleCoordinatesCanal()){
                if(!parcel.getIrrigated() && (canal[0].equals(parcel.getCoordinates()) || canal[1].equals(parcel.getCoordinates()))){
                    board.placeCanal(resource.drawCanal(),canal[0],canal[1]);
                    return true;
                }
            }
        }
        placeRandomCanal(possibleCoordinatesCanal());
        return false;
    }
}