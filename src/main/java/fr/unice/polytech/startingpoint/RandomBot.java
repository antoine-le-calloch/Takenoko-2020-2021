package fr.unice.polytech.startingpoint;

public class RandomBot extends Bot {

    RandomBot(String botName) {
        super(botName);
    }

    //Action d'un bot pendant un tour
    void play(Resource resource,Board board){
        drawMission(resource);
        placeParcel(resource,board);
    }

}
