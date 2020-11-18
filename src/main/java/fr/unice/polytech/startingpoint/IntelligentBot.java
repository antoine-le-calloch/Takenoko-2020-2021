package fr.unice.polytech.startingpoint;

public class IntelligentBot extends Bot{
    IntelligentBot(String botName) {
        super(botName);
    }

    void play(Resource resource,Board board){
        drawMission(resource);
        drawMission(resource);
        drawMission(resource);
        drawMission(resource);
        drawMission(resource);
        drawMission(resource);
        placeParcel(resource,board);
    }

}
