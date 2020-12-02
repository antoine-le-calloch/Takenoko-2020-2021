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
        return getInventoryMission().size() > 5;
    }

    //Pour chaque mission, pose une cases a la meilleur place pour la terminer, ou pose sur une place random
    void putParcel() {
        ParcelMission mission = (ParcelMission) getInventoryMission().get(0);
        String form = mission.getGoal();
        String color = mission.getColor();
        Parcel newParcel = resource.drawParcel();

        if(newParcel.getColor().equals(color))
            board.placeParcel(newParcel, bestCoordForForm(form,color));
        else
            board.placeParcel(newParcel, board.getPlayablePlaces().get(1));
    }

    //renvoie la coord de la pièce a poser pour terminer le plus vite une forme [form], ou renvoie une coord random
    Coordinate bestCoordForForm(String form, String color){
        Coordinate bestCoord = board.getPlayablePlaces().get(0);
        int minNeedPartels = 3;

        for (Coordinate coord : board.getAllPlaces()) {
            List<Coordinate> parcelToPlaceToDoForm = parcelToPlaceToDoForm(coord,form,color);

            if(parcelToPlaceToDoForm.size() == 1 && board.isPlayableParcel(parcelToPlaceToDoForm.get(0))) {
                bestCoord = parcelToPlaceToDoForm.get(0);
                minNeedPartels = 1;
            }
            else if(parcelToPlaceToDoForm.size() == 2 && minNeedPartels > 1) {

                if (board.isPlayableParcel(parcelToPlaceToDoForm.get(0)))
                    bestCoord = parcelToPlaceToDoForm.get(0);

                else if (board.isPlayableParcel(parcelToPlaceToDoForm.get(1)))
                    bestCoord = parcelToPlaceToDoForm.get(1);
            }
        }
        return bestCoord;
    }

    //renvoie une liste de toute les parcelles pas posé pour faire la forme [form] qui a pour parcel haute [x,y,z]
    ArrayList<Coordinate> parcelToPlaceToDoForm(Coordinate coordHight, String form, String color){
        ArrayList<Coordinate> parcelToPlaceToDoForm = new ArrayList<>();
        int x = coordHight.getCoordinate()[0];
        int y = coordHight.getCoordinate()[1];
        int z = coordHight.getCoordinate()[2];

        for (int i = 0; i < 3; i++) {
            Coordinate coord = new Coordinate(x, y, z);
            if((x==0 && y==0 && z==0) || (board.isPlacedParcel(coord) && !board.getPlacedParcels().get(coord).getColor().equals(color)))
                return new ArrayList<>();

            if(form.equals("line")) {
                if (!board.isPlacedParcel(coord))
                    parcelToPlaceToDoForm.add(coord);
                y--;
                z++;
            }
            else if(form.equals("triangle")) {
                if(!board.isPlacedParcel(coord))
                    parcelToPlaceToDoForm.add(coord);
                x = x - 1 + (2 * i); //x-- pour la parcel 2, x++ pour la parcel 3
                y = y - i; //y pour la parcel 2, y-- pour la parcel 3
                z = z + 1 - i; //z++ pour la parcel 2, z pour la parcel 3
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