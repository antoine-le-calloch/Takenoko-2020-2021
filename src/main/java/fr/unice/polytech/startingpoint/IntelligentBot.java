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
    void Botplay(){
        if (!doDrawMission() && resource.getMission().size() > 0)
            drawMission();
        if (resource.getParcel().size() > 0)
            PutParcel();
    }

    //Si le bot n'a pas de mission => true
    boolean doDrawMission(){
        return inventoryMission.size() > 1;
    }

    //Pour chaque mission, cherche les cases disponible pour finir la forme
    void PutParcel() {
        Coordinate coord = CoordBestform(inventoryMission.get(0).getGoal());
        if(coord != null)
            board.putParcel(resource.drawParcel(), coord);
        else
            placeParcel(possibleCoordinatesParcel());
    }

    Coordinate CoordBestform(String form){
        ArrayList<Coordinate> ParcelToPlaceToDoForm = new ArrayList<>();
        ArrayList<Coordinate> BestParcel = new ArrayList<>();

        for (Coordinate coord : allCoordinate()) {
            ParcelToPlaceToDoForm = ParcelToPlaceToDoForm(coord.getCoordinate(),form);

            if(ParcelToPlaceToDoForm.size() == 1 && ParcelToPlaceToDoForm.get(0) != null)
                return ParcelToPlaceToDoForm.get(0);
            /*else if(ParcelToPlaceToDoForm.size() == 2 && ParcelToPlaceToDoForm.get(0) != null)
                return ParcelToPlaceToDoForm.get(0);
            else if(ParcelToPlaceToDoForm.size() == 2 && ParcelToPlaceToDoForm.get(1) != null)
                return ParcelToPlaceToDoForm.get(1);*/
        }
        return null;
    }

    //renvoie une liste de toute les parcelles pas posé pour faire la forme [form] qui a pour parcel haute [x,y,z]
    ArrayList<Coordinate> ParcelToPlaceToDoForm(int []coord, String form){
        ArrayList<Coordinate> ParcelToPlaceToDoForm = new ArrayList<>();
        int x = coord[0];
        int y = coord[1];
        int z = coord[2];

        for (int i = 0; i < 3; i++) {
            if(x == 0 && y == 0 && z == 0)
                return ParcelToPlaceToDoForm;
            if(form.equals("line")) {
                if (board.playableParcel(new Coordinate(x, y, z)))
                    ParcelToPlaceToDoForm.add(new Coordinate(x, y, z));
                y--;
                z++;
            }
            else if(form.equals("triangle")) {
                if(board.playableParcel(new Coordinate(x, y, z)))
                    ParcelToPlaceToDoForm.add(new Coordinate(x, y, z));
                x = x - 1 + (2 * i); //x-- pour la parcel 2, x++ pour la parcel 3
                y = y - i; //y pour la parcel 2, y-- pour la parcel 3
                z = z + 1 - i; //z++ pour la parcel 2, z pour la parcel 3
            }
        }
        return ParcelToPlaceToDoForm;
    }
}