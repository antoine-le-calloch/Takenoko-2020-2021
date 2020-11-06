package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

class Bot {
    int num_bot;
    ArrayList<Mission> inventoryMission = new ArrayList<>();

    Bot(int num_bot) {
        this.num_bot = num_bot;
    }

    int play(Resource resource,Board board) {
        inventoryMission.add(resource.drawMission());
        board.putParcel(resource.drawParcel());

        if (board.placedParcel.size() != 0 && inventoryMission.get(0).getIDMission() == 1) {
            return inventoryMission.get(0).getPoints();
        }
        return 0;
    }
}
