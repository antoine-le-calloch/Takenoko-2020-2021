package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

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


        return board.putParcel(resource.drawParcel(),x,y,z);
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
