package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

class Bot {
    private final Resource resource;
    private final Board board;
    final ArrayList<Mission> inventoryMission = new ArrayList<>(); // pas de private pour les sous classes

    Bot(Resource resource, Board board) {
        this.resource = resource;
        this.board = board;
    }

    //Action d'un bot pendant un tour
    void Botplay(){
        //vide pour les sous classes
    }

    //pioche une mission
    void drawMission(){
        inventoryMission.add(resource.drawMission());
    }

    //place une parcelle
    void placeParcel(){
        //vide pour les sous classes
    }

    ArrayList<Coordinate> possibleCoordinates() {
        ArrayList<Coordinate> coordArounds = coordinatesAroundBoard();
        ArrayList<Coordinate> possibleCoordinates = new ArrayList<>();
        for(Coordinate coordinate : coordArounds){
            if(board.playableParcel(coordinate)){
                possibleCoordinates.add(coordinate); }
        }
        return possibleCoordinates;
    }

    ArrayList<Coordinate> coordinatesAroundBoard() {
        Set<Coordinate> coordinatesAroundBoard = new HashSet<>();
        for(Parcel parcel : board.getParcel()) {
            ArrayList<Coordinate> coordinatesAround = parcel.getCoordinates().coordinatesAround();
            coordinatesAroundBoard.addAll(coordinatesAround);
        }
        return new ArrayList<>(coordinatesAroundBoard);
    }




    void deleteMission(Mission mission) {
        inventoryMission.remove(mission);
    }

    ArrayList<Mission> getInventoryMission() {
        return (ArrayList<Mission>) inventoryMission.clone();
    }
}
