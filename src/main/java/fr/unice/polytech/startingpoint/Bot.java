package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
    void placeParcel(Resource resource, Board board){
        ArrayList<Coordinate> possibleCoord = possibleCoordinates(board);
        Collections.shuffle(possibleCoord);
        board.putParcel(resource.drawParcel(), possibleCoord.get(0));
    }

    public ArrayList<Coordinate> possibleCoordinates(Board board) {
        Set<Coordinate> coordArounds = coordinatesAroundBoard(board);
        ArrayList<Coordinate> possibleCoordinates = new ArrayList<>();
        for(Coordinate coordinate : coordArounds){
            if(board.playableParcel(coordinate)){
                possibleCoordinates.add(coordinate); }
        }
        return possibleCoordinates;
    }

    public Set<Coordinate> coordinatesAroundBoard(Board board) {
        Set<Coordinate> coordinatesAroundBoard = new HashSet<>();
        for(Parcel parcel : board.getParcel()) {
            ArrayList<Coordinate> coordinatesAround = parcel.getCoordinates().coordinatesAround();
            coordinatesAroundBoard.addAll(coordinatesAround);
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
