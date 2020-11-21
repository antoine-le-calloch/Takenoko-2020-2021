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
        if (!haveMission())
            drawMission();
        wherePutParcel();
    }

    boolean haveMission(){
        return inventoryMission.size() > 1;
    }


    //creer une liste des coordonnée possible qui peuvent faire une forme
    ArrayList<Coordinate> coordinateMakingGoal(Coordinate offset1, Coordinate offset2 /* + couleur*/) {
        ArrayList<Coordinate> possibleCoord = possibleCoordinates();
        ArrayList<Coordinate> coordMakingLine = new ArrayList<>();
        for (Coordinate coordinate : possibleCoord) {
            for( Parcel parcel : board.getParcel()) {
                if (parcel.getCoordinates().equals(new Coordinate(parcel.getCoordinates(), offset1)) // + bonne couleur
                        && parcel.getCoordinates().equals(new Coordinate(parcel.getCoordinates(), offset2))) {
                    coordMakingLine.add(coordinate);
                }
            }
        }
        return coordMakingLine;
    }

    void wherePutParcel() {
        for (Mission mission : inventoryMission) {
            if (mission.getGoal().equals("line"))
                if (coordinateMakingGoal(new Coordinate(0, 1, -1), new Coordinate(0, -1, 1)).size() > 0) {
                    // on doit verifier que la liste n'est pas vide
                    placeParcel(coordinateMakingGoal(new Coordinate(0, 1, -1), new Coordinate(0, -1, 1)));
                    return;
                }
        }
        placeParcel(possibleCoordinates());
    }

}
