package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Collections;

class Bot {
    private String botName;
    private ArrayList<Mission> inventoryMission = new ArrayList<>();

    Bot(String botName) {
        this.botName = botName;
    }

    void play(Resource resource,Board board){
        drawMission(resource);
        placeParcel(resource,board);
    }

    void drawMission(Resource resource){
        inventoryMission.add(resource.drawMission());
    }

    boolean placeParcel(Resource resource, Board board){
        ArrayList<int[]> possibleCoord = board.possibleCoordinates();
        Collections.shuffle(possibleCoord);
        return board.putParcel(resource.drawParcel(),possibleCoord.get(0));
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
