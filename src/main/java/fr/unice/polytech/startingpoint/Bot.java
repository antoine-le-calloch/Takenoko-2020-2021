package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class Bot {
    private final String botName;
    private final ArrayList<Mission> inventoryMission = new ArrayList<>();

    Bot(String botName) {
        this.botName = botName;
    }

    //Action d'un bot pendant un tour
    void play(Resource resource,Board board){
        drawMission(resource);
        placeParcel(resource,board);
    }

    //pioche une mission
    void drawMission(Resource resource){
        inventoryMission.add(resource.drawMission());
    }

    //place une parcelle
    boolean placeParcel(Resource resource, Board board){
        ArrayList<Coordinate> possibleCoord = possibleCoordinates(board);
        Collections.shuffle(possibleCoord);
        return board.putParcel(resource.drawParcel(),possibleCoord.get(0));
    }

    public ArrayList<Coordinate> possibleCoordinates(Board board) {
        ArrayList<Coordinate> coordArounds = coordinatesAroundBoard(board);
        ArrayList<Coordinate> possibleCoordinates = new ArrayList<>();
        for(Coordinate coordinate : coordArounds){
            if(board.playableParcel(coordinate)){
                possibleCoordinates.add(coordinate); }
        }
        return possibleCoordinates;
    }

    public ArrayList<Coordinate> coordinatesAroundBoard(Board board) {
        ArrayList<Coordinate> coordinatesAroundBoard = new ArrayList<>();
        for(Parcel parcel : board.getParcel()){
            ArrayList<Coordinate> coordinatesAround = parcel.getCoordinates().coordinatesAround();
            for(Coordinate coord : coordinatesAround){
                boolean add = true;
                for(Coordinate coordAB : coordinatesAroundBoard){
                    if(coord.isEqualTo(coordAB)){
                        add = false;
                    }
                }
                if(add){
                    coordinatesAroundBoard.add(coord);
                }
            }
        }
        return coordinatesAroundBoard;
    }

    void deleteMission(Mission mission) {
        inventoryMission.remove(mission);
    }

    String getBotName() {
        return botName;
    }

    ArrayList<Mission> getInventoryMission() {
        return (ArrayList<Mission>) inventoryMission.clone();
    }
}
