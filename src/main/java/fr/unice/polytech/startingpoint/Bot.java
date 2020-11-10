package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Iterator;

class Bot {
    int num_bot;
    ArrayList<Mission> inventoryMission = new ArrayList<>();

    Bot(int num_bot) {
        this.num_bot = num_bot;
    }

    void play(Resource resource,Board board){
        drawMission(resource);
        placeParcel(resource,board);
    }

    void drawMission(Resource resource){
        inventoryMission.add(resource.drawMission());
    }

    void placeParcel(Resource resource,Board board){
        board.putParcel(resource.drawParcel());
    }
}
