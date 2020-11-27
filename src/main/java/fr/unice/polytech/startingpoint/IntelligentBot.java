package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

class IntelligentBot extends Bot{
    Resource resource;
    Board board;

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
        return inventoryMission.size() > 1;
    }

    //Pour chaque mission, cherche les cases disponible pour finir la forme
    void putParcel() {
        Coordinate coord = BestCoordForForm(inventoryMission.get(0).getGoal());
        if(coord != null)
            board.placeParcel(resource.drawParcel(), coord);
        else
            placeRandomParcel(board.getFreePlaces());
    }

    Coordinate BestCoordForForm(String form){
        ArrayList<Coordinate> parcelToPlaceToDoForm;

        for (Coordinate coord : board.getAllPlaces()) {
            parcelToPlaceToDoForm = parcelToPlaceToDoForm(coord,form);

            if(parcelToPlaceToDoForm.size() == 1 && board.freeParcel(parcelToPlaceToDoForm.get(0)))
                return parcelToPlaceToDoForm.get(0);

            else if(parcelToPlaceToDoForm.size() == 2) {

                if (board.freeParcel(parcelToPlaceToDoForm.get(0)))
                    return parcelToPlaceToDoForm.get(0);

                else if (board.freeParcel(parcelToPlaceToDoForm.get(1)))
                    return parcelToPlaceToDoForm.get(1);
            }
        }
        return null;
    }

    //renvoie une liste de toute les parcelles pas posé pour faire la forme [form] qui a pour parcel haute [x,y,z]
    ArrayList<Coordinate> parcelToPlaceToDoForm(Coordinate coord, String form){
        ArrayList<Coordinate> parcelToPlaceToDoForm = new ArrayList<>();
        int x = coord.getCoordinate()[0];
        int y = coord.getCoordinate()[1];
        int z = coord.getCoordinate()[2];

        for (int i = 0; i < 3; i++) {
            if(form.equals("line")) {
                if (!board.isParcel(new Coordinate(x, y, z)))
                    parcelToPlaceToDoForm.add(new Coordinate(x, y, z));
                y--;
                z++;
            }
            else if(form.equals("triangle")) {
                if(!board.isParcel(new Coordinate(x, y, z)))
                    parcelToPlaceToDoForm.add(new Coordinate(x, y, z));
                x = x - 1 + (2 * i); //x-- pour la parcel 2, x++ pour la parcel 3
                y = y - i; //y pour la parcel 2, y-- pour la parcel 3
                z = z + 1 - i; //z++ pour la parcel 2, z pour la parcel 3
            }
        }
        return parcelToPlaceToDoForm;
    }

    boolean putCanal() {
        for(Parcel parcel : board.getPlacedParcels()){
            for(Coordinate[] canal : possibleCoordinatesCanal()){
                if(!parcel.getIrrigated() && (canal[0].equals(parcel.getCoordinates()) || canal[1].equals(parcel.getCoordinates()))){
                    board.putCanal(resource.drawCanal(),canal[0],canal[1]);
                    return true;
                }
            }
        }
        placeRandomCanal(possibleCoordinatesCanal());
        return false;
    }
}