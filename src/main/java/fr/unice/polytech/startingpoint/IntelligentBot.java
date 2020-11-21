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
        if ( coordinateMakingLine().size() > 0)
            placeParcel(coordinateMakingLine());
        placeParcel(possibleCoordinates());
    }

    boolean haveMission(){
        return inventoryMission.size() > 1;
    }


    //creer une liste des coordonnée possible qui peuvent faire une ligne
    ArrayList<Coordinate> coordinateMakingLine() {
        ArrayList<Coordinate> possibleCoord = possibleCoordinates();
        ArrayList<Coordinate> coordMakingLine = new ArrayList<>();
        for (Coordinate coordinate : possibleCoord) {
            for( Parcel parcel : board.getParcel()) {
                if (parcel.getCoordinates().equals(new Coordinate(parcel.getCoordinates(), new Coordinate(0, -1, 1)))
                        && parcel.getCoordinates().equals(new Coordinate(parcel.getCoordinates(), new Coordinate(0, -2, 2)))) {
                    coordMakingLine.add(coordinate); //c'est moche
                }
            }
        }
        return coordMakingLine;

    } //problème evident de code redondant par exemple :

    //creer une liste des coordonnée possible qui peuvent faire un triangle
    ArrayList<Coordinate> coordinateMakingTriangle() {
        ArrayList<Coordinate> possibleCoord = possibleCoordinates();
        ArrayList<Coordinate> coordMakingTriangle = new ArrayList<>();
        for (Coordinate coordinate : possibleCoord) {
            for( Parcel parcel : board.getParcel()) {
                if (parcel.getCoordinates().equals(new Coordinate(parcel.getCoordinates(), new Coordinate(1, -1, 0)))
                        && parcel.getCoordinates().equals(new Coordinate(parcel.getCoordinates(), new Coordinate(1, 0, -1)))) {
                    coordMakingTriangle.add(coordinate); //c'est moche
                }
            }
        }
        return coordMakingTriangle;
    }

}
