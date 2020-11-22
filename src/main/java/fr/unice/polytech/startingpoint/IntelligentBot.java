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
        if (!haveMission() && resource.getMission().size() > 0)
            drawMission();
        if (resource.getParcel().size() > 0)
            wherePutParcel();
    }

    boolean haveMission(){
        return inventoryMission.size() > 1;
    }


    //creer une liste des coordonnée possible qui peuvent faire une forme
    ArrayList<Coordinate> coordinateMakingGoal(String goal /* + couleur*/) {
        ArrayList<Coordinate> possibleCoord = possibleCoordinatesParcel();
        ArrayList<Coordinate> coordMakingLine = new ArrayList<>();
        for (Coordinate coordinate : possibleCoord) {
                if (board.checkGoal(goal,coordinate, false))
                    coordMakingLine.add(coordinate);
            }
        return coordMakingLine;
    }

    //Pour chaque mission, cherche les cases disponible pour finir la forme
    void wherePutParcel() {
        for (Mission mission : inventoryMission) {
            if (coordinateMakingGoal(mission.getGoal()).size() > 0) {
                // on doit verifier que la liste n'est pas vide
                placeParcel(coordinateMakingGoal(mission.getGoal()));
                return;
            }
        }
        placeParcel(possibleCoordinatesParcel());
    }

}
