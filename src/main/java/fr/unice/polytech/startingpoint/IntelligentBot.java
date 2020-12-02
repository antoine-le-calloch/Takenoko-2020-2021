package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class IntelligentBot extends Bot{
    private final Resource resource;
    private final Board board;

    IntelligentBot(Resource resource, Board board) {
        super(resource, board);
        this.resource = resource;
        this.board = board;
    }

    @Override
    void botPlay(){
        if (!doDrawMission() && resource.getMission().size() > 0)
            drawMission();
        if (resource.getParcel().size() > 0)
            putParcel();
        if(resource.getCanal().size()>0 && possibleCoordinatesCanal().size()>0)
            putCanal();
    }

    //Si le bot n'a pas de mission => true
    boolean doDrawMission(){
        return getInventoryMission().size() > 1;
    }

    //Pour chaque mission, pose une cases a la meilleur place pour la terminer, ou pose sur une place random
    void putParcel() {
        String form = getInventoryMission().get(0).getGoal();
        String color = getInventoryMission().get(0).getColor();

        board.placedParcel(resource.drawParcel(), bestCoordForForm(form,color));
    }

    //renvoie la coord de la pièce a poser pour terminer le plus vite une forme [form], ou renvoie une coord random
    Coordinate bestCoordForForm(String form, String color){
        Random rand = new Random();
        for (Coordinate coord : board.getAllPlaces()) {
            List<Coordinate> parcelToPlaceToDoForm = parcelToPlaceToDoForm(coord,form,color);

            if(parcelToPlaceToDoForm.size() == 1 && board.isPlayableParcel(parcelToPlaceToDoForm.get(0)))
                return parcelToPlaceToDoForm.get(0);

            else if(parcelToPlaceToDoForm.size() == 2) {

                if (board.isPlayableParcel(parcelToPlaceToDoForm.get(0)))
                    return parcelToPlaceToDoForm.get(0);

                else if (board.isPlayableParcel(parcelToPlaceToDoForm.get(1)))
                    return parcelToPlaceToDoForm.get(1);
            }
        }

        int rdmListPlace = rand.nextInt(board.getPlayablePlaces().size());
        return board.getPlayablePlaces().get(rdmListPlace);
    }

    //renvoie une liste de toute les parcelles pas posé pour faire la forme [form] qui a pour parcel haute [x,y,z]
    ArrayList<Coordinate> parcelToPlaceToDoForm(Coordinate coord, String form, String color){
        ArrayList<Coordinate> parcelToPlaceToDoForm = new ArrayList<>();
        int x = coord.getCoordinate()[0];
        int y = coord.getCoordinate()[1];
        int z = coord.getCoordinate()[2];

        for (int i = 0; i < 3; i++) {
            if((x==0 && y==0 && z==0) || (board.placedParcel(coord) && !board.getPlacedParcels().get(coord).getColor().equals(color)))
                return new ArrayList<>();

            if(form.equals("line")) {
                if (!board.placedParcel(new Coordinate(x, y, z)))
                    parcelToPlaceToDoForm.add(new Coordinate(x, y, z));
                y--;
                z++;
            }
            else if(form.equals("triangle")) {
                if(!board.placedParcel(new Coordinate(x, y, z)))
                    parcelToPlaceToDoForm.add(new Coordinate(x, y, z));
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
                    board.placedCanal(resource.drawCanal(),canal[0],canal[1]);
                    return true;
                }
            }
        }
        placeRandomCanal(possibleCoordinatesCanal());
        return false;
    }
}