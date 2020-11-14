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

    void play(Resource resource,Board board){
        drawMission(resource);
        placeParcel(resource,board);
    }

    void drawMission(Resource resource){
        inventoryMission.add(resource.drawMission());
    }

    boolean placeParcel(Resource resource, Board board){
        ArrayList<int[]> possibleCoord = possibleCoordinates(board);
        Collections.shuffle(possibleCoord);
        return board.putParcel(resource.drawParcel(),possibleCoord.get(0));
    }

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

    private void initializeNextCoordinates(Board board){
        for(Parcel parcel : board.getParcel()) {
            for(int[] offset : offset) {
                nextCoordinates.add(new int[] { parcel.getCoordinates()[0] + offset[0], parcel.getCoordinates()[1] + offset[1], parcel.getCoordinates()[2] + offset[2] });
            }
        }
    }

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
