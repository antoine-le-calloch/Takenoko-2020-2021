package fr.unice.polytech.startingpoint;

import java.util.Random;

class RandomBot extends Bot {
    private Random random;
    private Resource resource;
    private Board board;

    RandomBot(Resource resource, Board board) {
        super(resource, board);
        this.resource = resource;
        this.board = board;
        random = new Random();
    }

    //Action d'un bot pendant un tour
    @Override
    void botPlay(){
        int nb = random.nextInt(3);
        if (nb == 0 && resource.getMission().size() > 0) {   // pioche mission
            drawMission();
        }
        else if (nb ==1 && resource.getCanal().size() > 0) {  // place canal
            if (possibleCoordinatesCanal().size() > 0)
                placeRandomCanal(possibleCoordinatesCanal());
        }
        else if (nb ==2 && board.getPlayablePlaces().size() > 0){ // place parcel
            placeRandomParcel(board.getPlayablePlaces());
        }
        else if (possibleCoordinatesCanal() != null)
            movePanda(possibleCoordinatesBamboo());
    }
}