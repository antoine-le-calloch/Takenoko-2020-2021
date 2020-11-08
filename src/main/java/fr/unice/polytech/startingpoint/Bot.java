package fr.unice.polytech.startingpoint;

import java.util.ArrayList;

class Bot {


    int num_bot;
    int score_bot=0;
    int mission_done=0;

    ArrayList<Mission> inventoryMission = new ArrayList<>();

    Bot(int num_bot) {
        this.num_bot = num_bot;
    }

    void play(Resource resource,Board board){
        drawMission(resource);
        placeParcel(resource,board);
        missionDone(board);
    }

    void drawMission(Resource resource){
        inventoryMission.add(resource.drawMission());
    }

    void placeParcel(Resource resource,Board board){
        board.putParcel(resource.drawParcel());
    }


    void missionDone(Board board){
        for(Mission mission : inventoryMission){
            int count=mission.checkMission(board);
            if (count!=0){
                inventoryMission.remove(mission);
                mission_done++;
                score_bot+=count;
            }
        }
    }
}
