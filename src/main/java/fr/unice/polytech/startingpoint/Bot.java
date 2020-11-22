package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Collections;
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

    //place une parcelle dans une coordonnée de la liste donnée
    void placeParcel(ArrayList<Coordinate> listCoord){
        Collections.shuffle(listCoord);
        board.putParcel(resource.drawParcel(), listCoord.get(0));
    }

    //place un canal sur une coordonnée contenue dans une liste donnée
    void placeCanal(ArrayList<Coordinate[]> listCoord){
        Collections.shuffle(listCoord);
        board.putCanal(resource.drawCanal(), listCoord.get(0)[0],listCoord.get(0)[1]);
    }

    ArrayList<Coordinate> possibleCoordinatesParcel() {
        ArrayList<Coordinate> coordArounds = coordinatesAroundBoard();
        ArrayList<Coordinate> possibleCoordinates = new ArrayList<>();
        for(Coordinate coordinate : coordArounds){
            if(board.playableParcel(coordinate)){
                possibleCoordinates.add(coordinate); }
        }
        return possibleCoordinates;
    }

    ArrayList<Coordinate[]> possibleCoordinatesCanal(){
        Set<Coordinate[]> possibleCoordinates = new HashSet<>();
        for(Parcel parcel1 : board.getParcel()){
            for(Parcel parcel2 : board.getParcel()){
                if (board.playableCanal(parcel1.getCoordinates(),parcel2.getCoordinates()))
                    possibleCoordinates.add(new Coordinate[] {parcel1.getCoordinates(),parcel2.getCoordinates()});
            }
        }
        return new ArrayList<>(possibleCoordinates);
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
