package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Collections;

class Bot {
    private String botName;
    private ArrayList<Mission> inventoryMission = new ArrayList<>();
    private final ArrayList<int[]> nextCoordinates = new ArrayList<>();
    private final ArrayList<int[]> offset = new ArrayList<>();

    Bot(String botName) {
        this.botName = botName;
        initializeOffset();
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
        ArrayList<int[]> possibleCoord = possibleCoordinates(board);
        Collections.shuffle(possibleCoord);
        return board.putParcel(resource.drawParcel(),possibleCoord.get(0));
    }

    //créer une liste de toutes les coordonnées ont une case peut être posé
    ArrayList<int[]> possibleCoordinates(Board board){
        initializeNextCoordinates(board);
        ArrayList<int[]> possibleCoordinates = new ArrayList<>();
        for(int[] coord : nextCoordinates){
            if(board.playableParcel(coord)){
                possibleCoordinates.add(coord);
            }
        }
        return possibleCoordinates;
    }

    //créer une liste qui possède toutes les coordonnées des cases à côté de parcelle posé
    private void initializeNextCoordinates(Board board){
        for(Parcel parcel : board.getParcel()) {
            for(int[] offset : offset) {
                nextCoordinates.add(new int[] { parcel.getCoordinates()[0] + offset[0], parcel.getCoordinates()[1] + offset[1], parcel.getCoordinates()[2] + offset[2] });
            }
        }
    }

    //initialise la array liste offset
    private void initializeOffset(){
        offset.add(new int[]{0,-1,1});
        offset.add(new int[]{1,-1,0});
        offset.add(new int[]{1,0,-1});
        offset.add(new int[]{0,1,-1});
        offset.add(new int[]{-1,1,0});
        offset.add(new int[]{-1,0,1});
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
